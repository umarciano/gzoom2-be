package it.mapsgroup.gzoom.rest.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Una UO/scheda (work_effort CTX_BS) che usa l'indicatore: qui il referente
 * inserisce i valori del periodo. Fa parte del contratto dell'endpoint
 * {@code GET consuntivazione/albero} ed estende il modello FE {@code UoConsuntivo}
 * con {@code orgUnitId}, {@code statoScheda} e {@code periodo}.
 */
public class UoConsuntivo {

    private String workEffortId;
    private String uo;             // party_group.group_name
    private BigDecimal peso;       // work_effort_measure.kpi_score_weight
    private String orgUnitId;      // work_effort.org_unit_id
    private String statoScheda;    // work_effort.current_status_id
    private String periodo;        // work_effort_measure.period_type_id (tipo, es. FISCAL_YEAR)
    private Integer anno;          // anno del ciclo (EXTRACT YEAR da work_effort.estimated_completion_date)
    private BigDecimal valoreActual; // valore ACTUAL gia' salvato (read-back)
    private BigDecimal punteggio;    // v1: sempre null (nessun calcolo scoring)
    private Map<String, BigDecimal> valoriParametri = new LinkedHashMap<>(); // parId -> valore PAR_* gia' salvato

    public UoConsuntivo() {
    }

    public String getWorkEffortId() {
        return workEffortId;
    }

    public void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
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

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getStatoScheda() {
        return statoScheda;
    }

    public void setStatoScheda(String statoScheda) {
        this.statoScheda = statoScheda;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public BigDecimal getValoreActual() {
        return valoreActual;
    }

    public void setValoreActual(BigDecimal valoreActual) {
        this.valoreActual = valoreActual;
    }

    public BigDecimal getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(BigDecimal punteggio) {
        this.punteggio = punteggio;
    }

    public Map<String, BigDecimal> getValoriParametri() {
        return valoriParametri;
    }

    public void setValoriParametri(Map<String, BigDecimal> valoriParametri) {
        this.valoriParametri = valoriParametri;
    }
}
