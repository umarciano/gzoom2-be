package it.mapsgroup.gzoom.querydsl.dao;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dao.UomTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.UomType;
import it.mapsgroup.gzoom.querydsl.dto.QUomType;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

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

            UomType record = new UomType();
            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Primo UomType " + System.currentTimeMillis());
            uomTypeDao.create(record);
            LOG.debug("i" + record.getUomTypeId());

            return null;
        });
    }

    @Test
    public void daoUpdate() throws Exception {
        transactionTemplate.execute(txStatus -> {

            UomType record = new UomType();
            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Update Primo UomType " + System.currentTimeMillis());
            uomTypeDao.update(record);
            LOG.debug("i" + record.getUomTypeId());

            return null;
        });
    }
}
