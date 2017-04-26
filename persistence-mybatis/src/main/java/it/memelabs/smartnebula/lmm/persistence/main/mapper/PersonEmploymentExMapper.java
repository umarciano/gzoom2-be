package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.PersonEmploymentFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmploymentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmploymentExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmploymentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmploymentWwlExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PersonEmploymentExMapper extends PersonEmploymentMapper {

    PersonEmploymentEx selectByPrimaryKey(Long id);

    List<PersonEmploymentEx> selectExByExampleWithRowbounds(PersonEmploymentExample example, RowBounds rowBounds);

    int countDateOverlap(PersonEmploymentExExample ex);

    List<PersonEmploymentEx> selectWeekyWorkLogByExampleWithRowbounds(PersonEmploymentWwlExample example, RowBounds rowBounds);

    int countWeekyWorkLogByExample(PersonEmploymentWwlExample example);

    /**
     * Search {@link PersonEmploymentEx PersonEmployment} filtering {@link it.memelabs.smartnebula.lmm.persistence.main.dto.Person Person} that {@link it.memelabs.smartnebula.lmm.persistence.main.dto.Person#lastName lastName}
     * or {@link it.memelabs.smartnebula.lmm.persistence.main.dto.Person#firstName firsName} contain {@link PersonEmploymentFilter#filterText}
     * and  {@link PersonEmploymentEx#startDate} <= {@link PersonEmploymentFilter#filterDate}
     * and  {@link PersonEmploymentEx#endDate} >= {@link PersonEmploymentFilter#filterDate} or is null
     *
     * @param filter
     * @param rowBounds
     * @return
     */
    List<PersonEmploymentEx> selectByFreeTextWithRowbounds(PersonEmploymentFilter filter, RowBounds rowBounds);

    long selectNextExternalId();

}
