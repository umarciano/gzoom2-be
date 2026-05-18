package it.mapsgroup.gzoom.rest.premiali;

import java.math.BigDecimal;

/**
 * Riga restituita dall'export "Premiali": rappresenta i punteggi di valutazione
 * di un singolo dipendente per una scheda CTX_EP in un anno specifico.
 */
public class PremialeExportRow {

    private String workEffortId;
    private String workEffortName;
    private String workEffortTypeId;
    private String tipologiaScheda;
    private Integer annoValutazione;

    // Dati dipendente (valutato)
    private String partyId;
    private String matricola;
    private String codiceFiscale;
    private String nome;
    private String cognome;

    // Unità organizzativa
    private String orgUnitId;
    private String codiceUnitaOrganizzativa;
    private String descrizioneUnitaOrganizzativa;

    // Punteggi
    private BigDecimal scoreEp;
    private BigDecimal scoreBs;
    private BigDecimal adjustedScoreEp;
    private BigDecimal adjustedScoreBs;
    private BigDecimal overallEpBsScore;

    public String getWorkEffortId() { return workEffortId; }
    public void setWorkEffortId(String workEffortId) { this.workEffortId = workEffortId; }

    public String getWorkEffortName() { return workEffortName; }
    public void setWorkEffortName(String workEffortName) { this.workEffortName = workEffortName; }

    public String getWorkEffortTypeId() { return workEffortTypeId; }
    public void setWorkEffortTypeId(String workEffortTypeId) { this.workEffortTypeId = workEffortTypeId; }

    public String getTipologiaScheda() { return tipologiaScheda; }
    public void setTipologiaScheda(String tipologiaScheda) { this.tipologiaScheda = tipologiaScheda; }

    public Integer getAnnoValutazione() { return annoValutazione; }
    public void setAnnoValutazione(Integer annoValutazione) { this.annoValutazione = annoValutazione; }

    public String getPartyId() { return partyId; }
    public void setPartyId(String partyId) { this.partyId = partyId; }

    public String getMatricola() { return matricola; }
    public void setMatricola(String matricola) { this.matricola = matricola; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getOrgUnitId() { return orgUnitId; }
    public void setOrgUnitId(String orgUnitId) { this.orgUnitId = orgUnitId; }

    public String getCodiceUnitaOrganizzativa() { return codiceUnitaOrganizzativa; }
    public void setCodiceUnitaOrganizzativa(String codiceUnitaOrganizzativa) { this.codiceUnitaOrganizzativa = codiceUnitaOrganizzativa; }

    public String getDescrizioneUnitaOrganizzativa() { return descrizioneUnitaOrganizzativa; }
    public void setDescrizioneUnitaOrganizzativa(String descrizioneUnitaOrganizzativa) { this.descrizioneUnitaOrganizzativa = descrizioneUnitaOrganizzativa; }

    public BigDecimal getScoreEp() { return scoreEp; }
    public void setScoreEp(BigDecimal scoreEp) { this.scoreEp = scoreEp; }

    public BigDecimal getScoreBs() { return scoreBs; }
    public void setScoreBs(BigDecimal scoreBs) { this.scoreBs = scoreBs; }

    public BigDecimal getAdjustedScoreEp() { return adjustedScoreEp; }
    public void setAdjustedScoreEp(BigDecimal adjustedScoreEp) { this.adjustedScoreEp = adjustedScoreEp; }

    public BigDecimal getAdjustedScoreBs() { return adjustedScoreBs; }
    public void setAdjustedScoreBs(BigDecimal adjustedScoreBs) { this.adjustedScoreBs = adjustedScoreBs; }

    public BigDecimal getOverallEpBsScore() { return overallEpBsScore; }
    public void setOverallEpBsScore(BigDecimal overallEpBsScore) { this.overallEpBsScore = overallEpBsScore; }
}
