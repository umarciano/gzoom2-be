package it.memelabs.smartnebula.lmm.querydsl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.*;
import it.memelabs.smartnebula.lmm.querydsl.ex.PersonEx;
import it.memelabs.smartnebula.lmm.querydsl.ex.PersonEx2;
import it.memelabs.smartnebula.lmm.querydsl.generated.Person;
import it.memelabs.smartnebula.lmm.querydsl.generated.QPerson;
import it.memelabs.smartnebula.lmm.querydsl.generated.QUserLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static it.memelabs.smartnebula.lmm.querydsl.QBeanUtils.merge;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = it.memelabs.smartnebula.lmm.persistence.service.MainPersistenceConfiguration.class)
@TestPropertySource("/dev.properties")
public class FirstTest {
    private static final Logger LOG = getLogger(FirstTest.class);

    @Autowired
    @Deprecated
    private DataSource mainDataSource;

    @Autowired
    private SQLQueryFactory queryFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    public void name() throws Exception {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            transactionTemplate.execute(status2 -> {
                QPerson person = QPerson.person;
                QPostalAddress postalAddress = QPostalAddress.postalAddress;

                QBean<PersonEx> personExQBean = bean(PersonEx.class, bean(Person.class, person.all()).as("person"), bean(PostalAddress.class, postalAddress.all()).as("postalAddress"));

                List<PersonEx> ret = queryFactory.select(person, postalAddress).from(person).innerJoin(person.personAddressIdFkey, postalAddress)
                        .where(postalAddress.postalCodeGeoId.isNotNull())
                        .transform(GroupBy.groupBy(person.id).list(personExQBean));
                ret.size();
                System.out.println("TxManager.stamp2 " + ((CustomTxManager) txManager).getStamp());
                return null;
            });
            System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
            return null;
        });
    }

    @Test
    @Transactional
    public void name2() throws Exception {
        System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
        name3();
        System.out.println("TxManager.stamp1 " + ((CustomTxManager) txManager).getStamp());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void name3() {
        QPerson person = QPerson.person;
        QPostalAddress postalAddress = QPostalAddress.postalAddress;

        QBean<PersonEx> personExQBean = bean(PersonEx.class, bean(Person.class, person.all()).as("person"), bean(PostalAddress.class, postalAddress.all()).as("postalAddress"));

        List<PersonEx> ret = queryFactory.select(person, postalAddress).from(person).innerJoin(person.personAddressIdFkey, postalAddress)
                .where(postalAddress.postalCodeGeoId.isNotNull())
                .transform(GroupBy.groupBy(person.id).list(personExQBean));
        ret.size();
        System.out.println("TxManager.stamp2 " + ((CustomTxManager) txManager).getStamp());
    }

    @Test
    public void peronsNative() throws Exception {
        QPerson person = QPerson.person;
        QPostalAddress postalAddress = new QPostalAddress("person_postal_address");

        // SQLTemplates dialect = new PostgreSQLTemplates(); // SQL-dialect

        // SQLQueryFactory queryFactory = new SQLQueryFactory(new Configuration(dialect), mainDataSource);

        List<Person> ret = queryFactory.select(person).from(person)
                .join(person.personAddressIdFkey, postalAddress)
                .where(postalAddress.postalCode.isNotNull(), postalAddress.id.gt(0L))
                .fetch();

        ret.size();


    }

    /**
     * person that contains two postal_address
     *
     * @throws Exception
     */
    @Test
    public void persons() throws Exception {
        QPerson person = QPerson.person;
        QPostalAddress postalAddress = new QPostalAddress("person_postal_address");
        QPostalAddress birthLocation = new QPostalAddress("person_birth_location");

        SQLTemplates dialect = new PostgreSQLTemplates(); // SQL-dialect

        SQLQueryFactory queryFactory = new SQLQueryFactory(new Configuration(dialect), mainDataSource);


        QBean<PersonEx2> personEx = bean(PersonEx2.class,
                merge(person.all(),
                        bean(PostalAddress.class, postalAddress.all()).as("postalAddress"),
                        bean(PostalAddress.class, birthLocation.all()).as("birthLocation")));


        SQLQuery<?> where = queryFactory
                .from(person)
                .innerJoin(person.personAddressIdFkey, postalAddress)
                .innerJoin(person.personBirthLocationIdFkey, birthLocation)
                .where(postalAddress.postalCodeGeoId.isNotNull());
        List<PersonEx2> ret = where
                .transform(GroupBy.groupBy(person.id).list(personEx));
        ret.size();
    }

    /**
     * Check if can aggregate collection
     * limit/offset give wrong result if cartesian product is greather than 1 (userLogin has more than one node)
     *
     * @throws Exception
     */
    @Test
    public void userLogin() throws Exception {
        QUserLogin userLogin = QUserLogin.userLogin;
        QNode node = QNode.node;
        QUserLoginNodeAssoc assoc = QUserLoginNodeAssoc.userLoginNodeAssoc;

        QBean<UserLoginEx> userLoginExBean = bean(UserLoginEx.class,
                merge(userLogin.all(), GroupBy.list(node).as("nodes")));

        SQLTemplates dialect = new PostgreSQLTemplates(); // SQL-dialect
        SQLQueryFactory queryFactory = new SQLQueryFactory(new Configuration(dialect), mainDataSource);

        List<UserLoginEx> ret = queryFactory
                .from(userLogin)
                .innerJoin(userLogin._userLoginNodeAssocUserLoginIdFkey, assoc)
                .innerJoin(assoc.userLoginNodeAssocNodeIdFkey, node)
                .limit(10)
                .offset(5)
                .orderBy(userLogin.id.asc())
                .transform(GroupBy.groupBy(userLogin.id).list(userLoginExBean));
        ret.size();
    }


    /**
     * Check if can aggregate collection
     * limit/offset give wrong result if cartesian product is greather than 1 (userLogin has more than one node)
     *
     * @throws Exception
     */
    @Test
    public void userLogin2() throws Exception {
        QUserLogin userLogin = QUserLogin.userLogin;
        QNode node = QNode.node;
        QUserLoginNodeAssoc assoc = QUserLoginNodeAssoc.userLoginNodeAssoc;

        QBean<UserLoginEx> userLoginExBean = bean(UserLoginEx.class,
                merge(userLogin.all(), GroupBy.list(node).as("nodes")));

        SQLTemplates dialect = new PostgreSQLTemplates(); // SQL-dialect
        SQLQueryFactory queryFactory = new SQLQueryFactory(new Configuration(dialect), mainDataSource);

        SQLQuery<?> sqlQuery = queryFactory
                .from(userLogin)
                .innerJoin(userLogin._userLoginNodeAssocUserLoginIdFkey, assoc)
                .innerJoin(assoc.userLoginNodeAssocNodeIdFkey, node)
                .limit(10)
                .offset(5)
                .orderBy(userLogin.id.asc());
        sqlQuery.setUseLiterals(false);
        SQLBindings sql = sqlQuery.getSQL();
        System.out.println(sql.getSQL());
        List<UserLoginEx> ret = sqlQuery
                .transform(GroupBy.groupBy(userLogin.id).list(userLoginExBean));
        ret.size();
        //  queryFactory.query().getSQL()
        //  queryFactory.select().from(Exception.)
    }


}
