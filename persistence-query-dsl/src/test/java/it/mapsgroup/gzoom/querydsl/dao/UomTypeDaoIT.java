package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dto.QUomType;
import it.mapsgroup.gzoom.querydsl.dto.UomType;

public class UomTypeDaoIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(UomTypeDaoIT.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    UomTypeDao uomTypeDao;

    @Test
    public void daoInsert() throws Exception {
        transactionTemplate.execute(txStatus -> {
            String userLoginId = "admin";
            UomType record = new UomType();
            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Primo UomType " + System.currentTimeMillis());
            uomTypeDao.create(record, userLoginId);
            LOG.debug("uomTypeId " + record.getUomTypeId());

            return null;
        });
    }

    @Test
    public void daoUpdate() throws Exception {
        transactionTemplate.execute(txStatus -> {
            String userLoginId = "admin";
            
            UomType record = new UomType();
            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Update Primo UomType " + System.currentTimeMillis());
            uomTypeDao.update("Uom_TYPE_1", record, userLoginId);
            LOG.debug("uomTypeId " + record.getUomTypeId());

            return null;
        });
    }
    
    @Test
    public void daoDelete() throws Exception {
        transactionTemplate.execute(txStatus -> {
            uomTypeDao.delete("Uom_TYPE_1");
            
            return null;
        });
    }
}
