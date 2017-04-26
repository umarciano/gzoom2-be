package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.WorkLogEventUpdateFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

public interface WorkLogEquipmentEventExMapper extends WorkLogEquipmentEventMapper {

    List<WorkLogEquipmentEventEx> selectExByExample(WorkLogEquipmentEventExExample example, RowBounds rowBounds);

    int countExByExample(WorkLogEquipmentEventExExample example);

    WorkLogEquipmentEventEx selectExByPrimaryKey(Long id);

    int update(WorkLogEventUpdateFilter updateReq);

    int countNotValid(@Param("workLogId") Long workLogId, @Param("events") List<Long> events);

    int countDuplicate(@Param("srcWorkLogId") Long srcWorkLogId, @Param("destWorkLogId") Long destWorkLogId, @Param("events") List<Long> events);

    Set<Long> selectEquipmentEmploymentIds(@Param("workLogId") Long workLogId);

    List<WorkLogEquipmentEventExport> findForExportByFilter(WorkLogEquipmentEventExExample example, RowBounds rowBounds);
}