package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import it.mapsgroup.gzoom.querydsl.dto.Uom;

public class UomDaoIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(UomDaoIT.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    UomDao uomDao;

    @Test
    public void daoInsert() throws Exception {
        transactionTemplate.execute(txStatus -> {
            String userLoginId = "admin";
            Uom record = new Uom();
            record.setUomId("UOM_1");
            record.setUomTypeId("UOM_TYPE_1");
            record.setDescription("Primo Uom ");
            uomDao.create(record, userLoginId);
            LOG.debug("uomTypeId " + record.getUomTypeId());

            return null;
        });
    }

    @Test
    public void daoUpdate() throws Exception {
        transactionTemplate.execute(txStatus -> {
            String userLoginId = "admin";

            Uom record = new Uom();
            record.setUomId("Uom_1");
            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Update Primo Uom");
            record.setAbbreviation("Ab");
            uomDao.update("Uom_1", record, userLoginId);
            LOG.debug("uomTypeId " + record.getUomTypeId());

            return null;
        });
    }

    @Test
    public void daoDelete() throws Exception {
        transactionTemplate.execute(txStatus -> {
            uomDao.delete("Uom_1");

            return null;
        });
    }
}
