package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.WorkLogEventUpdateFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEventExport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

public interface WorkLogPersonEventExMapper extends WorkLogPersonEventMapper {

    List<WorkLogPersonEventEx> selectExByExample(WorkLogPersonEventExExample example, RowBounds rowBounds);

    int countExByExample(WorkLogPersonEventExExample example);

    WorkLogPersonEventEx selectExByPrimaryKey(Long id);

    int update(WorkLogEventUpdateFilter updateReq);

    int countNotValid(@Param("workLogId") Long workLogId, @Param("events") List<Long> events);

    int countDuplicate(@Param("srcWorkLogId") Long srcWorkLogId, @Param("destWorkLogId") Long destWorkLogId, @Param("events") List<Long> events);

    Set<Long> selectPersonEmploymentIds(@Param("workLogId") Long workLogId);

    List<WorkLogPersonEventExport> findForExportByFilter(WorkLogPersonEventExExample example, RowBounds rowBounds);
}