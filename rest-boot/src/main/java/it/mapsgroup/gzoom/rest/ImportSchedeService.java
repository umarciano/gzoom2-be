package it.mapsgroup.gzoom.rest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class ImportSchedeService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportSchedeService.class);
    private final ImportSchedeRepository repository;

    @Autowired
    public ImportSchedeService(ImportSchedeRepository repository) {
        this.repository = repository;
    }

    public Map<String,Object> importFromExcel(MultipartFile file) throws Exception {
        List<Map<String,Object>> rows = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
            if (!it.hasNext()) {
                throw new IllegalArgumentException("File Excel vuoto");
            }

            // Leggi header
            Row header = it.next();
            List<String> headers = new ArrayList<>();
            for (Cell c : header) {
                headers.add(c.getStringCellValue().trim());
            }
            LOG.info("Headers letti dal file Excel: {}", headers);

            // Mappa le righe
            int rowNum = 1;
            while (it.hasNext()) {
                Row r = it.next();
                rowNum++;
                if (isRowEmpty(r)) {
                    LOG.info("Riga {} vuota, saltata", rowNum);
                    continue;
                }
                Map<String,Object> map = new HashMap<>();
                
                // Log delle celle raw per debug
                StringBuilder rowDebug = new StringBuilder("Riga " + rowNum + " RAW: ");
                for (int i = 0; i < Math.max(headers.size(), r.getLastCellNum()); i++) {
                    Cell c = r.getCell(i);
                    String cellValue = "";
                    if (c != null) {
                        try {
                            switch (c.getCellType()) {
                                case STRING:
                                    cellValue = c.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(c)) {
                                        cellValue = "DATE:" + c.getDateCellValue();
                                    } else {
                                        cellValue = "NUM:" + c.getNumericCellValue();
                                    }
                                    break;
                                case BOOLEAN:
                                    cellValue = "BOOL:" + c.getBooleanCellValue();
                                    break;
                                case FORMULA:
                                    cellValue = "FORMULA:" + c.getCellFormula();
                                    break;
                                case BLANK:
                                    cellValue = "BLANK";
                                    break;
                                case ERROR:
                                    cellValue = "ERROR_CELL";
                                    break;
                                case _NONE:
                                default:
                                    cellValue = "NONE";
                                    break;
                            }
                        } catch (Exception e) {
                            cellValue = "ERROR:" + e.getMessage();
                        }
                    }
                    rowDebug.append("[").append(i).append("]=").append(cellValue).append(" ");
                }
                LOG.info(rowDebug.toString());
                
                for (int i=0;i<headers.size();i++) {
                    Cell c = r.getCell(i);
                    Object value = null;
                    if (c != null) {
                        switch (c.getCellType()) {
                            case STRING:
                                value = c.getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(c)) {
                                    value = c.getDateCellValue();
                                } else {
                                    value = c.getNumericCellValue();
                                }
                                break;
                            case BOOLEAN:
                                value = c.getBooleanCellValue();
                                break;
                            case FORMULA:
                                try {
                                    value = c.getStringCellValue();
                                } catch (Exception ex) {
                                    value = null;
                                }
                                break;
                            case BLANK:
                            default:
                                value = null;
                        }
                    }
                    map.put(headers.get(i), value);
                }
                LOG.info("Riga {} processata: {}", rowNum, map);
                rows.add(map);
            }
        }

        // Persisti in DB
        int inserted = repository.insertRows(rows);

        Map<String,Object> result = new HashMap<>();
        result.put("inserted", inserted);
        result.put("processed", rows.size());
        return result;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell c = row.getCell(i);
            if (c != null && c.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK) {
                String value = "";
                try {
                    switch (c.getCellType()) {
                        case STRING:
                            value = c.getStringCellValue();
                            break;
                        case NUMERIC:
                            value = String.valueOf(c.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    // Ignora errori
                }
                if (value != null && !value.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
