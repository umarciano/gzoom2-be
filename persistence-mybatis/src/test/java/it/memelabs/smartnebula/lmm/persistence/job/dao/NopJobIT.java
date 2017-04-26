package it.memelabs.smartnebula.lmm.persistence.job.dao; /**
 * @author Andrea Fossi.
 */

import it.memelabs.smartnebula.lmm.persistence.enumeration.AttributeType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobStatus;
import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobAttribute;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobEx;
import it.memelabs.smartnebula.lmm.persistence.job.util.JobUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginDao;
import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 *
 * test execution of {@link it.memelabs.smartnebula.batch.job.impl.NopJob}
 */

//@Transactional
public class NopJobIT extends AbstractDaoTestIT {
    @Autowired
    private ActivityJobDao jobDao;
    @Autowired
    private UserLoginDao userLoginDao;

    @Test
    public void testName() throws Exception {
        ActivityJobEx job = new ActivityJobEx();
        job.setStatus(JobStatus.SCHEDULED);
        job.setCommitRequired(true);
        job.setScheduledStamp(new Date());
        job.setType(JobType.NOP);
        List<ActivityJobAttribute> attributes = job.getAttributes();
        attributes.add(createAttribute("testString", "test_value", AttributeType.STRING));
        attributes.add(createAttribute("testLong", JobUtil.toXml(33L), AttributeType.XML));
        jobDao.insert(job,  getUserLogin());

    }

    private UserLogin getUserLogin() {
        UserLoginFilter filter = new UserLoginFilter();
        filter.setPage(1);
        filter.setSize(1);
        UserLogin userLogin = userLoginDao.find(filter).first().get(0);
        userLogin.setActiveNode(userLogin.getNodes().get(0));
        return userLogin;
    }

    private ActivityJobAttribute createAttribute(String attrName, String attrValue, AttributeType attrTypeId) {
        ActivityJobAttribute ret = new ActivityJobAttribute();
        ret.setAttrValue(attrValue);
        ret.setAttrTypeId(attrTypeId);
        ret.setAttrName(attrName);
        return ret;
    }
}
