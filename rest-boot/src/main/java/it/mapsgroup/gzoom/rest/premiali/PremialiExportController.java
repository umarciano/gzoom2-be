package it.mapsgroup.gzoom.rest.premiali;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST per l'export verso il sistema esterno "Premiali".
 *
 * <p>Endpoint:</p>
 * <pre>GET /rest/api/premiali/export?annoValutazione=2025&amp;page=0&amp;size=500</pre>
 *
 * <p>L'API è sincrona e paginata. Il client itera incrementando il parametro
 * {@code page} fino a quando {@code (page + 1) * size &gt;= total}.</p>
 *
 * <p>Authenticazione: JWT (header {@code Authorization: Bearer &lt;token&gt;}),
 * ottenuto via {@code POST /rest/api/getToken}. Vedi
 * {@code gzoom2-be/rest-boot/src/main/java/it/mapsgroup/gzoom/rest/premiali/README.md}
 * per il flusso completo.</p>
 */
@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PremialiExportController {

    private final PremialiExportService premialiExportService;

    @Autowired
    public PremialiExportController(PremialiExportService premialiExportService) {
        this.premialiExportService = premialiExportService;
    }

    @GetMapping(value = "/api/premiali/export")
    public Result<PremialeExportRow> exportPremiali(
            @RequestParam("annoValutazione") int annoValutazione,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "500") int size) {
        return Exec.exec("premiali-export",
                () -> premialiExportService.exportByAnno(annoValutazione, page, size));
    }
}
