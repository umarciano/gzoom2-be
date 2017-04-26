package it.mapsgroup.gzoom.persistence.common;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class TxStampHolderTest {
    private static final Logger LOG = getLogger(TxStampHolderTest.class);

    TxStampHolder txHolder1;
    TxStampHolder txHolder2;

    @Before
    public void setUp() throws Exception {
        txHolder1 = new TxStampHolder();
        txHolder2 = new TxStampHolder();
    }

    @Test
    public void singleThread() throws Exception {
        test(txHolder1);
    }

    public void test(TxStampHolder txHolder) throws Exception {
        assertThat(txHolder.getStamp(), is(nullValue()));
        Date first = txHolder.push();
        assertThat(txHolder.getStamp(), is(first));
        Date second = txHolder.push();
        assertThat(txHolder.getStamp(), is(second));
        txHolder.pop();
        assertThat(txHolder.getStamp(), is(first));
        txHolder.pop();
        assertThat(txHolder.getStamp(), is(nullValue()));
    }

    @Test
    public void stackTest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Callable<Boolean> c = () -> {
                try {
                  //  Thread.sleep(Math.round(Math.random() * 1000));
                    test(txHolder1);
                    System.out.println("C1 Thread " + finalI);
                } catch (Exception e) {
                    LOG.error("Callable: " + finalI, e);
                    return false;
                }
                return true;
            };
            Callable<Boolean> c2 = () -> {
                try {
                  //  Thread.sleep(Math.round(Math.random() * 1000));
                    test(txHolder2);
                    System.out.println("C2 Thread " + finalI);
                } catch (Exception e) {
                    LOG.error("Callable: " + finalI, e);
                    return false;
                }
                return true;
            };
            futures.add(executor.submit(c));
            futures.add(executor.submit(c2));
        }
        executor.shutdown();
        for (Future<Boolean> f : futures) {
            assertTrue(f.get());
        }


    }
}
