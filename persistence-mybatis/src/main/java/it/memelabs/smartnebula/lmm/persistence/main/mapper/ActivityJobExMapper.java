package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.ReferenceObject;
import it.memelabs.smartnebula.lmm.persistence.job.dao.ActivityJobFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJob;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobEx;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ActivityJobExMapper extends ActivityJobMapper{
    ActivityJobEx selectByPrimaryKeyEx(Long id);

    int deleteByReferenceId(@Param("referenceId") Long referenceId, @Param("referenceObject") ReferenceObject referenceObject, @Param("type") JobType type);

    int updateConsumedByPrimaryKey(ActivityJob record);

    List<ActivityJobEx> selectByFilterEx(ActivityJobFilter filter, RowBounds rowBounds);

    List<ActivityJob> selectByFilter(ActivityJobFilter filter, RowBounds rowBounds);
}
