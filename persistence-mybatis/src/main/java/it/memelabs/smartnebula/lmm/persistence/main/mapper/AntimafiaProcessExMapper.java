package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessExExample;

public interface AntimafiaProcessExMapper extends AntimafiaProcessMapper {

	AntimafiaProcessEx selectExByPrimaryKey(Long id);

	List<AntimafiaProcessEx> selectExByExampleWithRowbounds(AntimafiaProcessExExample example, RowBounds rowBounds);
}
