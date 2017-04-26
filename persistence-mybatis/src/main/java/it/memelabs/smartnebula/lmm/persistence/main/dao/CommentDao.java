package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Comment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.CommentExMapper;

/**
 * @author Andrea Fossi.
 */
@Service
public class CommentDao {
    private final CommentExMapper commentExMapper;

    @Autowired
    public CommentDao(CommentExMapper commentExMapper) {
        this.commentExMapper = commentExMapper;
    }

    public Long create(Comment record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = commentExMapper.insert(record);
        if (result > 0)
            return record.getId();
        else return null;
    }
    
    public boolean update(Comment record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = commentExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = commentExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Comment findById(long id) {
        return commentExMapper.selectByPrimaryKey(id);
    }
    
    public Tuple2<List<CommentEx>, Integer> findByContractId(long contractId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("id desc");
        int count = commentExMapper.countByExample(example);
        List<CommentEx> list = commentExMapper.selectByExampleEx(example);
        return new Tuple2<>(list, count);
    }

    public boolean deleteByContractId(long contractId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("id desc");
        int count = commentExMapper.deleteByExample(example);
        return count > 0;
    }

    public Tuple2<List<CommentEx>, Integer> findByAntimafiaProcessId(long processId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andAntimafiaProcessIdEqualTo(processId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("id desc");
        int count = commentExMapper.countByExample(example);
        List<CommentEx> list = commentExMapper.selectByExampleEx(example);
        return new Tuple2<>(list, count);
    }

    public boolean deleteByAntimafiaProcessId(long processId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andAntimafiaProcessIdEqualTo(processId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("id desc");
        int count = commentExMapper.deleteByExample(example);
        return count > 0;
    }


    public Tuple2<List<CommentEx>, Integer> findByConstructionSiteLogId(Long constructionSiteLogId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andConstructionSiteLogIdEqualTo(constructionSiteLogId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("CT_ordinal, CT_description");
        int count = commentExMapper.countByExample(example);
        List<CommentEx> list = commentExMapper.selectByExampleEx(example);
        return new Tuple2<>(list, count);
    }

    public boolean deleteByConstructionSiteLogId(long constructionSiteLogId, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andConstructionSiteLogIdEqualTo(constructionSiteLogId);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        int count = commentExMapper.deleteByExample(example);
        return count > 0;
    }
    
    public boolean commentTypeExists(CommentFilter filter, UserLogin user) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        criteria.andCommentTypeIdEqualTo(filter.getCommentTypeId());
        criteria.andConstructionSiteLogIdEqualTo(filter.getConstructionSiteLogId());
                        if(filter.getId()!=null){
            criteria.andIdNotEqualTo(filter.getId());
        }
        return commentExMapper.countByExample(example) > 0;
    }
    
    public CommentEx findExById(long id) {
        return commentExMapper.selectExByPrimaryKey(id);
    }
}
