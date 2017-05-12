package it.mapsgroup.gzoom.persistence.common;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class CustomTransactionStatus implements TransactionStatus {
    private final TransactionStatus transactionStatus;
    private final LocalDateTime timestamp;

    public CustomTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public boolean isNewTransaction() {
        return transactionStatus.isNewTransaction();
    }

    @Override
    public boolean hasSavepoint() {
        return transactionStatus.hasSavepoint();
    }

    @Override
    public void setRollbackOnly() {
        transactionStatus.setRollbackOnly();
    }

    @Override
    public boolean isRollbackOnly() {
        return transactionStatus.isRollbackOnly();
    }

    @Override
    public void flush() {
        transactionStatus.flush();
    }

    @Override
    public boolean isCompleted() {
        return transactionStatus.isCompleted();
    }

    @Override
    public Object createSavepoint() throws TransactionException {
        return transactionStatus.createSavepoint();
    }

    @Override
    public void rollbackToSavepoint(Object savepoint) throws TransactionException {
        transactionStatus.rollbackToSavepoint(savepoint);
    }

    @Override
    public void releaseSavepoint(Object savepoint) throws TransactionException {
        transactionStatus.releaseSavepoint(savepoint);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
}
