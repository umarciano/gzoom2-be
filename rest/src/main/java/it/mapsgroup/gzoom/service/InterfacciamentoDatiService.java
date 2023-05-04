package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dao.PartyContentDao;
import it.mapsgroup.gzoom.rest.ValidationException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class InterfacciamentoDatiService {

    private final DataSource dataSource;
    private final PartyContentDao partyContentDao;
    private final UserPreferenceService userPreferenceService;
    private final PartyService partyService;
    private static final Logger LOG = getLogger(QueryExecutorService.class);
    private static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    private final Map<String, String> TIPO_QUERY = new HashMap<String, String>() {{
        put("SELECT", "E");
        put("UPDATE", "A");
    }};

    public InterfacciamentoDatiService(DataSource dataSource, PartyContentDao partyContentDao, UserPreferenceService userPreferenceService, PartyService partyService) {
        this.dataSource = dataSource;
        this.partyContentDao = partyContentDao;
        this.userPreferenceService = userPreferenceService;
        this.partyService = partyService;
    }

    public static String[] addX(int n, String arr[], String x)
    {
        int i;
        String newarr[] = new String[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
        newarr[n] = x;
        return newarr;
    }

    @Transactional
    public String ExecuteInsertRowFile(MultipartFile file, String nomeFile) throws IOException {

        String[] arrayQueryInsert = new String[0];
        String queryFinal = "DELETE FROM " + nomeFile.split("\\.")[0] + "; ";
        String queryInsert = "INSERT INTO " + nomeFile.split("\\.")[0] +" ( ";
        String values = "VALUES ( ";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        FileInputStream fis = (FileInputStream) file.getInputStream();
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Iterator<Row> rowIterator = mySheet.iterator();
        int cols = 0;
        if(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                cols ++;
                switch (cell.getCellType()) {
                    case STRING:
                        queryInsert += cell.getStringCellValue();
                        if(cellIterator.hasNext()){
                            queryInsert += ",";
                        }else{
                            queryInsert += ") ";
                        }
                        break;
                    default:
                }
            }
            arrayQueryInsert = addX(arrayQueryInsert.length,arrayQueryInsert,queryInsert);
        }
        DataFormatter formatter = new DataFormatter();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            for (int cn=0; cn< cols; cn++) {
                Cell cell = row.getCell(cn);
                if (cell == null) {
                    values += "'" + " " + "'" ;
                }
                else {
                    values +=   "'" + formatter.formatCellValue(cell) + "'"  ;

                }
                if(cn < cols -1){
                    values += ",";
                }
            }

//            while (cellIterator.hasNext()) {
//
//                Cell cell = cellIterator.next();
//                values += "'" + formatter.formatCellValue(cell) + "'" ;
//
//                if(cellIterator.hasNext()){
//                    values += ", ";
//                }
//            }

            if(rowIterator.hasNext()){
                values += "),";
            }else {
                values += ");";
            }
//            System.out.print(values +  queryFinal.replaceAll("\n", " "));
            arrayQueryInsert = addX(arrayQueryInsert.length,arrayQueryInsert,values);
            values = "( ";
        }

        String query = "";
        for (String item : arrayQueryInsert)
        {
            query += item;
        }

        try {
            connection = dataSource.getConnection();
            ScriptRunner scriptRunner = null;
            StringWriter errorWriter = new StringWriter();
            scriptRunner = new ScriptRunner(connection);
            scriptRunner.setStopOnError(true);
            scriptRunner.setErrorLogWriter(new PrintWriter(errorWriter));

            //          Query di Insert Row of file
            try {

                long start = System.currentTimeMillis();
                LOG.info("Executing: {} ", queryFinal.replaceAll("\n", " "));
                System.out.print("Executing: " +  queryFinal.replaceAll("\n", " "));

                scriptRunner.runScript(new StringReader(queryFinal));
                scriptRunner.runScript(new StringReader(query));

                if (LOG.isInfoEnabled()) {
                    LOG.info("Time execute query: {}ms", System.currentTimeMillis() - start);
                    System.out.print("Time execute query: " + (System.currentTimeMillis() - start) + "ms");
                }
            }
            catch (Exception e)
            {
                LOG.error("Error executing query: " + queryFinal, e +"\n RUNNER ERROR: "+errorWriter.toString());
                System.out.print("Error executing: " +  queryFinal.replaceAll("\n", " ") + "error is : " + errorWriter.toString());
                throw new ValidationException(e.getMessage());

            }
        } catch (RuntimeException | SQLException e)
        {
            throw new ValidationException(e.getMessage());
        }finally
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
                throw new ValidationException(e.getMessage());
            }
        }
        return "ok";
    }
}
