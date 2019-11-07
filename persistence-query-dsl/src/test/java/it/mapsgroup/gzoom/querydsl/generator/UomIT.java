package it.mapsgroup.gzoom.querydsl.generator;

import static com.querydsl.core.types.Projections.bean;
import static it.mapsgroup.gzoom.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLBindings;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.*;

public class UomIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(UomIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    @Transactional
    public void select() throws Exception {
        QUom uom = QUom.uom;
        QUomType uomType = QUomType.uomType;

        QBean<UomEx> uomExQBean = bean(UomEx.class, merge(uom.all(), bean(UomType.class, uomType.all()).as("uomType")));
                        
        SQLQuery<Tuple> tupleSQLQuery = queryFactory.select(uom, uomType)
                .from(uom)
                .innerJoin(uom.uomToType, uomType)
                .orderBy(uomType.description.asc());
                
        SQLBindings bindings = tupleSQLQuery.getSQL();
        LOG.info("{}",bindings.getSQL());
        LOG.info("{}",bindings.getNullFriendlyBindings());
        List<UomEx> ret = tupleSQLQuery
                .transform(GroupBy.groupBy(uom.uomId).list(uomExQBean));
                

        System.out.println("name ret.size() " + ret.size());
        ret.forEach(p -> System.out.println("p " + p.getUomId() + " " + p.getUomType().getUomTypeId()));
    }

    @Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
            QUom uom = QUom.uom;

            Uom record = new Uom();
            record.setUomId("PROVA_UOM");
            record.setUomTypeId("PROVA_UOM_TYPE");
            record.setDescription("Primo Uom ");
            record.setAbbreviation("Primo Uom ");
            record.setDecimalScale(BigInteger.valueOf(2));
            record.setMinValue(BigDecimal.ZERO);
            record.setMaxValue(new BigDecimal(100.0));
            long i = queryFactory.insert(uom).populate(record).execute();
            LOG.info("i " + i);

            return null;
        });
    }

    @Test
    public void update() throws Exception {
        transactionTemplate.execute(txStatus -> {
            Uom record = new Uom();
            QUom uom = QUom.uom;
            // Using bean population

            record.setUomTypeId("Uom_TYPE_1");
            record.setDescription("Update 2");

            long i = queryFactory.update(uom).where(uom.uomId.eq("PROVA_UOM")).populate(record).execute();
            LOG.info("i " + i);
            
            return null;
        });
    }

    @Test
    public void delete() throws Exception {
        transactionTemplate.execute(txStatus -> {
            QUom uom = QUom.uom;
            long i = queryFactory.delete(uom).where(uom.uomId.eq("PROVA_UOM")).execute();

            LOG.info("i " + i);

            return null;
        });
    }
}
