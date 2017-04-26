package it.memelabs.smartnebula.lmm.persistence.job.dao;

import it.memelabs.smartnebula.lmm.persistence.PersistenceConfiguration;
import it.memelabs.smartnebula.lmm.persistence.enumeration.AttributeType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobStatus;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.ReferenceObject;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJob;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobAttribute;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobEx;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginDao;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrea Fossi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfiguration.class)
@TestPropertySource("/dev.properties")
@Transactional
public class ActivityJobDaoIT {
    @Autowired
    private ActivityJobDao jobDao;
    @Autowired
    private UserLoginDao userLoginDao;

    @Test
    public void testSaveJob() throws Exception {
        UserLogin user = getUserLogin();
        ActivityJobEx job = new ActivityJobEx();
        job.setCommitRequired(true);
        job.setType(JobType.NOP);
        job.setStatus(JobStatus.SCHEDULED);
        job.setScheduledStamp(new Date());
        job.setReferenceId(1L);
        job.setReferenceObject(ReferenceObject.DOCUMENT);

        List<ActivityJobAttribute> attributes = job.getAttributes();
        ActivityJobAttribute att1 = new ActivityJobAttribute();
        att1.setAttrTypeId(AttributeType.STRING);
        att1.setAttrValue("AAAA");
        att1.setAttrName("value1");
        attributes.add(att1);
        ActivityJobAttribute att2 = new ActivityJobAttribute();
        att2.setAttrTypeId(AttributeType.XML);
        att2.setAttrValue("xml1");
        att2.setAttrName("value2");
        attributes.add(att2);
        jobDao.insert(job, user);

        ActivityJobFilter filter = new ActivityJobFilter();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        filter.setStatus(JobStatus.SCHEDULED);
        filter.setReferenceId(1L);
        filter.setScheduledSince(new Date());
        filter.setScheduledUntil(new Date());
        filter.setType(JobType.NOP);
        jobDao.find(filter);

        ActivityJobFilter filter2 = new ActivityJobFilter();
        filter2.setPage(1);
        filter2.setSize(10);
        List<ActivityJob> jobs = jobDao.find(filter2);
        assertEquals(1, jobs.size());
        ActivityJobEx ret = jobDao.findExById(jobs.get(0).getId());
        assertEquals(2, ret.getAttributes().size());

        boolean del = jobDao.delete(1L, ReferenceObject.DOCUMENT, JobType.NOP);

        assertTrue(del);

        List<ActivityJob> jobs3 = jobDao.find(filter2);
        assertEquals(0, jobs3.size());

    }

    private UserLogin getUserLogin() {
        UserLoginFilter filter = new UserLoginFilter();
        filter.setPage(1);
        filter.setSize(1);
        UserLogin user = userLoginDao.find(filter).first().get(0);
        user.setActiveNode(user.getNodes().get(0));
        return user;
    }
}
