package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogEquipmentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogEquipmentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogEquipmentExport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface WeeklyWorkLogEquipmentExMapper extends WeeklyWorkLogEquipmentMapper {

    Set<Long> selectEquipmentEmploymentIds(@Param("weeklyWorkLogId") Long weeklyWorkLogId);

    List<WeeklyWorkLogEquipmentExport> findForExportByExample(WeeklyWorkLogEquipmentExample example);

}