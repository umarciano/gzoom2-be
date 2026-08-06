package it.mapsgroup.gzoom.querydsl.dto;

import java.math.BigDecimal;

/**
 * Riga PIATTA (indicatore x UO x parametro) restituita dalla query nativa
 * dell'endpoint {@code GET consuntivazione/albero}.
 * <p>
 * Il servizio {@code ConsuntivazioneService} assembla queste righe in albero
 * Indicatore &gt; UO &gt; parametri. I campi parametro (par_id, etichetta, ruolo,
 * seq) sono valorizzati solo per gli indicatori di tipo {@code A/B*100};
 * per {@code SI_NO} / diretto sono {@code null} (LEFT JOIN).
 */
public class ConsuntivazioneAlberoRow {

    private String glAccountId;
    private String accountCode;
    private String accountName;
    private String tipo;          // calc_custom_method_id: 'A/B*100' | 'SI_NO' | null
    private String fonte;         // gl_account.source
    private String area;          // gl_resource_type.description
    private String descrizione;   // gl_account.description (estesa)
    private Integer anno;         // EXTRACT(YEAR FROM work_effort.estimated_completion_date)
    private String workEffortId;
    private String orgUnitId;
    private String uo;            // party_group.group_name
    private BigDecimal peso;      // work_effort_measure.kpi_score_weight
    private String periodTypeId;
    private String statoScheda;   // work_effort.current_status_id
    private Integer seq;          // gl_account_input_calc.input_sequence_num
    private String ruolo;         // gl_account_input_calc.factor_calculator: 'A' | 'B'
    private String parId;         // gl_fiscal_type_id (PAR_*)
    private String etichetta;     // gl_fiscal_type.description
    private BigDecimal valoreActual; // valore ACTUAL gia' salvato per (UO)
    private BigDecimal valorePar;    // valore PAR_* gia' salvato per (UO, parametro)

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getUo() {
        return uo;
    }

    public void setUo(String uo) {
        this.uo = uo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public String getStatoScheda() {
        return statoScheda;
    }

    public void setStatoScheda(String statoScheda) {
        this.statoScheda = statoScheda;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
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

    public BigDecimal getValoreActual() {
        return valoreActual;
    }

    public void setValoreActual(BigDecimal valoreActual) {
        this.valoreActual = valoreActual;
    }

    public BigDecimal getValorePar() {
        return valorePar;
    }

    public void setValorePar(BigDecimal valorePar) {
        this.valorePar = valorePar;
    }
}
