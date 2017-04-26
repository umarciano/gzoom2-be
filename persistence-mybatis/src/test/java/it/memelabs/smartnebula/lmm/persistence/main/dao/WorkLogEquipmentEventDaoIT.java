package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Equipment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogEquipmentEventEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogEquipmentEventExport;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventEx;
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
public class WorkLogEquipmentEventDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(WorkLogEquipmentEventDaoIT.class);

    @Autowired
    private WorkLogEquipmentEventDao workLogEquipmentEventDao;

    @Autowired
    private EquipmentDao equipmentDao;


    /************************* Equipments *******************/

    @Test
    public void testFindEquipmentsByFilter() throws Exception {
        WorkLogEventFilter filter = new WorkLogEventFilter();
        filter.setPage(1);
        filter.setSize(10);
        filter.setWorkLogId(1013L);
        Tuple2<List<WorkLogEquipmentEventEx>, Integer> ret = workLogEquipmentEventDao.findEquipmentByFilter(filter);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void findEquipmentByWorkLogId() throws Exception {
        Tuple2<List<Equipment>, Integer> ret = equipmentDao.findByWorkLogId(1013L, "ZA001AA", 1, 30);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }

    @Test
    public void findForExport() throws Exception {
        WorkLogEventFilter filter = new WorkLogEventFilter();
        filter.setConstructionSiteId(1017L);
        filter.setCompanyId(1044L);
        filter.setEventTimestampBetween(new DateRange(DateUtil.addDays(new Date(), -100), new Date()));
        filter.setPage(1);
        filter.setSize(Integer.MAX_VALUE);
        List<WorkLogEquipmentEventExport> result = workLogEquipmentEventDao.findForExportByFilter(filter, getUserLogin(2L));
        System.out.println("size=" + (result != null ? result.size() : null));
    }
}
