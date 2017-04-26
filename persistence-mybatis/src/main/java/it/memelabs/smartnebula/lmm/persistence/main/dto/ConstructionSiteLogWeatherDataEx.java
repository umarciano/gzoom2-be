package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class ConstructionSiteLogWeatherDataEx extends ConstructionSiteLogWeatherData implements AbstractIdentity {
    private ConstructionSiteLogWeatherCatalog windType;
    private ConstructionSiteLogWeatherCatalog weatherCondition;
    private ConstructionSiteLogWeatherCatalog activitySuspension;

    public ConstructionSiteLogWeatherCatalog getWindType() {
        return windType;
    }

    public void setWindType(ConstructionSiteLogWeatherCatalog windType) {
        this.windType = windType;
    }

    public ConstructionSiteLogWeatherCatalog getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(ConstructionSiteLogWeatherCatalog weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public ConstructionSiteLogWeatherCatalog getActivitySuspension() {
        return activitySuspension;
    }

    public void setActivitySuspension(ConstructionSiteLogWeatherCatalog activitySuspension) {
        this.activitySuspension = activitySuspension;
    }
}