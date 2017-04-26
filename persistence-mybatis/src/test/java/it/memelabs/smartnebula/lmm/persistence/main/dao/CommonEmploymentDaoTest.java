package it.memelabs.smartnebula.lmm.persistence.main.dao;

import org.junit.Test;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrea Fossi.
 */
public class CommonEmploymentDaoTest {
    @Test
    public void uniqueIdentifier() throws Exception {
            String ret = CommonEmploymentDao.uniqueIdentifier(CommonEmploymentDao.Type.EE, 33L, 4L);
            System.out.println(ret);
            assertThat(ret.length(), is(lessThanOrEqualTo(30)));
            assertThat(ret, is("EE_00033_00000000000000000004"));

    }

}