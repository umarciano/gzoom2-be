package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogExample;

public interface ConstructionSiteLogExMapper extends ConstructionSiteLogMapper {

	List<ConstructionSiteLogEx> selectByFilterWithRowboundsEx(ConstructionSiteLogExample example, RowBounds rowBounds);

	ConstructionSiteLogEx selectByPrimaryKeyEx(long id);

	int countByFilterEx(ConstructionSiteLogExample example);
}