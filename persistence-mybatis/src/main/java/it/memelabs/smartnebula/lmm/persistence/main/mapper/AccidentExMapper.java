package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.AccidentFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AccidentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AccidentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface AccidentExMapper extends AccidentMapper {

    AccidentEx selectExByPrimaryKey(Long id);

    List<AccidentEx> selectExByExampleWithRowbounds(AccidentExample example, RowBounds rowBounds);

    List<AccidentEx> searchOverlap(AccidentFilter accidentFilter);

    int countByWorkLogPersonEvents( @Param("events") List<Long> events);
}