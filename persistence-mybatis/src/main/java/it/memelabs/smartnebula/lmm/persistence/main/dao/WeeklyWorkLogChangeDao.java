package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WeeklyWorkLogChangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author sivi.
 */
@Service
public class WeeklyWorkLogChangeDao {
    // private static final Logger LOG = getLogger(WeeklyWorkLogChangeDao.class);

    private WeeklyWorkLogChangeMapper mapper;

    @Autowired
    public WeeklyWorkLogChangeDao(WeeklyWorkLogChangeMapper mapper) {
        this.mapper = mapper;
    }

    public List<Company> selectCompanyByIds(Collection<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.selectCompanyByIds(idList);
    }

    /**
     * @param idList
     * @return The id field is indeed the employment id
     */
    public List<Person> selectPersonByEmplIds(Collection<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.selectPersonByEmplIds(idList);
    }

    /**
     * @param idList
     * @return The id field is indeed the employment id
     */
    public List<Equipment> selectEquipmentByEmplIds(Collection<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.selectEquipmentByEmplIds(idList);
    }


    /**
     * @param idList
     * @return personEmployment with need person and company fields
     */
    public List<PersonEmploymentEx> selectPersonEmplByIds(Collection<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.selectPersonEmplByIds(idList);
    }

    /**
     * @param idList
     * @return equipmentEmployment with need equipment and company fields
     */
    public List<EquipmentEmploymentEx> selectEquipmentEmplByIds(Collection<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.selectEquipmentEmplByIds(idList);
    }

}
