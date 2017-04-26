package it.memelabs.smartnebula.lmm.persistence.security.dao;

import it.memelabs.smartnebula.lmm.persistence.PersistenceConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfiguration.class)
@TestPropertySource("/dev.properties")@Transactional
public class JdbcTokenStoreIT {
    private static final Logger LOG = getLogger(JdbcTokenStoreIT.class);

    @Autowired
    @Qualifier(value = "mainDataSource")
    DataSource dataSource;

    JdbcTokenStore permitsStorage;

    @Before
    public void setUp() throws Exception {
        permitsStorage = new JdbcTokenStore(dataSource);
    }

    @Test
    public void storeTest() throws Exception {
        String jwt = "test_" + UUID.randomUUID().toString();
        permitsStorage.save(jwt, "test", 10);
        for (int i = 0; i < 100; i++) {
            JdbcTokenStore.Permit permit = permitsStorage.load(jwt);
            assertThat(permit, is(notNullValue()));
            assertThat(permit.token, is(jwt));
            assertThat(permit.username, is("test"));
            assertThat(permit.expiration, is(notNullValue()));
            Thread.sleep(50);
        }

        JdbcTokenStore.Permit permit2 = permitsStorage.prune(jwt);
        assertThat(permit2, is(notNullValue()));
        assertThat(permit2.token, is(jwt));
        assertThat(permit2.username, is("test"));
        assertThat(permit2.expiration, is(notNullValue()));

        JdbcTokenStore.Permit permit3 = permitsStorage.load(jwt);
        assertThat(permit3, is(nullValue()));
    }

    @Ignore
    @Test
    @SuppressWarnings("unchecked")
    public void storeTest_Multi() {
        final Integer[] count = {0};
        Callable task = () -> {
            //wait one second between each test
            synchronized (count) {
                count[0] = count[0] + 1;
            }
            Thread.sleep(count[0] * 100);
            // Thread.sleep(30);
            storeTest();
            try {
                storeTest();
            } catch (Exception e) {
                LOG.error("error on thread", e);
                throw e;
            }
            return null;
        };
        executeMultiThreadedTest(40, task);
    }

    protected <T> void executeMultiThreadedTest(int threadCount, Callable<T> task) {
        List<Callable<T>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<T>> futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            LOG.error("multi thread execution error", e);
            assertTrue(false);
        }
    }
}
