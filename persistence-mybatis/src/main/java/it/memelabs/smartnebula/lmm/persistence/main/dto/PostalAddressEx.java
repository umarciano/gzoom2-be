package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class PostalAddressEx extends PostalAddress {

    private Geo countryGeo;
    private Geo regionGeo;
    private Geo provinceGeo;
    private Geo municipalityGeo;
    private Geo postalCodeGeo;

    public Geo getCountryGeo() {
        return countryGeo;
    }

    public void setCountryGeo(Geo countryGeo) {
        this.countryGeo = countryGeo;
    }

    public Geo getMunicipalityGeo() {
        return municipalityGeo;
    }

    public void setMunicipalityGeo(Geo municipalityGeo) {
        this.municipalityGeo = municipalityGeo;
    }

    public Geo getPostalCodeGeo() {
        return postalCodeGeo;
    }

    public void setPostalCodeGeo(Geo postalCodeGeo) {
        this.postalCodeGeo = postalCodeGeo;
    }

    public Geo getProvinceGeo() {
        return provinceGeo;
    }

    public void setProvinceGeo(Geo provinceGeo) {
        this.provinceGeo = provinceGeo;
    }

    public Geo getRegionGeo() {
        return regionGeo;
    }

    public void setRegionGeo(Geo regionGeo) {
        this.regionGeo = regionGeo;
    }
}
