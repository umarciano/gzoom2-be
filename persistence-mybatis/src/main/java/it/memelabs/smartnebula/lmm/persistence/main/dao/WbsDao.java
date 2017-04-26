package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Wbs;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WbsEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WbsExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.WbsExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author sivi.
 */
@Service
public class WbsDao {
    private final WbsExMapper wbsExMapper;

    @Autowired
    public WbsDao(WbsExMapper wbsExMapper) {
        this.wbsExMapper = wbsExMapper;
    }

    public WbsEx findById(long id) {
        return wbsExMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<WbsEx>, Integer> findByFilter(WbsFilter filter, UserLogin user) {
        RowBounds rowBounds = filter.makeRowBounds();
        WbsExample wbsExample = new WbsExample();
        WbsExample.Criteria criteria = wbsExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getCode())) {
            criteria.andCodeLikeInsensitive(filter.getCode());
        }
        if (filter.getJobOrderId() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrderId());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        wbsExample.setOrderByClause("code, description, id");
        int count = wbsExMapper.countByExample(wbsExample);
        List<WbsEx> list = wbsExMapper.selectByFilterWithRowboundsEx(wbsExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<WbsEx>, Integer> freeSearch(WbsFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        WbsExample wbsExample = new WbsExample();
        WbsExample.Criteria criteria = wbsExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (filter.getJobOrderId() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrderId());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getId() != null) {
            criteria.andIdEqualTo(filter.getId());
        }
        if (StringUtils.isNoneBlank(filter.getText())) {
            criteria.andDescriptionLikeInsensitive("%" + filter.getText() + "%");
            WbsExample.Criteria or = wbsExample.or();
            or.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
            or.andCodeLikeInsensitive("%" + filter.getText() + "%");
            if (filter.getJobOrderId() != null) {
                or.andJobOrderIdEqualTo(filter.getJobOrderId());
            }
            if (filter.getConstructionSiteId() != null) {
                or.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
            }
            if (filter.getId() != null) {
                criteria.andIdEqualTo(filter.getId());
            }
        }
        wbsExample.setOrderByClause("code, description, id");
        int count = wbsExMapper.countByExample(wbsExample);
        List<WbsEx> list = wbsExMapper.selectByFilterWithRowboundsEx(wbsExample, rowBounds);
        return new Tuple2<>(list, count);
    }


    public Long create(Wbs record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = wbsExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(Wbs record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = wbsExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = wbsExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Tuple2<List<WbsEx>, Integer> checkUniqueCode(WbsFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        WbsExample wbsExample = new WbsExample();
        WbsExample.Criteria criteria = wbsExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (StringUtils.isNotBlank(filter.getCode())) {
            criteria.andCodeLikeInsensitive(filter.getCode());
        }
        if (filter.getJobOrderId() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrderId());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        if (filter.getId() != null) {
            criteria.andIdNotEqualTo(filter.getId());
        }
        wbsExample.setOrderByClause("code, description, id");
        int count = wbsExMapper.countByExample(wbsExample);
        List<WbsEx> list = wbsExMapper.selectByFilterWithRowboundsEx(wbsExample, rowBounds);
        return new Tuple2<>(list, count);
    }
}
