package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogPersonExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WeeklyWorkLogPersonExport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface WeeklyWorkLogPersonExMapper extends WeeklyWorkLogPersonMapper {

    Set<Long> selectPersonEmploymentIds(@Param("weeklyWorkLogId") Long weeklyWorkLogId);

    List<WeeklyWorkLogPersonExport> findForExportByExample(WeeklyWorkLogPersonExample example);

}