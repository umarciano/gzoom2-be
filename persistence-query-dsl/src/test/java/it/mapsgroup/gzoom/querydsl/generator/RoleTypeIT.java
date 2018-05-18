package it.mapsgroup.gzoom.querydsl.generator;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.group.GroupBy;
import com.querydsl.sql.SQLQueryFactory;

import it.mapsgroup.gzoom.querydsl.dao.AbstractDaoIT;
import it.mapsgroup.gzoom.querydsl.dto.QRoleType;
import it.mapsgroup.gzoom.querydsl.dto.RoleType;

public class RoleTypeIT extends AbstractDaoIT {
    private static final Logger LOG = getLogger(RoleTypeIT.class);

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    @Transactional
    public void select() throws Exception {
        QRoleType roleType = QRoleType.roleType;

        List<RoleType> ret = queryFactory.select(roleType).from(roleType).transform(GroupBy.groupBy(roleType.roleTypeId).list(roleType));

        System.out.println("name ret.size() " + ret.size());
        ret.forEach(p -> System.out.println("p " + p.getRoleTypeId()));
    }

    @Test
    public void insert() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QRoleType roleType = QRoleType.roleType;

        	RoleType record = new RoleType();
            record.setRoleTypeId("PROVA_ROLE_TYPE");
            record.setDescription("Primo roleType ");
            long i = queryFactory.insert(roleType).populate(record).execute();
            LOG.info("i " + i);

            return null;
        });
    }

    @Test
    public void update() throws Exception {
        transactionTemplate.execute(txStatus -> {
            // setUpdateTimestamp(record);

        	QRoleType roleType = QRoleType.roleType;
            long i = queryFactory.update(roleType).set(roleType.description, "Primo roleType 2 Updated").where(roleType.roleTypeId.eq("PROVA_ROLE_TYPE")).execute();

            LOG.info("i " + i);

            return null;
        });

        transactionTemplate.execute(txStatus -> {
        	QRoleType roleType = QRoleType.roleType;

            long i = queryFactory.update(roleType).where(roleType.roleTypeId.eq("ROLE_TYPE_2")).set(roleType.description, "S").execute();
            LOG.info("i " + i);
            
            return null;
        });

        transactionTemplate.execute(txStatus -> {
        	RoleType record = new RoleType();
            QRoleType survey = QRoleType.roleType;
            // Using bean population

            record.setRoleTypeId("ROLE_TYPE_2");
            record.setDescription("Update 2");

            long i = queryFactory.update(survey).where(survey.roleTypeId.eq("ROLE_TYPE_2")).populate(record).execute();
            LOG.info("i " + i);
            
            return null;
        });
    }

    @Test
    public void delete() throws Exception {
        transactionTemplate.execute(txStatus -> {
        	QRoleType roleType = QRoleType.roleType;
            long i = queryFactory.delete(roleType).where(roleType.roleTypeId.eq("PROVA_ROLE_TYPE")).execute();

            LOG.info("i " + i);

            return null;
        });
    }
}
