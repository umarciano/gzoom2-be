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

public class UomTypeDaoIT extends AbstractDaoTest {
    private static final Logger LOG = getLogger(UomTypeDaoIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    UomTypeDao uomTypeDao;

    @Test
    public void daoInsert() throws Exception {
        transactionTemplate.execute(txStatus -> {
            String userLoginId = "admin";
            UomType record = new UomType();
            record.setUomTypeId("Uom_TYPE_2");
            record.setDescription("Primo UomType " + System.currentTimeMillis());
            uomTypeDao.create(record, userLoginId);
            LOG.debug("i" + record.getUomTypeId());

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
            uomTypeDao.update(record, userLoginId);
            LOG.debug("i" + record.getUomTypeId());

            return null;
        });
        
        transactionTemplate.execute(txStatus -> {
            UomType record = new UomType();
        QUomType survey = QUomType.uomType;

        queryFactory.update(survey)
            .where(survey.uomTypeId.eq("Uom_TYPE_1"))
            .set(survey.description, "S")
            .execute();
        return null;
        });
        
        
        transactionTemplate.execute(txStatus -> {
            UomType record = new UomType();
            QUomType survey = QUomType.uomType;
        // Using bean population

            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Update 2");
        
            queryFactory.update(survey)
            .where(survey.uomTypeId.eq("Uom_TYPE_1"))
            .populate(record)
            .execute();
        return null;
        });
    }
}
