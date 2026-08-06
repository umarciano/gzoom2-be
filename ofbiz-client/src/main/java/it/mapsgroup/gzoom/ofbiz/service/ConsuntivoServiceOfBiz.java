package it.mapsgroup.gzoom.ofbiz.service;

import it.mapsgroup.gzoom.ofbiz.client.impl.AuthenticationOfBizClientImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper per il salvataggio di un movimento consuntivo (Portale Referente, CTX_BS)
 * verso il legacy via XML-RPC.
 * <p>
 * Autenticazione robusta e stabile: login by userId (senza password) tramite
 * {@code gzSimpleLoginWithOnlyUserLoginId} -> sessionId, poi invocazione del servizio
 * legacy ESPORTATO {@code saveIndicatorConsuntivo} con quella sessione. Il login e' fatto
 * al momento a ogni chiamata, quindi un riavvio del legacy non lascia chiavi "stale".
 * Il movimento risulta creato dall'utente reale (login by userId).
 */
public class ConsuntivoServiceOfBiz {

    private final AuthenticationOfBizClientImpl client;

    public ConsuntivoServiceOfBiz(AuthenticationOfBizClientImpl client) {
        this.client = client;
    }

    /**
     * Salva UN movimento (ACTUAL o PAR_*) per (workEffortId, glAccountId).
     *
     * @param userLoginId   utente referente (login by userId, no password)
     * @param workEffortId  scheda
     * @param glAccountId   indicatore
     * @param glFiscalTypeId 'ACTUAL' oppure 'PAR_*'
     * @param transValue    valore
     * @return mappa risultato del servizio legacy (acctgTransId, workEffortMeasureId)
     */
    public Map<String, Object> saveMovimento(String userLoginId, String workEffortId, String glAccountId,
                                             String glFiscalTypeId, Double transValue) {
        // login by userId (no password) -> externalLoginKey (l'handler XML-RPC autentica i servizi
        // auth="true" tramite externalLoginKey nei parametri, non tramite il sessionId).
        Map<String, Object> login = client.login(userLoginId, "GZOOM2"); // gzSimpleLoginWithOnlyUserLoginId
        String externalLoginKey = (String) login.get("externalLoginKey");

        Map<String, Object> params = new HashMap<>();
        params.put("externalLoginKey", externalLoginKey);
        params.put("workEffortId", workEffortId);
        params.put("glAccountId", glAccountId);
        params.put("glFiscalTypeId", glFiscalTypeId);
        params.put("transValue", transValue);
        // organizzazione a cui e' registrato l'indicatore in GlAccountOrganization (WECAL -> 'Company').
        params.put("defaultOrganizationPartyId", "Company");

        return client.execute("saveIndicatorConsuntivo", externalLoginKey, params);
    }
}
