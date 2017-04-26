package it.mapsgroup.gzoom.persistence.common;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class CustomTxManager implements PlatformTransactionManager {
    private PlatformTransactionManager txManager;
    private TxStampHolder txStampHolder;

    public CustomTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
        this.txStampHolder = new TxStampHolder();
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        TransactionStatus transaction = txManager.getTransaction(definition);
        if (transaction.isNewTransaction()) txStampHolder.push();
        else txStampHolder.push(getStamp());
        return transaction;
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        try {
            txManager.commit(status);
            txStampHolder.pop();
        } catch (Exception e) {
            txStampHolder.pop();
            throw e;
        }
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        try {
            txManager.rollback(status);
            txStampHolder.pop();
        } catch (Exception e) {
            txStampHolder.pop();
            throw e;
        }
    }

    public Date getStamp() {
        return txStampHolder.getStamp();
    }
}
