package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentTypeExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentType;

/**
 * @author Andrea Fossi.
 */
public interface CommentTypeExMapper extends CommentTypeMapper {
    List<CommentType> selectByExampleEx(CommentTypeExample example);
}
