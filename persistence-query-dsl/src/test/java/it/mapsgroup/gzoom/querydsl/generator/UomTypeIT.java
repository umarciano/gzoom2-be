package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.group.GroupBy;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoTest;
import it.mapsgroup.gzoom.querydsl.dto.*;

public class UomTypeIT extends AbstractDaoTest {
    private static final Logger LOG = getLogger(UomTypeIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    @Transactional
    public void select() throws Exception {
        QUomType uomType = QUomType.uomType;

        List<UomType> ret = queryFactory.select(uomType).from(uomType).transform(GroupBy.groupBy(uomType.uomTypeId).list(uomType));

        System.out.println("name ret.size() " + ret.size());
        ret.forEach(p -> System.out.println("p " + p.getUomTypeId()));
    }

    @Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
            QUomType uomType = QUomType.uomType;

            UomType record = new UomType();
            record.setUomTypeId("PROVA_UOM_TYPE");
            record.setDescription("Primo UomType ");
            long i = queryFactory.insert(uomType).populate(record).execute();
            LOG.debug("i" + i);

            return null;
        });
    }
    
    @Test
    public void update() throws Exception {
        transactionTemplate.execute(txStatus -> {
            setUpdateTimestamp(record);
            
            QUomType uomType = QUomType.uomType;
            long i = queryFactory.update(uomType)
            .set(uomType.description, "Primo UomType 2 Updated")
            .where(uomType.uomTypeId.eq("PROVA_UOM_TYPE"))
            .execute();
            
            LOG.debug("i" + i);

            return null;
        });
    }
    
    /*@Test
    public void delete() throws Exception {
        transactionTemplate.execute(txStatus -> {
            QUomType uomType = QUomType.uomType;
            long i = queryFactory.delete(uomType)
            .where(uomType.uomTypeId.eq("PROVA_UOM_TYPE"))
            .execute();
            
            LOG.debug("i" + i);

            return null;
        });
    }*/
}
