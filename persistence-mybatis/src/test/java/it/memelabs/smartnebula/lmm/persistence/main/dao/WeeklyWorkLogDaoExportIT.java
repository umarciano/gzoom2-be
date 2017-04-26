package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogEquipmentExport;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogPersonExport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Andrea Fossi.
 */

public class WeeklyWorkLogDaoExportIT extends AbstractDaoTestIT {

    @Autowired
    private WeeklyWorkLogDao weeklyWorkLogDao;

    @Test
    public void testSearchPersons() {
        List<WeeklyWorkLogPersonExport> ret = weeklyWorkLogDao.findPersonForExport(1005L,null);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }
    @Test
    public void testSearchEquipments() {
        List<WeeklyWorkLogEquipmentExport> ret = weeklyWorkLogDao.findEquipmentForExport(1005L,null);
        System.out.println("size=" + (ret != null ? ret.size() : null));
    }

}
