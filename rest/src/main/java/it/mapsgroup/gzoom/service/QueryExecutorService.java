package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dao.QueryConfigDao;
import it.mapsgroup.gzoom.querydsl.dto.QueryConfig;
import it.mapsgroup.gzoom.rest.ValidationException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.poi.ss.usermodel.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Query Executor service.
 *
 * @author Antonio Calò
 */
@Service
public class QueryExecutorService {

    private final DataSource dataSource;
    private final QueryConfigDao queryConfigDao;
    private static final Logger LOG = getLogger(QueryExecutorService.class);

    private final Map<String, String> TIPO_QUERY = new HashMap<String, String>() {{
        put("SELECT", "E");
        put("UPDATE", "U");
    }};

    @Autowired
    public QueryExecutorService(@Qualifier(value = "mainDataSource") DataSource dataSource, QueryConfigDao queryConfigDao) {
        this.dataSource = dataSource;
        this.queryConfigDao = queryConfigDao;
    }

    @Transactional
    public String execQuery(QueryConfig query, HttpServletRequest req, HttpServletResponse response) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
            status.getClass();
        }


        String finalQuery = "";
        if(query!=null) {
            //GET Query
            finalQuery = query.getQueryInfo();
            //Replace Condition
            if(query.getCond0Info()!=null && !query.getCond0Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND0#",query.getCond0Info());
            if(query.getCond1Info()!=null && !query.getCond1Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND1#",query.getCond1Info());
            if(query.getCond2Info()!=null && !query.getCond2Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND2#",query.getCond2Info());
            if(query.getCond3Info()!=null && !query.getCond3Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND3#",query.getCond3Info());
            if(query.getCond4Info()!=null && !query.getCond4Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND4#",query.getCond4Info());
            if(query.getCond5Info()!=null && !query.getCond5Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND5#",query.getCond5Info());
            if(query.getCond6Info()!=null && !query.getCond6Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND6#",query.getCond6Info());
            if(query.getCond7Info()!=null && !query.getCond7Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND7#",query.getCond7Info());
            if(query.getCond8Info()!=null && !query.getCond8Info().equals(""))
                finalQuery = finalQuery.replaceAll("(?i)#COND8#",query.getCond8Info());

            finalQuery = finalQuery.replaceAll("(?i)#USERID#",SecurityContextHolder.getContext().getAuthentication().getName());
        }

        try {

            connection = dataSource.getConnection();

            //Se la query è una select crea un file xlsx
            if(query.getQueryType().equals(TIPO_QUERY.get("SELECT")))
            {
                stmt = connection.prepareStatement(finalQuery);
                rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                int maxColumn = rsmd.getColumnCount();

                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("ExecuteQuery");

                // create header
                Row headerRow = sheet.createRow(0);
                for (int i = 1; i <= maxColumn; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Cell headerCell = headerRow.createCell(i - 1);
                    headerCell.setCellValue(columnName);
                }

                //create detail
                int rowCount = 1;
                while (rs.next()) {
                    Row row = sheet.createRow(rowCount++);

                    for (int i = 1; i <= maxColumn; i++) {
                        Object valueObject = rs.getObject(i);

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
                        } else cell.setCellValue((String) valueObject);

                    }

                }


                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"export_" +query.getQueryName()+"_"+ query.getQueryId() + ".xlsx\"");
                wb.write(response.getOutputStream());
                response.flushBuffer();
                wb.close();

            }
            //Altrimenti Eseguo la query come se fosse sempre uno script
            else {
                ScriptRunner scriptRunner = null;
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
                        throw new RuntimeException(e);
                }
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private static void formatDateCell(Workbook workbook, Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        cell.setCellStyle(cellStyle);
    }
}
