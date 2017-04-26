package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.WbsEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WbsExample;

public interface WbsExMapper extends WbsMapper {

	List<WbsEx> selectByFilterWithRowboundsEx(WbsExample example, RowBounds rowBounds);

	WbsEx selectByPrimaryKeyEx(long id);

	int countByFilterEx(WbsExample example);
}