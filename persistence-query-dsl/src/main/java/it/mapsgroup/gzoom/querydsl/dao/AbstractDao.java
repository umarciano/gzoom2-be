package it.mapsgroup.gzoom.querydsl.dao;

import it.mapsgroup.gzoom.persistence.common.CustomTransactionStatus;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

/**
 * @author Andrea Fossi.
 */
public abstract class AbstractDao {

    public TransactionStatus getTxStatus() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            return TransactionAspectSupport.currentTransactionStatus();
        } else return null;
    }

    public LocalDateTime getTxTimestamp() {
        TransactionStatus txStatus = getTxStatus();
        if (txStatus != null && txStatus instanceof CustomTransactionStatus) {
            return ((CustomTransactionStatus) txStatus).getTimestamp();
        } else {
            return null;
        }
    }

    public void setCreatedTimestamp(AbstractIdentity record) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime txTimestamp = getTxTimestamp();
        record.setCreatedStamp(now);
        record.setCreatedTxStamp(txTimestamp);
        record.setLastUpdatedStamp(now);
        record.setLastUpdatedTxStamp(txTimestamp);
    }

    public void setUpdateTimestamp(AbstractIdentity record) {
        record.setLastUpdatedStamp(LocalDateTime.now());
        record.setLastUpdatedTxStamp(getTxTimestamp());
    }
}
