package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Person;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonExExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PersonExMapper extends PersonMapper {

    PersonEx selectByPrimaryKey(Long id);

    int countByExample(PersonExExample example);

    List<PersonEx> selectByExampleWithRowbounds(PersonExExample example, RowBounds rowBounds);

    List<Person> selectByWorkLogIdWithRowbounds(@Param("workLogId") Long workLogId, @Param("filterText") String filterText, RowBounds rowBounds);

    int countByWorkLogId(@Param("workLogId") Long workLogId, @Param("filterText") String filterText);
}
