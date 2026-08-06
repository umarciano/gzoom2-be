package it.mapsgroup.gzoom.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Un indicatore di cui l'utente loggato e' referente (WEM_IND_IN_CHARGE della sua UOC),
 * con le UO/schede su cui e' assegnato e la definizione dei parametri di input.
 * Elemento dell'array {@code results} restituito da {@code GET consuntivazione/albero};
 * allineato al modello FE {@code IndicatoreConsuntivo} con in piu' il campo {@code fonte}.
 */
public class IndicatoreConsuntivo {

    private String glAccountId;
    private String codice;          // gl_account.account_code (es. S05)
    private String nome;            // gl_account.account_name
    private String tipo;            // gl_account.calc_custom_method_id: 'A/B*100' | 'SI_NO' | null
    private String fonte;           // gl_account.source
    private String area;            // gl_resource_type.description
    private String descrizione;     // gl_account.description (estesa)
    private List<ParametroDef> parametri = new ArrayList<>();
    private List<UoConsuntivo> uo = new ArrayList<>();

    public IndicatoreConsuntivo() {
    }

    public String getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(String glAccountId) {
        this.glAccountId = glAccountId;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public List<ParametroDef> getParametri() {
        return parametri;
    }

    public void setParametri(List<ParametroDef> parametri) {
        this.parametri = parametri;
    }

    public List<UoConsuntivo> getUo() {
        return uo;
    }

    public void setUo(List<UoConsuntivo> uo) {
        this.uo = uo;
    }
}
