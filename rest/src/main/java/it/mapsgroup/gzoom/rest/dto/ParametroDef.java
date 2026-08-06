package it.mapsgroup.gzoom.rest.dto;

import java.math.BigDecimal;

/**
 * Definizione di un parametro di input di un indicatore num/den (tipo A/B*100).
 * Fa parte del contratto dell'endpoint {@code GET consuntivazione/albero}
 * ed e' allineato al modello FE {@code ParametroDef}.
 */
public class ParametroDef {

    private String parId;          // gl_fiscal_type PAR_<COD>_<seq>
    private String etichetta;      // gl_fiscal_type.description
    private String ruolo;          // 'A' = numeratore, 'B' = denominatore
    private BigDecimal valoreCorrente; // v1: sempre null (nessuna lettura movimenti AcctgTrans)

    public ParametroDef() {
    }

    public ParametroDef(String parId, String etichetta, String ruolo) {
        this.parId = parId;
        this.etichetta = etichetta;
        this.ruolo = ruolo;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public BigDecimal getValoreCorrente() {
        return valoreCorrente;
    }

    public void setValoreCorrente(BigDecimal valoreCorrente) {
        this.valoreCorrente = valoreCorrente;
    }
}
