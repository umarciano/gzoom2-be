package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentExample;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
public interface CommentExMapper extends CommentMapper {

    List<CommentEx> selectByExampleEx(CommentExample example);

    CommentEx selectExByPrimaryKey(long id);
}
