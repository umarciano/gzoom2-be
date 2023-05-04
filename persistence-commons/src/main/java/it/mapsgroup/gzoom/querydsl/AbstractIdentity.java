package it.mapsgroup.gzoom.querydsl;

/**
 * @author Andrea Fossi.
 */
public interface AbstractIdentity {

    java.time.LocalDateTime getCreatedStamp();

    void setCreatedStamp(java.time.LocalDateTime createdStamp);

    java.time.LocalDateTime getCreatedTxStamp();

    void setCreatedTxStamp(java.time.LocalDateTime createdTxStamp);

    java.time.LocalDateTime getLastUpdatedStamp();

    void setLastUpdatedStamp(java.time.LocalDateTime lastUpdatedStamp);

    java.time.LocalDateTime getLastUpdatedTxStamp();

    void setLastUpdatedTxStamp(java.time.LocalDateTime lastUpdatedTxStamp);

}
