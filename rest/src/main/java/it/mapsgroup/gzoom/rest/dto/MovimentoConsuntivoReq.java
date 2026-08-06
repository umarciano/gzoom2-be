package it.mapsgroup.gzoom.rest.dto;

import java.math.BigDecimal;

/**
 * Un movimento consuntivo da salvare (Portale Referente, CTX_BS).
 * Body element di {@code POST consuntivazione/valori}.
 */
public class MovimentoConsuntivoReq {

    private String workEffortId;
    private String glAccountId;
    private String glFiscalTypeId;   // 'ACTUAL' oppure 'PAR_*'
    private BigDecimal transValue;

    public String getWorkEffortId() { return workEffortId; }
    public void setWorkEffortId(String workEffortId) { this.workEffortId = workEffortId; }

    public String getGlAccountId() { return glAccountId; }
    public void setGlAccountId(String glAccountId) { this.glAccountId = glAccountId; }

    public String getGlFiscalTypeId() { return glFiscalTypeId; }
    public void setGlFiscalTypeId(String glFiscalTypeId) { this.glFiscalTypeId = glFiscalTypeId; }

    public BigDecimal getTransValue() { return transValue; }
    public void setTransValue(BigDecimal transValue) { this.transValue = transValue; }
}
