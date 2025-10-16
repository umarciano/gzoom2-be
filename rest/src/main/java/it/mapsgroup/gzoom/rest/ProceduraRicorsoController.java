package it.mapsgroup.gzoom.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Controller per il download della documentazione procedura di ricorso
 */
@RestController
@RequestMapping(value = "/procedura-ricorso")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProceduraRicorsoController {

    private static final Logger LOG = LoggerFactory.getLogger(ProceduraRicorsoController.class);
    private static final String PDF_FILENAME = "documentazione_procedura_ricorso.pdf";
    
    // Path assoluto - file ora è sotto gzoom2-be
    private static final String PDF_PATH_ABSOLUTE = "C:\\GZOOM\\workspace\\gzoom2-be\\static_content\\" + PDF_FILENAME;
    
    // Path relativi da provare (dal modulo rest-boot)
    private static final String[] PDF_PATHS_RELATIVE = {
        "static_content/" + PDF_FILENAME,              // Dalla root di gzoom2-be
        "../static_content/" + PDF_FILENAME,           // Dal modulo rest o rest-boot
        "../../static_content/" + PDF_FILENAME         // Se siamo dentro target
    };

    /**
     * Endpoint per scaricare il PDF della procedura di ricorso
     * 
     * @return ResponseEntity con il file PDF
     */
    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadProceduraRicorso() {
        
        LOG.info("===== INIZIO DOWNLOAD PROCEDURA RICORSO =====");
        LOG.info("Working Directory: {}", System.getProperty("user.dir"));
        
        try {
            File file = null;
            
            // Prova prima i path relativi
            for (String relativePath : PDF_PATHS_RELATIVE) {
                File tempFile = new File(relativePath);
                LOG.info("Tentativo path relativo: {}", relativePath);
                LOG.info("Path assoluto completo: {}", tempFile.getAbsolutePath());
                LOG.info("File exists: {}", tempFile.exists());
                LOG.info("File readable: {}", tempFile.canRead());
                
                if (tempFile.exists() && tempFile.canRead()) {
                    file = tempFile;
                    LOG.info("✓ FILE TROVATO con path relativo: {}", file.getAbsolutePath());
                    break;
                } else {
                    LOG.warn("✗ File NON trovato o non leggibile: {}", tempFile.getAbsolutePath());
                }
            }
            
            // Se non trovato, prova path assoluto
            if (file == null || !file.exists()) {
                LOG.warn("File non trovato nei path relativi, provo path assoluto");
                file = new File(PDF_PATH_ABSOLUTE);
                LOG.info("Tentativo path assoluto: {}", PDF_PATH_ABSOLUTE);
                LOG.info("File exists: {}", file.exists());
                LOG.info("File readable: {}", file.canRead());
            }
            
            if (!file.exists()) {
                LOG.error("===== FILE PDF NON TROVATO =====");
                LOG.error("Path assoluto provato: {}", PDF_PATH_ABSOLUTE);
                LOG.error("Working directory: {}", System.getProperty("user.dir"));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            LOG.info("✓ FILE PDF TROVATO: {}", file.getAbsolutePath());
            LOG.info("Dimensione file: {} bytes", file.length());
            LOG.info("✓ FILE PDF TROVATO: {}", file.getAbsolutePath());
            LOG.info("Dimensione file: {} bytes", file.length());
            
            // Leggi il file come byte array
            byte[] pdfBytes = Files.readAllBytes(file.toPath());
            
            // Imposta gli headers per forzare il download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", PDF_FILENAME);
            headers.setContentLength(pdfBytes.length);
            headers.set("Access-Control-Expose-Headers", "Content-Disposition");
            
            LOG.info("✓ Download procedura ricorso completato con successo");
            LOG.info("Bytes inviati: {}", pdfBytes.length);
            LOG.info("===== FINE DOWNLOAD PROCEDURA RICORSO =====");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
                    
        } catch (IOException e) {
            LOG.error("===== ERRORE I/O DURANTE LETTURA FILE PDF =====", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
