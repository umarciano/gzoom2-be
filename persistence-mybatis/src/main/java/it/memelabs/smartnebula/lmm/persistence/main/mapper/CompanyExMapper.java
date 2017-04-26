package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.CompanyWwlFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Company;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyWwlExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CompanyExMapper extends CompanyMapper {

    CompanyEx selectByPrimaryKey(Long id);

    List<CompanyEx> selectByExampleWithRowbounds(CompanyExExample example, RowBounds rowBounds);

    List<CompanyEx> selectWeekyWorkLogByExampleWithRowbounds(CompanyWwlExample example, RowBounds rowBounds);

    int countWeekyWorkLogByExample(CompanyWwlExample example);

    List<Company> validForWeeklyWorkLog(CompanyWwlFilter filter);
}
