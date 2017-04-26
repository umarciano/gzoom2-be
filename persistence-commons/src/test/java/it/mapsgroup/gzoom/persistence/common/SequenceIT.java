package it.mapsgroup.gzoom.persistence.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class SequenceIT {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void SequenceTest() throws Exception {
        String seqName = "Test01";
        Long v1 = getCurrentSeqValue();
        String id1 = sequenceGenerator.getNextSeqId(seqName);
        assertThat(id1, is(String.valueOf(v1 + 1)));

        Long v2 = getCurrentSeqValue();
        assertThat(v1 + 10, is(v2));
        for (int i = 0; i < 9; i++) {
            sequenceGenerator.getNextSeqId(seqName);
        }
        Long v3 = getCurrentSeqValue();
        assertThat(v1 + 10, is(v3));

    }

    private Long getCurrentSeqValue() {
        Long value = jdbcTemplate.queryForObject("SELECT SEQ_NAME, SEQ_ID from SEQUENCE_VALUE_ITEM WHERE SEQ_NAME=?", (rs, rowNum) -> {
            return rs.getLong(1);
        }, "Test01");
        return value;
    }
}
