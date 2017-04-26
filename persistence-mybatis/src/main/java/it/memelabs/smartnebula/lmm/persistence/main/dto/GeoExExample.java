package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class GeoExExample extends GeoExample {

    public Criteria andGeoAssGeoIdToEqualTo(Criteria criteria, String value) {
        criteria.addCriterion("GEO_ASS_GEO_ID_TO =", value, "geoAssGeoIdTo");
        return criteria;
    }

    public Criteria andGeoAssGeoIdEqualTo(Criteria criteria, String value) {
        criteria.addCriterion("GEO_ASS_GEO_ID =", value, "geoAssGeoId");
        return criteria;
    }

}