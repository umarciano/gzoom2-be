package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.ofbiz.service.ConsuntivoServiceOfBiz;
import it.mapsgroup.gzoom.querydsl.dao.ConsuntivazioneAlberoDao;
import it.mapsgroup.gzoom.querydsl.dto.ConsuntivazioneAlberoRow;
import it.mapsgroup.gzoom.rest.dto.IndicatoreConsuntivo;
import it.mapsgroup.gzoom.rest.dto.MovimentoConsuntivoReq;
import it.mapsgroup.gzoom.rest.dto.ParametroDef;
import it.mapsgroup.gzoom.rest.dto.UoConsuntivo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Servizio del Portale Referente - Consuntivazione indicatori (CTX_BS).
 * <p>
 * Assembla le righe piatte (indicatore x UO x parametro) restituite dal DAO in
 * un albero Indicatore &gt; UO &gt; parametri, pronto per il frontend.
 * Lo scoping e' sull'utente loggato ({@link it.mapsgroup.gzoom.security.Principals}):
 * un referente vede solo i propri indicatori, nessun ulteriore controllo permessi.
 */
@Service
public class ConsuntivazioneService {

    private static final Logger LOG = getLogger(ConsuntivazioneService.class);

    private final ConsuntivazioneAlberoDao consuntivazioneAlberoDao;
    private final ConsuntivoServiceOfBiz consuntivoServiceOfBiz;

    @Autowired
    public ConsuntivazioneService(ConsuntivazioneAlberoDao consuntivazioneAlberoDao,
                                  ConsuntivoServiceOfBiz consuntivoServiceOfBiz) {
        this.consuntivazioneAlberoDao = consuntivazioneAlberoDao;
        this.consuntivoServiceOfBiz = consuntivoServiceOfBiz;
    }

    /**
     * Salva i movimenti consuntivi dell'utente loggato (referente) chiamando il legacy
     * (login by userId via XML-RPC, poi saveIndicatorConsuntivo). Vedi doc 13 §2.2/§3.
     *
     * @return mappa con il numero di movimenti salvati.
     */
    public Map<String, Object> salvaValori(List<MovimentoConsuntivoReq> movimenti) {
        String userLoginId = principal().getUserLoginId();
        int salvati = 0;
        if (movimenti != null) {
            for (MovimentoConsuntivoReq m : movimenti) {
                consuntivoServiceOfBiz.saveMovimento(userLoginId, m.getWorkEffortId(), m.getGlAccountId(),
                        m.getGlFiscalTypeId(), m.getTransValue() == null ? null : m.getTransValue().doubleValue());
                salvati++;
            }
        }
        LOG.info("consuntivazione/valori [userLoginId={}] movimenti salvati = {}", userLoginId, salvati);
        Map<String, Object> res = new HashMap<>();
        res.put("salvati", salvati);
        return res;
    }

    /**
     * Albero degli indicatori da consuntivare per l'utente loggato (referente).
     *
     * @return oggetto con proprieta' {@code results} = array di {@link IndicatoreConsuntivo}.
     */
    public Result<IndicatoreConsuntivo> albero() {
        String userLoginId = principal().getUserLoginId();
        List<ConsuntivazioneAlberoRow> rows = consuntivazioneAlberoDao.getAlbero(userLoginId);
        List<IndicatoreConsuntivo> tree = assemble(rows);
        LOG.info("consuntivazione/albero [userLoginId={}] indicatori = {}", userLoginId, tree.size());
        return new Result<>(tree, tree.size());
    }

    /**
     * Raggruppa le righe piatte per gl_account_id, costruendo per ciascun indicatore
     * l'elenco distinto dei parametri (par_id non null, ordinati per seq) e l'elenco
     * distinto delle UO (work_effort).
     */
    private List<IndicatoreConsuntivo> assemble(List<ConsuntivazioneAlberoRow> rows) {
        // Mappe di appoggio per preservare l'ordine (SQL: ORDER BY account_code, uo, seq).
        Map<String, IndicatoreConsuntivo> indByGlAccount = new LinkedHashMap<>();
        Map<String, Map<String, ParametroDef>> paramByGlAccount = new LinkedHashMap<>();
        Map<String, Map<String, UoConsuntivo>> uoByGlAccount = new LinkedHashMap<>();

        for (ConsuntivazioneAlberoRow row : rows) {
            String glAccountId = row.getGlAccountId();

            IndicatoreConsuntivo ind = indByGlAccount.get(glAccountId);
            if (ind == null) {
                ind = new IndicatoreConsuntivo();
                ind.setGlAccountId(glAccountId);
                ind.setCodice(row.getAccountCode());
                ind.setNome(row.getAccountName());
                ind.setTipo(row.getTipo());
                ind.setFonte(row.getFonte());
                ind.setArea(row.getArea());
                ind.setDescrizione(row.getDescrizione());
                indByGlAccount.put(glAccountId, ind);
                paramByGlAccount.put(glAccountId, new LinkedHashMap<>());
                uoByGlAccount.put(glAccountId, new LinkedHashMap<>());
            }

            // Parametro (solo per tipo A/B*100: par_id valorizzato). Distinto per par_id.
            if (row.getParId() != null) {
                Map<String, ParametroDef> params = paramByGlAccount.get(glAccountId);
                if (!params.containsKey(row.getParId())) {
                    params.put(row.getParId(),
                            new ParametroDef(row.getParId(), row.getEtichetta(), row.getRuolo()));
                }
            }

            // UO/scheda. Distinta per work_effort_id.
            if (row.getWorkEffortId() != null) {
                Map<String, UoConsuntivo> uos = uoByGlAccount.get(glAccountId);
                UoConsuntivo uo = uos.get(row.getWorkEffortId());
                if (uo == null) {
                    uo = new UoConsuntivo();
                    uo.setWorkEffortId(row.getWorkEffortId());
                    uo.setUo(row.getUo());
                    uo.setPeso(row.getPeso());
                    uo.setOrgUnitId(row.getOrgUnitId());
                    uo.setStatoScheda(row.getStatoScheda());
                    uo.setPeriodo(row.getPeriodTypeId());
                    uo.setAnno(row.getAnno());
                    uos.put(row.getWorkEffortId(), uo);
                }
                // read-back: valore ACTUAL (per UO) + valori PAR_* (per parametro).
                uo.setValoreActual(row.getValoreActual());
                if (row.getParId() != null && row.getValorePar() != null) {
                    uo.getValoriParametri().put(row.getParId(), row.getValorePar());
                }
            }
        }

        // Ordina i parametri per seq (la seq e' nota tramite la riga di origine).
        // La seq viene ricavata riscorrendo le righe: costruiamo un ordinamento per par_id.
        Map<String, Integer> seqByPar = seqByParId(rows);

        List<IndicatoreConsuntivo> result = new ArrayList<>(indByGlAccount.size());
        for (Map.Entry<String, IndicatoreConsuntivo> entry : indByGlAccount.entrySet()) {
            String glAccountId = entry.getKey();
            IndicatoreConsuntivo ind = entry.getValue();

            List<ParametroDef> params = new ArrayList<>(paramByGlAccount.get(glAccountId).values());
            params.sort(Comparator.comparing(
                    p -> seqByPar.getOrDefault(p.getParId(), Integer.MAX_VALUE)));
            ind.setParametri(params);

            ind.setUo(new ArrayList<>(uoByGlAccount.get(glAccountId).values()));
            result.add(ind);
        }
        return result;
    }

    /** Prima seq (input_sequence_num) osservata per ciascun par_id, per l'ordinamento parametri. */
    private Map<String, Integer> seqByParId(List<ConsuntivazioneAlberoRow> rows) {
        Map<String, Integer> seqByPar = new LinkedHashMap<>();
        for (ConsuntivazioneAlberoRow row : rows) {
            if (row.getParId() != null && !seqByPar.containsKey(row.getParId())) {
                seqByPar.put(row.getParId(), row.getSeq() == null ? Integer.MAX_VALUE : row.getSeq());
            }
        }
        return seqByPar;
    }
}
