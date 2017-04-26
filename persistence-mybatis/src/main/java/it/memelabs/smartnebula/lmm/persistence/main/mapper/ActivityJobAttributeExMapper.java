package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.enumeration.JobType;
import it.memelabs.smartnebula.lmm.persistence.enumeration.ReferenceObject;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ActivityJobAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityJobAttributeExMapper extends ActivityJobAttributeMapper{

    int deleteByJobReferenceId(@Param("referenceId") Long referenceId, @Param("referenceObject") ReferenceObject referenceObject, @Param("type") JobType type);

    int deleteByJobId(long jobId);

    int insertAttributes(@Param("attributes") List<ActivityJobAttribute> attributes);
}