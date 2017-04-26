package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteExExample;

public interface ConstructionSiteExMapper extends ConstructionSiteMapper {

    List<ConstructionSite> selectByContract(Long contractId);

	List<ConstructionSiteEx> selectByFilterWithRowboundsEx(ConstructionSiteExExample example, RowBounds rowBounds);

	ConstructionSiteEx selectByPrimaryKeyEx(long id);

	int countByFilterEx(ConstructionSiteExExample example);

	Long getAddressId(long id);
    
    

}