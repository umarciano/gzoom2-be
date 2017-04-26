package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeSequenceGeneratorParam;

public interface IdentityMapper {

	Long selectMaxByNode(NodeSequenceGeneratorParam param);
}
