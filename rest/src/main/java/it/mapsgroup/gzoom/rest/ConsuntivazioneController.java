package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.rest.dto.IndicatoreConsuntivo;
import it.mapsgroup.gzoom.rest.dto.MovimentoConsuntivoReq;
import it.mapsgroup.gzoom.service.ConsuntivazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Portale Referente - Consuntivazione indicatori (CTX_BS).
 * Espone l'albero degli indicatori da consuntivare per l'utente loggato (referente).
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ConsuntivazioneController {

    private final ConsuntivazioneService consuntivazioneService;

    @Autowired
    public ConsuntivazioneController(ConsuntivazioneService consuntivazioneService) {
        this.consuntivazioneService = consuntivazioneService;
    }

    /**
     * Albero Indicatore &gt; UO &gt; parametri per l'utente loggato.
     * <p>
     * Il parametro {@code context} e' accettato per compatibilita' col frontend
     * (oggi solo CTX_BS, gia' vincolato nella query); lo scoping vero e' sull'utente loggato.
     *
     * @return {@code { "results": [ ...IndicatoreConsuntivo... ], "total": n }}
     */
    @RequestMapping(value = "consuntivazione/albero", method = RequestMethod.GET)
    @ResponseBody
    public Result<IndicatoreConsuntivo> albero(
            @RequestParam(value = "context", required = false) String context) {
        return Exec.exec("consuntivazione/albero", () -> consuntivazioneService.albero());
    }

    /**
     * Salva i movimenti consuntivi dell'utente loggato (referente).
     * Body: array di movimenti {@code [{ workEffortId, glAccountId, glFiscalTypeId, transValue }]}.
     *
     * @return {@code { "salvati": n }}
     */
    @RequestMapping(value = "consuntivazione/valori", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> salvaValori(@RequestBody List<MovimentoConsuntivoReq> movimenti) {
        return Exec.exec("consuntivazione/valori", () -> consuntivazioneService.salvaValori(movimenti));
    }
}
