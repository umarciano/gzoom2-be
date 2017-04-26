package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class AccidentEx extends Accident implements AbstractIdentifiable, AbstractIdentity {
    private ConstructionSite constructionSite;
    private PersonEmploymentEx personEmployment;

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }

    public PersonEmploymentEx getPersonEmployment() {
        return personEmployment;
    }

    public void setPersonEmployment(PersonEmploymentEx personEmployment) {
        this.personEmployment = personEmployment;
    }
}