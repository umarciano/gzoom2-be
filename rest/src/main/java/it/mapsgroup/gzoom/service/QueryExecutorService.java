package it.mapsgroup.gzoom.service;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.PartyContentDao;
import it.mapsgroup.gzoom.querydsl.dto.*;
import it.mapsgroup.gzoom.service.queryexecutor.layout.ColumnStyle;
import it.mapsgroup.gzoom.service.queryexecutor.layout.StyleApply;
import it.mapsgroup.gzoom.service.queryexecutor.layout.StyleParser;
import it.mapsgroup.gzoom.util.pdf.ConvertPDFtoA3;
import it.mapsgroup.gzoom.util.pdf.PDFA3Components;
import oracle.sql.TIMESTAMP;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Query Executor service.
 *
 * @author Antonio Calò
 */
@Service
public class QueryExecutorService{
    private final DataSource dataSource;
    private final PartyContentDao partyContentDao;
    private final UserPreferenceService userPreferenceService;
    private final PartyService partyService;
    private static final Logger LOG = getLogger(QueryExecutorService.class);
    private static final String CSV = "text/csv";
    private static final String PDF = "application/pdf";
    private static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    DataFormatter formatter = new DataFormatter();
    private static final PDFont fontPlain = PDType1Font.HELVETICA;
    private static final PDFont fontBold = PDType1Font.HELVETICA_BOLD;
    private String columnsFormat;
    private List<ColumnStyle> columnStyleList;
    private StyleParser styleParser;
    private StyleApply styleApply;
    private ColumnStyle breakRowColumnStyle;
    private String breakRowColumnCurrentValue;
    private boolean hasRowFormatParam;
    //private static long cellStyleCounter = 0;
    private CellStyle dateCellStyle;
    private CellStyle headerCellStyle;

    private static final String SERVICE_NAME = "QueryExecutorService";
    private static final String SERVICE_TYPE_ID = "QUERY_EXECUTOR";
    private final JobLogService jobLogService;

    private final Map<String, String> TIPO_QUERY = new HashMap<>() {{
        put("SELECT", "E");
        put("UPDATE", "A");
    }};


    @Autowired
    public QueryExecutorService(@Qualifier(value = "mainDataSource") DataSource dataSource,
                                PartyService partyService,
                                PartyContentDao partyContentDao, UserPreferenceService userPreferenceService, JobLogService jobLogService, SequenceGenerator sequenceGenerator) {
        this.dataSource = dataSource;
        this.partyService = partyService;
        this.partyContentDao = partyContentDao;
        this.userPreferenceService = userPreferenceService;
        this.hasRowFormatParam = false;
        this.jobLogService = jobLogService;
    }

    @Transactional
    public String execQuery(QueryConfig query, HttpServletRequest req, HttpServletResponse response) {

        this.styleApply = new StyleApply();
        this.styleParser = new StyleParser();
        this.dateCellStyle = null;
        this.headerCellStyle = null;

        JobLog jobLog = new JobLog();
        jobLog = this.jobLogService.jobLogStart(jobLog, SERVICE_TYPE_ID, SERVICE_NAME);
        long execQueryStart = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if(query.getQueryColumnsFormaParam() != null){
            this.columnsFormat = query.getQueryColumnsFormaParam().replaceAll("\n","");
        }
        else{
            this.columnsFormat = null;
        }

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }


        String finalQuery;
        //GET Query
        finalQuery = query.getQueryInfo();

        JobLogLog jobLogLog = new JobLogLog();
        jobLogLog.setJobLogId(jobLog.getJobLogId());
        jobLogLog.setValueRef1(query.getQueryCode());

        Map<String, String> queryCodeLogParameters = new HashMap<>();
        queryCodeLogParameters.put("queryCode", query.getQueryCode());
        this.jobLogService.addJobLogLogStart(jobLogLog, queryCodeLogParameters);

        //Replace Condition
        if(query.getCond0Info()!=null && !query.getCond0Info().equals("")) {

            finalQuery = finalQuery.replaceAll("(?i)#COND0#",query.getCond0Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond0Name(), query.getCond0Comm(), query.getCond0Info());
        }
        if(query.getCond1Info()!=null && !query.getCond1Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND1#", query.getCond1Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond1Name(), query.getCond1Comm(), query.getCond1Info());

        }
        if(query.getCond2Info()!=null && !query.getCond2Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND2#", query.getCond2Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond2Name(), query.getCond2Comm(), query.getCond2Info());

        }
        if(query.getCond3Info()!=null && !query.getCond3Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND3#", query.getCond3Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond3Name(), query.getCond3Comm(), query.getCond3Info());

        }
        if(query.getCond4Info()!=null && !query.getCond4Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND4#", query.getCond4Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond4Name(), query.getCond4Comm(), query.getCond4Info());

        }
        if(query.getCond5Info()!=null && !query.getCond5Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND5#", query.getCond5Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond5Name(), query.getCond5Comm(), query.getCond5Info());

        }
        if(query.getCond6Info()!=null && !query.getCond6Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND6#", query.getCond6Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond6Name(), query.getCond6Comm(), query.getCond6Info());

        }
        if(query.getCond7Info()!=null && !query.getCond7Info().equals("")) {
            finalQuery = finalQuery.replaceAll("(?i)#COND7#", query.getCond7Info());
            this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), query.getCond7Name(), query.getCond7Comm(), query.getCond7Info());

        }

        this.jobLogService.addJobLogJobExecParams(jobLog.getJobLogId(), "userLoginId", null, SecurityContextHolder.getContext().getAuthentication().getName());

        finalQuery = finalQuery.replaceAll("(?i)#USERID#",SecurityContextHolder.getContext().getAuthentication().getName());

        queryCodeLogParameters.put("finalQuery", finalQuery);
        this.jobLogService.addJobLogLogFinal(jobLogLog, queryCodeLogParameters);

        try {
            connection = dataSource.getConnection();
            //Se la query è una select crea un file xlsx
            if(query.getQueryType().equals(TIPO_QUERY.get("SELECT")))
            {
                //GN-4940
                if(!isContain(finalQuery,"UPDATE") &&
                        !isContain(finalQuery,"DELETE") &&
                        !isContain(finalQuery,"TRUNCATE") &&
                        !isContain(finalQuery,"INSERT") &&
                        !isContain(finalQuery,"DROP")
                    ) {
                    connection.setReadOnly(true);
                    stmt = connection.prepareStatement(finalQuery);
                    rs = stmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    getColumnsFormat(rsmd);
                    int maxColumn = rsmd.getColumnCount();
                    Workbook wb = new XSSFWorkbook();
                    Sheet sheet = wb.createSheet("ExecuteQuery");
                    // create header
                    Row headerRow = sheet.createRow(0);
                    for (int i = 1; i <= maxColumn; i++) {
                        String columnName = rsmd.getColumnName(i);
                        //non stampo eventuale colonna PARAMS
                        if(!columnName.equalsIgnoreCase(StyleParser.ROW_FORMAT_PARAM_NAME)){
                            Cell headerCell = headerRow.createCell(i - 1);
                            headerCell.setCellValue(columnName);
                            //formatCellByParams(sheet,wb,i,headerCell);
                            formatHeader(wb,headerCell);
                        }
                    }
                    //create detail
                    int rowCount = 1;
                    int breakRowLastColNum = getBreakRowLastColNum(rs,maxColumn);
                    while (rs.next()) {
                        rowCount = manageBreakRowCellByParams(sheet,wb,rowCount,rs,breakRowLastColNum);
                        getRowFormat(rowCount, rs);
                        Row row = sheet.createRow(rowCount++);
                        for (int i = 1; i <= maxColumn; i++) {
                            Object valueObject = rs.getObject(i);
                            //non stampo eventuali valori della colonna PARAMS
                            String columnName = rsmd.getColumnName(i);
                            if(!columnName.equalsIgnoreCase(StyleParser.ROW_FORMAT_PARAM_NAME)){
                                Cell cell = row.createCell(i - 1);
                                if (valueObject instanceof Boolean)
                                    cell.setCellValue((Boolean) valueObject);
                                else if (valueObject instanceof Integer)
                                    cell.setCellValue((int) valueObject);
                                else if (valueObject instanceof BigDecimal)
                                    cell.setCellValue(((BigDecimal) valueObject).doubleValue());
                                else if (valueObject instanceof Double)
                                    cell.setCellValue((double) valueObject);
                                else if (valueObject instanceof Long)
                                    cell.setCellValue((long) valueObject);
                                else if (valueObject instanceof Float)
                                    cell.setCellValue((float) valueObject);
                                else if (valueObject instanceof Date) {
                                    cell.setCellValue((Date) valueObject);
                                    formatDateCell(wb, cell);
                                } else if (valueObject instanceof Timestamp) {
                                    cell.setCellValue((Timestamp) valueObject);
                                    formatDateCell(wb, cell);
                                } else if (valueObject instanceof oracle.sql.TIMESTAMP) {
                                    cell.setCellValue(((TIMESTAMP) valueObject).timestampValue());
                                    formatDateCell(wb, cell);
                                }
                                else if (valueObject instanceof Clob) {
                                    cell.setCellValue(((Clob) valueObject).getSubString(1, (int)((Clob) valueObject).length()));
                                }
                                else cell.setCellValue((String) valueObject);
                                formatCellByParams(sheet,wb,i,rowCount -1,cell);
                            }
                        }
                    }
                    //Resize width column after population data
                    if(this.columnStyleList == null || this.columnStyleList.size() == 0){
                        autoSizeColumns(wb);
                    }
                    if(query.getExportMimeType().equals(PDF)) {
                        response.setContentType(PDF);
                        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"export_" +query.getQueryName()+"_"+ query.getQueryId() + ".pdf\"");
                        createPDF(response, wb, query);

                    } else if (query.getExportMimeType().equals(CSV)) {
                        response.setContentType(CSV);
                        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"export_" +query.getQueryName()+"_"+ query.getQueryId() + ".csv\"");
                        CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), CSVFormat.DEFAULT.withDelimiter(';'));

                        Iterator<Row> rowIterator = wb.getSheetAt(0).rowIterator();
                        while(rowIterator.hasNext()) {
                            Row row = rowIterator.next();
                            Iterator<Cell> cellIterator = row.cellIterator();
                            while(cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                csvPrinter.print(formatter.formatCellValue(cell));
                            }
                            csvPrinter.println();
                        }
                        csvPrinter.flush();
                        csvPrinter.close();
                    } else {
                        response.setContentType(XLSX);
                        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"export_" + query.getQueryName() + "_" + query.getQueryId() + ".xlsx\"");
                        wb.write(response.getOutputStream());
                    }
                    response.flushBuffer();
                    wb.close();
                }
                else{
                    LOG.error("Extraction type query admit only read operations, no statement executed");
                    this.jobLogService.setAndAddErrorLog(jobLog, jobLogLog, "Extraction type query admit only read operations, no statement executed");
                    throw new RuntimeException("Extraction type query admit only read operations, no statement executed");
                }


            }
            //Altrimenti Eseguo la query come se fosse sempre uno script
            else if(query.getQueryType().equals(TIPO_QUERY.get("UPDATE"))) {
                ScriptRunner scriptRunner;
                StringWriter errorWriter = new StringWriter();
                try {
                    long start = System.currentTimeMillis();
                    LOG.info("Executing: {} ", finalQuery.replaceAll("\n", " "));
                    scriptRunner = new ScriptRunner(connection);
                    scriptRunner.setStopOnError(true);
                    scriptRunner.setErrorLogWriter(new PrintWriter(errorWriter));
                    scriptRunner.runScript(new StringReader(finalQuery));

                    if (LOG.isInfoEnabled()) {
                        LOG.info("Time execute query: {}ms", System.currentTimeMillis() - start);
                    }
                }
                catch (Exception e)
                {
                        LOG.error("Error executing query: " + finalQuery, e +"\n RUNNER ERROR: "+errorWriter.toString());

                    this.jobLogService.setAndAddErrorLog(jobLog, jobLogLog, e.getMessage());
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e)
        {

            this.jobLogService.setAndAddErrorLog(jobLog, jobLogLog, e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally
        {
            try {
                if(rs!=null)
                    rs.close();
                if(stmt!=null)
                    stmt.close();
                if(connection!=null)
                    connection.close();
            }catch (Exception e) {
                LOG.error("Error in close connection: "+e.getMessage());
                this.jobLogService.setAndAddErrorLog(jobLog, jobLogLog, e.getMessage());
                throw new RuntimeException(e);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Time execute overall execQuery method: {}ms", System.currentTimeMillis() - execQueryStart);
        }

        this.jobLogService.jobLogEnd(jobLog);

        return null;
    }

    private boolean isContain(String source, String item) {
        String pattern = "\\b"+item+"\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source.toUpperCase());
        return m.find();
    }

    private void getColumnsFormat(ResultSetMetaData rsmd) throws SQLException {
        this.columnStyleList = this.styleParser.parseColumnStyle(this.columnsFormat,rsmd);
        this.breakRowColumnStyle = this.styleParser.parseBreakRowColumnStyle(this.columnsFormat);
    }

    private void getRowFormat(int rowCount, ResultSet rs) throws SQLException {
        if(hasColumn(rs,StyleParser.ROW_FORMAT_PARAM_NAME)){
            if(rs.getObject(StyleParser.ROW_FORMAT_PARAM_NAME) instanceof String){
                String style = (String)rs.getObject(StyleParser.ROW_FORMAT_PARAM_NAME);
                if(style != null && style.length() > 0){
                    this.styleParser.parseRowStyle(rowCount, style, this.columnStyleList);
                    this.hasRowFormatParam = true;
                }
                else{
                    this.hasRowFormatParam = false;
                }
            }
        }
    }






    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }


    private void formatCellByParams(Sheet sheet,Workbook workbook, int ColNum, int rowCount, Cell cell) {
        if(this.columnStyleList != null && this.columnStyleList.size() > 0){
            ColumnStyle cs = this.columnStyleList.get(ColNum -1);
            if(cs != null){
                this.styleApply.setCellStyle(sheet,workbook,cs, cell, rowCount,hasRowFormatParam);
                //cellStyleCounter++;
                //LOG.info("ccs : " + cellStyleCounter);
            }
        }
    }

    private int manageBreakRowCellByParams(Sheet sheet,Workbook workbook, int rowCount,ResultSet rs,int breakRowLastColNum) throws SQLException {
        if(this.breakRowColumnStyle != null){
            String breakRowColumnName = this.breakRowColumnStyle.getColName();
            if(breakRowColumnName != null){
                //stampo riga di rottura se prima riga o valore colonna di rottura cambia
                if (breakRowColumnCurrentValue == null || !breakRowColumnCurrentValue.equalsIgnoreCase((String) rs.getObject(breakRowColumnName))){
                    breakRowColumnCurrentValue = (String) rs.getObject(breakRowColumnName);
                    rowCount = addBreakRowCell(sheet,workbook,rowCount,breakRowLastColNum);
                }
            }
        }
        return rowCount;
    }


    private int addBreakRowCell(Sheet sheet,Workbook workbook,int rowCount,int breakRowLastColNum){
        Row breakRow = sheet.createRow(rowCount++);
        Cell breakCell = breakRow.createCell(0);
        breakCell.setCellValue(breakRowColumnCurrentValue);
        CellRangeAddress cra = new CellRangeAddress(breakCell.getRow().getRowNum(),breakCell.getRow().getRowNum(),0,breakRowLastColNum);
        sheet.addMergedRegion(cra);
        this.breakRowColumnStyle.setCellRangeAddress(cra);
        this.styleApply.setCellStyle(sheet,workbook,this.breakRowColumnStyle, breakCell, rowCount,hasRowFormatParam);
        //cellStyleCounter++;
        //LOG.info("ccs : " + cellStyleCounter);
        return rowCount;
    }

    private void formatDateCell(Workbook workbook, Cell cell) {
        if(dateCellStyle == null){
            CellStyle cellStyle = workbook.createCellStyle();
            //cellStyleCounter++;
            //LOG.info("ccs by QueryExecutor : " + cellStyleCounter);
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
            dateCellStyle = cellStyle;
        }
        cell.setCellStyle(dateCellStyle);
    }

    private void formatHeader(Workbook workbook, Cell cell) {
        if(headerCellStyle == null){
            CellStyle cellStyle = workbook.createCellStyle();
            //cellStyleCounter++;
            //LOG.info("ccs by QueryExecutor : " + cellStyleCounter);
            cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            headerCellStyle = cellStyle;
        }
        cell.setCellStyle(headerCellStyle);
    }

    private int getBreakRowLastColNum(ResultSet rs, int maxColumn) throws SQLException {
        int breakRowLastColNum;
        if(hasColumn(rs,StyleParser.ROW_FORMAT_PARAM_NAME)){
            breakRowLastColNum = maxColumn - 2;
        }
        else{
            breakRowLastColNum =  maxColumn - 1;
        }
        return breakRowLastColNum;
    }


    private static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

    private String getPathLogo() {
        String path = "";
        PartyContentEx partyContentEx = partyContentDao.getPartyContent("Company", "REPORT_SMALL");
        if(partyContentEx!=null) {
            path = partyContentEx.getDataResource().getObjectInfo();
        }
        return path;
    }

    private void createPDF(HttpServletResponse response, Workbook wb, QueryConfig query) throws Exception{

        PDDocument pdf = new PDDocument();
        PDPage pdfPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        pdf.addPage(pdfPage);

        PDPageContentStream cos = new PDPageContentStream(pdf, pdfPage);

        //Dummy Table
        float margin = 50;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = pdfPage.getMediaBox().getHeight() - margin;
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = pdfPage.getMediaBox().getWidth() - (2 * margin);

        //float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage,
                bottomMargin, tableWidth, margin, pdf, pdfPage, true, true);

        //Company
        UserPreference company = this.userPreferenceService.getUserPreference("ORGANIZATION_PARTY");
        Party party = this.partyService.findByPartyId(company!=null?company.getUserPrefValue():"Company");
        be.quodlibet.boxable.Row<PDPage> headerBox = table.createRow(50);
        be.quodlibet.boxable.Cell<PDPage> cellBox = headerBox.createCell(30, party!=null?party.getPartyName():"");
        cellBox.setFontSize(10);
        cellBox.setTopBorderStyle(null);
        cellBox.setLeftBorderStyle(null);
        cellBox.setRightBorderStyle(null);
        cellBox.setValign(be.quodlibet.boxable.VerticalAlignment.MIDDLE);
        cellBox.setAlign(be.quodlibet.boxable.HorizontalAlignment.LEFT);
        //LOGO
        Image image = null;
        try {
            image = new Image(ImageIO.read(new File(this.getPathLogo())));
            image = image.scaleByWidth(75);
        } catch (Exception e ){
            LOG.error("Cannot load logo for QueryExecutorService");
        }
        if(image!=null)
            cellBox = headerBox.createImageCell(40, image);
        else
            cellBox = headerBox.createCell(40,"LOGO");
        cellBox.setValign(be.quodlibet.boxable.VerticalAlignment.MIDDLE);
        cellBox.setAlign(be.quodlibet.boxable.HorizontalAlignment.CENTER);
        cellBox.setFontSize(10);
        cellBox.setTopBorderStyle(null);
        cellBox.setLeftBorderStyle(null);
        cellBox.setRightBorderStyle(null);
        //QueryName
        cellBox = headerBox.createCell(30,query.getQueryName());
        cellBox.setFontSize(10);
        cellBox.setValign(be.quodlibet.boxable.VerticalAlignment.MIDDLE);
        cellBox.setAlign(be.quodlibet.boxable.HorizontalAlignment.RIGHT);
        cellBox.setTopBorderStyle(null);
        cellBox.setLeftBorderStyle(null);
        cellBox.setRightBorderStyle(null);

        table.addHeaderRow(headerBox);

        if(wb!=null) {
            Iterator<Row> rowIterator = wb.getSheetAt(0).rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                be.quodlibet.boxable.Row<PDPage> rowBox = table.createRow(20);
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellBox = rowBox.createCell((float)100/row.getPhysicalNumberOfCells(), formatter.formatCellValue(cell)
                            .replace("\n", "<br>").replace("\r", "<br>").replace("\t"," "));
                    cellBox.setFontSize(8);
                    if(row.getRowNum()==0) {
                        cellBox.setFont(fontBold);
                    }
                }
            }
        }
        table.draw();
        // close the content stream
        cos.close();
        addFooter(pdf);

        //GN-5131
        //Convert BIRT generated pdf to pdf/a and overwrite the related output file
        PDFA3Components pdfa3Components = new PDFA3Components();
        ConvertPDFtoA3 converter = new ConvertPDFtoA3();
        PDDocument pdfa = converter.Convert(pdf,pdfa3Components);
        pdfa.save(response.getOutputStream());
        pdfa.close();
        pdf.close();
    }


    private void addFooter(PDDocument document) throws Exception {
        // get all number of pages.

        int numberOfPages = document.getNumberOfPages();
        int pageCount = 1;
        for (int i = 0; i < numberOfPages; i++) {
            PDPage fpage = document.getPage(i);
            float x = fpage.getMediaBox().getWidth();
            // content stream to write content in pdf page.
            PDPageContentStream contentStream = new PDPageContentStream(document, fpage, PDPageContentStream.AppendMode.APPEND, true);
            //Draw pages
            PDStreamUtils.write(contentStream, "Pag. " + pageCount, fontPlain, 8, x-70, 50, java.awt.Color.BLACK);
            //Draw date
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new java.util.Date());
            PDStreamUtils.write(contentStream, date, fontPlain, 8, 50, 50, java.awt.Color.BLACK);
            contentStream.close();
            ++pageCount;
        }
    }

}
