package it.memelabs.smartnebula.lmm.querydsl;

import com.querydsl.core.QueryMetadata;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.sql.*;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.service.MainPersistenceConfiguration;
import it.memelabs.smartnebula.lmm.querydsl.generated.Person;
import it.memelabs.smartnebula.lmm.querydsl.generated.QPerson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MainPersistenceConfiguration.class)
@TestPropertySource("/dev.properties")
public class SecondTest {
    private static final Logger LOG = getLogger(SecondTest.class);

    @Autowired
    private DataSource mainDataSource;


    @Test
    public void listener() throws Exception {

        QPerson person = QPerson.person;

        SQLTemplates dialect = new PostgreSQLTemplates(); // SQL-dialect

        SQLQueryFactory queryFactory = new SQLQueryFactory(new Configuration(dialect), mainDataSource);

        queryFactory.getConfiguration().addListener(new TestListener());

        Person record = new Person();
        Long id = queryFactory.select(SQLExpressions.nextval("resources_id_seq")).fetchOne();
       /* record.setId(id);
        record.setOwnerNodeId(1L);
        record.setFirstName("test1");
        record.setLastName("test2");
        record.setTaxIdentificationNumber("sss");
        record.setBirthDate(DateUtil.getStartOfDay(new Date()));
        record.setGender("M");
        record.setResidencyPermit(false);*/
        queryFactory.insert(person).populate(record).execute();


       // List<Person> ret = queryFactory.select(person).from(person).where(person.id.eq(id)).fetch();

       // queryFactory.query().select(person).from(person).where(person.id.eq(id)).iterate();

       // assertThat(ret.size(), is(1));
       // assertThat(ret.get(0).getId(), is(id));
       // assertThat(ret.get(0).toString(), is(record.toString()));
    }

    private static class TestListener extends SQLBaseListener {
        @Override
        public void notifyInsert(RelationalPath<?> entity, QueryMetadata md, List<Path<?>> columns, List<Expression<?>> values, SubQueryExpression<?> subQuery) {
            super.notifyInsert(entity, md, columns, values, subQuery);
        }

        @Override
        public void prePrepare(SQLListenerContext context) {
            super.prePrepare(context);
        }

        @Override
        public void preRender(SQLListenerContext context) {
            super.preRender(context);
        }
    }
}
