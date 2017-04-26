package it.memelabs.smartnebula.lmm.persistence.main.dto;

/**
 * @author Andrea Fossi.
 */
public class WeeklyWorkLogPersonExport extends WeeklyWorkLogPersonEx {
    private PersonEmploymentEx personEmployment;
    private PostalAddressEx birthLocation;

    public PersonEmploymentEx getPersonEmployment() {
        return personEmployment;
    }

    public void setPersonEmployment(PersonEmploymentEx personEmployment) {
        this.personEmployment = personEmployment;
    }

    public PostalAddressEx getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(PostalAddressEx birthLocation) {
        this.birthLocation = birthLocation;
    }
}
