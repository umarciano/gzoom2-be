package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmploymentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmploymentExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentEmploymentMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonEmploymentExMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Component
public class CommonEmploymentDao {
    private static final Logger LOG = getLogger(CommonEmploymentDao.class);
    private final PersonEmploymentExMapper personEmploymentMapper;
    private final EquipmentEmploymentMapper equipmentEmploymentMapper;

    @Autowired
    public CommonEmploymentDao(PersonEmploymentExMapper personEmploymentMapper,
                               EquipmentEmploymentMapper equipmentEmploymentMapper) {
        this.personEmploymentMapper = personEmploymentMapper;
        this.equipmentEmploymentMapper = equipmentEmploymentMapper;
    }


    public String uniqueIdentifier(Type employmentType, Long nodeId) {
        return uniqueIdentifier(employmentType, nodeId, personEmploymentMapper.selectNextExternalId());
    }

    protected static String uniqueIdentifier(Type employmentType, Long nodeId, Long sequence) {
        return String.format("%s_%05d_%020d", employmentType.toString(), nodeId, sequence);
    }

    public boolean exist(String externalId) {
        PersonEmploymentExample pExample = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria pCriteria = pExample.createCriteria();
        pCriteria.andExternalIdEqualTo(externalId);
        EquipmentEmploymentExample eExample = new EquipmentEmploymentExample();
        EquipmentEmploymentExample.Criteria eCriteria = eExample.createCriteria();
        eCriteria.andExternalIdEqualTo(externalId);
        return (personEmploymentMapper.countByExample(pExample) + equipmentEmploymentMapper.countByExample(eExample)) > 0;
    }

    public enum Type {
        PE, EE
    }
}
