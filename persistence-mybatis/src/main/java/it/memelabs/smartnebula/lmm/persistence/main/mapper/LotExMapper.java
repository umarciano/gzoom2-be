package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.LotEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.LotExExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface LotExMapper extends LotMapper {

	int countByExample(LotExExample example);

	List<LotEx> selectByExampleWithRowbounds(LotExExample example, RowBounds rowBounds);



	LotEx selectByPrimaryKeyEx(Long id);

}
