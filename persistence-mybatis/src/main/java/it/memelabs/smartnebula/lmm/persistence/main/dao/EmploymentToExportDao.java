package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EmploymentToExport;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EmploymentToExportExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EmploymentToExportKey;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EmploymentToExportExMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class EmploymentToExportDao {
    // private static final Logger LOG = getLogger(EquipmentEmploymentDao.class);

    private final EmploymentToExportExMapper employmentToExportMapper;


    @Autowired
    public EmploymentToExportDao(EmploymentToExportExMapper employmentToExportMapper) {
        this.employmentToExportMapper = employmentToExportMapper;
    }

    public EmploymentToExport findById(String externalId, Long sessionId) {
        EmploymentToExportKey key = new EmploymentToExportKey();
        key.setSessionId(sessionId);
        key.setEmploymentExternalId(externalId);
        return employmentToExportMapper.selectByPrimaryKey(key);
    }

    public Tuple2<List<EmploymentToExport>, Integer> findByFilter(EmploymentToExportFilter filter, Long ownerNodeId) {
        RowBounds rowBounds = filter.makeRowBounds();
        EmploymentToExportExample equipmentEmploymentExample = new EmploymentToExportExample();
        EmploymentToExportExample.Criteria criteria = equipmentEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(ownerNodeId);
        if (filter.getSessionId() != null)
            criteria.andSessionIdEqualTo(filter.getSessionId());
        equipmentEmploymentExample.setOrderByClause("session_id ASC, employment_external_id ASC");

        int count = employmentToExportMapper.countByExample(equipmentEmploymentExample);
        List<EmploymentToExport> list = employmentToExportMapper.selectByExampleWithRowbounds(equipmentEmploymentExample, rowBounds);
        return new Tuple2<>(list, count);
    }


    public boolean delete(String externalId, Long sessionId) {
        EmploymentToExportKey key = new EmploymentToExportKey();
        key.setSessionId(sessionId);
        key.setEmploymentExternalId(externalId);
        int result = employmentToExportMapper.deleteByPrimaryKey(key);
        return result > 0;
    }

    public int markAsSent(Date modifiedStamp, Long sessionId, Long nodeId) {
        EmploymentToExport record = new EmploymentToExport();
        record.setSent(true);
        record.setModifiedStamp(modifiedStamp);
        EmploymentToExportExample example = new EmploymentToExportExample();
        EmploymentToExportExample.Criteria criteria = example.createCriteria();
        criteria.andSessionIdEqualTo(sessionId);
        criteria.andOwnerNodeIdEqualTo(nodeId);
        return employmentToExportMapper.updateByExampleSelective(record, example);
    }

    public int markPersonEmploymentAsSent(Date modifiedStamp, Long sessionId, Long ownerNodeId) {
        return employmentToExportMapper.markPersonEmploymentAsSent(modifiedStamp, ownerNodeId, sessionId);
    }

    public int markEquipmentEmploymentAsSent(Date modifiedStamp, Long sessionId, Long ownerNodeId) {
        return employmentToExportMapper.markEquipmentEmploymentAsSent(modifiedStamp, ownerNodeId, sessionId);
    }
}
