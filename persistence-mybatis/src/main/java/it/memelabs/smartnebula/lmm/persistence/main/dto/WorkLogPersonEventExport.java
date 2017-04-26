package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogPersonEventExport extends WorkLogPersonEventEx {
    private PostalAddressEx birthLocation;

    public PostalAddressEx getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(PostalAddressEx birthLocation) {
        this.birthLocation = birthLocation;
    }
}