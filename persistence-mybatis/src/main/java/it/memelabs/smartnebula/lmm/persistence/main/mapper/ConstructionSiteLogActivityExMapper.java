package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogActivityEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogActivityExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ConstructionSiteLogActivityExMapper extends ConstructionSiteLogActivityMapper {

    ConstructionSiteLogActivityEx selectExByPrimaryKey(long id);

    List<ConstructionSiteLogActivityEx> selectExByExampleWithRowbounds(ConstructionSiteLogActivityExample example, RowBounds rowBounds);

    List<ConstructionSiteLogActivityEx> selectExByExample(ConstructionSiteLogActivityExample example);
}