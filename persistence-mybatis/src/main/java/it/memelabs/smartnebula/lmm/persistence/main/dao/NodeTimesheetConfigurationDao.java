package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeTimesheetConfiguration;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeTimesheetConfigurationExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.NodeTimesheetConfigurationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class NodeTimesheetConfigurationDao {

    private final NodeTimesheetConfigurationMapper configurationMapper;

    @Autowired
    public NodeTimesheetConfigurationDao(NodeTimesheetConfigurationMapper configurationMapper) {
        this.configurationMapper = configurationMapper;
    }


    public List<NodeTimesheetConfiguration> findAll() {
        NodeTimesheetConfigurationExample example = new NodeTimesheetConfigurationExample();
        NodeTimesheetConfigurationExample.Criteria criteria = example.createCriteria();
        criteria.andDisabledEqualTo(false);
        return configurationMapper.selectByExample(example);
    }

    public NodeTimesheetConfiguration findById(Long id) {
        return configurationMapper.selectByPrimaryKey(id);
    }
}
