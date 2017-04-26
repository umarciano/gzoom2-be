package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseExample;

public interface AntimafiaProcessPhaseExMapper extends AntimafiaProcessPhaseMapper {

	AntimafiaProcessPhaseEx selectExByPrimaryKey(Long id);

	List<AntimafiaProcessPhaseEx> selectExByExample(AntimafiaProcessPhaseExample example);

    int selectCountByUniquePhaseType(@Param("antimafia_process_id")long antimafiaProcessId, @Param("phase_type_id")long phaseTypeId);
}
