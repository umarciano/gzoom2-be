package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ImportSchedeController {

    private final ImportSchedeService importSchedeService;

    @Autowired
    public ImportSchedeController(ImportSchedeService importSchedeService) {
        this.importSchedeService = importSchedeService;
    }

    /** Endpoint per effettuare salvataggio a DB dei file excel usati per gli import massivi 
     *  Il salvataggio avviene nella tabella import_schede, il cui script sql di creazione è presente al path ..\workspace\create_table_import_schede.sql
     *  La chiamata va effettuata sotto la cartella workspace
     *  Sostituire il token dopo "Bearer" con il token di accesso che si può trovare dopo il login negli strumenti da sviluppatore > Application > Local Storage
     *  Si possono inserire i valori direttamente nel template già pushato nella repository, modificandolo.
     *  Es. 
     *  curl.exe -X POST http://localhost:8081/rest/api/import/schede/upload -H "Authorization: Bearer eyJraWQiOiJrMSIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJsbW0iLCJhdWQiOiJlbmQtdXNlciIsImV4cCI6MTc2ODIzNzI2NiwianRpIjoiT0dQVVlxVGZtQmhzZ2s5SWlQMFhsZyIsImlhdCI6MTc2ODIzMzY2Niwic3ViIjoiYWRtaW4iLCJmaXJzdE5hbWUiOiJBTU1JTklTVFJBVE9SRSIsImxhc3ROYW1lIjoiU0lTVEVNQSIsImV4dGVybmFsTG9naW5LZXkiOiJFTDUyOTIwODk4MDE3IiwicGVybWlzc2lvbnMiOjEsImlkIjoiYWRtaW4iLCJ1c2VybmFtZSI6ImFkbWluIn0.JHBDMVm8w3YyuCJ-Wm0ELB-1VNLNBD7HLCEiEIP0lLfrd8uCFz8A0zvAOXy82wqzHJdynENRseDAR633vIQ-UzjyMC9eR-JV5NnHuj9GyCaXi1f3_25TFwd2MwCT1ceNaACckYRBK2ijW5HeicCNEQmw25vO8vAej69ukJVw101EnCpdoSNSqD8SkNxbJiif-0TZxuD_DzWK0ekzSj5c_7V3kOzymLSNEyG8SH4kWG9f9OyMOqkDTa0Op8qapB4hzYrbzB1l4rKRRf6IDDjcSn1ZVHE0ODwXnLO7cdEZMMWtiyt3V_VABtx27t0hR59w_nqIANkGzge5ChmG1GbaxQ" -F "file=@gzoom-legacy/script/templates/IMPORT_SCHEDE.xlsx"
    */
    @PostMapping(value = {"/import/schede/upload", "/api/import/schede/upload"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String,Object>> uploadSchede(@RequestParam("file") MultipartFile file) {
        return Exec.exec("import-schede-upload", () -> {
            Map<String,Object> result = importSchedeService.importFromExcel(file);
            return ResponseEntity.ok(result);
        });
    }
}
