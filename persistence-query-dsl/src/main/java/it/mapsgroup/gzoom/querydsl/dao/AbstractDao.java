package it.mapsgroup.gzoom.querydsl.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;

import it.mapsgroup.gzoom.persistence.common.CustomTransactionStatus;
import it.mapsgroup.gzoom.querydsl.AbstractIdentity;

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
    
    public Predicate filterByDate(DateTimePath<LocalDateTime> fromDate, DateTimePath<LocalDateTime> thruDate) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        return fromDate.loe(currentTimestamp.toLocalDateTime()).and(thruDate.isNull().or(thruDate.goe(currentTimestamp.toLocalDateTime())));
    }

    
}
