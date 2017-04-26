package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeConfigurationEx;

public interface NodeConfigurationExMapper extends NodeConfigurationMapper {

    NodeConfigurationEx selectByPrimaryKeyEx(Long id);

}