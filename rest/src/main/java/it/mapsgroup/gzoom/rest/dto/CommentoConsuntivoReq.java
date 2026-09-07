package it.mapsgroup.gzoom.rest.dto;

/**
 * Richiesta di salvataggio nota del referente su un indicatore-su-scheda
 * (endpoint {@code POST consuntivazione/commento}). La nota viene APPESA
 * (attribuita: referente + data) al campo {@code work_effort_measure.comments},
 * senza sovrascrivere eventuali testi di admin/Direttori.
 */
public class CommentoConsuntivoReq {

    private String workEffortId;
    private String glAccountId;
    private String testo;

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
