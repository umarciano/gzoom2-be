package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyCompositionEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyCompositionExample;

public interface CompanyCompositionExMapper extends CompanyCompositionMapper {

	List<CompanyCompositionEx> selectExByExample(CompanyCompositionExample example);
}
