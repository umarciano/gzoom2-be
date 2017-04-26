package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeConfigurationEx;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.NodeConfigurationExMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class NodeConfigurationDao {
    private static final Logger LOG = getLogger(NodeConfigurationDao.class);

    private final NodeConfigurationExMapper configurationMapper;

    @Autowired
    public NodeConfigurationDao(NodeConfigurationExMapper configurationMapper) {
        this.configurationMapper = configurationMapper;
    }


    public NodeConfigurationEx findById(Long id) {
        NodeConfigurationEx config = configurationMapper.selectByPrimaryKeyEx(id);
        if (config == null) LOG.warn("NodeConfiguration not exist for Node[{}]", id);
        return config;
    }
}
