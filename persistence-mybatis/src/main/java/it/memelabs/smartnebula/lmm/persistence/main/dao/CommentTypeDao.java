package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentType;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentTypeExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentTypeExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.CommentTypeExMapper;

/**
 * @author Andrea Fossi.
 */
@Service
public class CommentTypeDao {
    private final CommentTypeExMapper commentTypeExMapper;

    @Autowired
    public CommentTypeDao(CommentTypeExMapper commentTypeExMapper) {
        this.commentTypeExMapper = commentTypeExMapper;
    }

    public Tuple2<List<CommentType>,Integer> findByEntity(CommentEntity entity, UserLogin user) {
        CommentTypeExample example = new CommentTypeExample();
        CommentTypeExample.Criteria criteria = example.createCriteria();
        criteria.andEntityEqualTo(entity);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("ordinal, description");
        int count = commentTypeExMapper.countByExample(example);
        List<CommentType> list = commentTypeExMapper.selectByExample(example);
        return new Tuple2<>(list, count);
    }
    
    public Tuple2<List<CommentType>, Integer> findByConstructionSiteLogId(long constructionSiteLogId, CommentEntity entity, UserLogin user) {
        CommentTypeExExample example = new CommentTypeExExample();
        CommentTypeExExample.Criteria criteria = example.createCriteria();
        criteria.andEntityEqualTo(entity);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("ordinal, description");
        example.setConstructionSiteLogId(constructionSiteLogId);
        int count = commentTypeExMapper.countByExample(example);
        List<CommentType> list = commentTypeExMapper.selectByExampleEx(example);
        return new Tuple2<>(list, count);
    }
}
