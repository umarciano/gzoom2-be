package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Arrays;
import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Company;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Equipment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Person;


public class WeeklyWorkLogChangeDaoIT extends AbstractDaoTestIT {

    @Autowired
    private WeeklyWorkLogChangeDao dao;

    @Test
    public void testCompany() {
        List<Company> list = dao.selectCompanyByIds(Arrays.asList(999L, 101L, 102L, 103L));
        System.out.println("size=" + (list != null ? list.size() : null));
    }

    @Test
    public void testPersonEmpl() {
        List<Person> list = dao.selectPersonByEmplIds(Arrays.asList(999L, 101L, 102L, 103L));
        System.out.println("size=" + (list != null ? list.size() : null));
    }

    @Test
    public void testEquipmentEmpl() {
        List<Equipment> list = dao.selectEquipmentByEmplIds(Arrays.asList(999L, 101L, 102L, 103L));
        System.out.println("size=" + (list != null ? list.size() : null));
    }
}
