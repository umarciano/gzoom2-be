package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Person;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventExport;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.lmm.persistence.main.util.Dummies.getUserLogin;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class WorkLogPersonEventDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(WorkLogPersonEventDaoIT.class);

    @Autowired
    private WorkLogPersonEventDao personEventDao;
    @Autowired
    private PersonDao personDao;


    @Test
    public void testFindExById() throws Exception {
        WorkLogPersonEventEx ret = personEventDao.findPersonEventExById(1004L);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }

    @Test
    public void testFindPersonsByFilter() throws Exception {
        WorkLogEventFilter filter = new WorkLogEventFilter();
        filter.setPage(1);
        filter.setSize(10);
        filter.setWorkLogId(1015L);
        Tuple2<List<WorkLogPersonEventEx>, Integer> ret = personEventDao.findPersonByFilter(filter);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void testFindPersonsChangesByFilter() throws Exception {
        WorkLogEventFilter filter = new WorkLogEventFilter();
        filter.setPage(1);
        filter.setSize(10);
        filter.setWorkLogId(1065L);
        filter.setNotInWeeklyWorkLogId(1007L);
        Tuple2<List<WorkLogPersonEventEx>, Integer> ret = personEventDao.findPersonByFilter(filter);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void findPersonByWorkLogId() throws Exception {
        Tuple2<List<Person>, Integer> ret = personDao.findByWorkLogId(1015L, "andrea", 1, 30);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }


    @Test
    public void findForExport() throws Exception {
        WorkLogEventFilter filter = new WorkLogEventFilter();
        filter.setConstructionSiteId(1017L);
        filter.setCompanyId(1044L);
        filter.setEventTimestampBetween(new DateRange(DateUtil.addDays(new Date(),-100),new Date()));
        filter.setPage(1);
        filter.setSize(Integer.MAX_VALUE);
        List<WorkLogPersonEventExport> result = personEventDao.findForExportByFilter(filter, getUserLogin(2L));
        System.out.println("size=" + (result != null ? result.size() : null));
    }


}
