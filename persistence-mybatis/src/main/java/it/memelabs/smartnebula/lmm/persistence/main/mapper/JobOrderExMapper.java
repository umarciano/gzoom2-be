package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderExample;

public interface JobOrderExMapper extends JobOrderMapper {

    JobOrderEx selectByPrimaryKeyEx(@Param("id")Long id);

    List<JobOrderEx> selectByExampleWithRowboundsEx(JobOrderExample example, RowBounds rowBounds);

	int countByExampleEx(JobOrderExExample jobOrderExample);

}