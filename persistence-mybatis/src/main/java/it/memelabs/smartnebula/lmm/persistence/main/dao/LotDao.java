package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Lot;
import it.memelabs.smartnebula.lmm.persistence.main.dto.LotEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.LotExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.LotExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LotDao {
    // private static final Logger LOG = getLogger(LotDao.class);

    private final LotExMapper lotExMapper;

    @Autowired
    public LotDao(LotExMapper lotExMapper) {
        this.lotExMapper = lotExMapper;
    }

    public Lot findById(long id) {
        return lotExMapper.selectByPrimaryKey(id);
    }

    public LotEx findExById(long id) {
        return lotExMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<LotEx>, Integer> findByFilter(LotFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());

        LotExExample lotExExample = new LotExExample();
        LotExExample.Criteria criteria = lotExExample.createCriteria();
        if (StringUtils.isNotBlank(filter.getCode())) {
            criteria.andCodeLikeInsensitive(filter.getCode());
        }
        if (filter.getJobOrderId() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrderId());
        } else {
            criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        }
        if (filter.getCompanyAssignedId() != null) {
            criteria.andAssignedCompanyIdEqualTo(filter.getCompanyAssignedId());
        }
        lotExExample.setOrderByClause("code, description, id");
        int count = lotExMapper.countByExample(lotExExample);
        List<LotEx> list = lotExMapper.selectByExampleWithRowbounds(lotExExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(Lot record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = lotExMapper.insert(record);
        return result > 0 ? record.getId() : null;
    }

    public boolean update(Lot record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = lotExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = lotExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Tuple2<List<LotEx>, Integer> findLotByJobOrderAndText(LotFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds(RowBounds.NO_ROW_OFFSET, filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());

        LotExExample lotExExample = new LotExExample();
        LotExExample.Criteria criteria = lotExExample.createCriteria();
        if (filter.getJobOrderId() != null) {
            criteria.andJobOrderIdEqualTo(filter.getJobOrderId());
        }
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());

        if (StringUtils.isNoneBlank(filter.getFilterText())) {
            lotExExample.setFilterText(filter.getFilterText());
        }
        if (filter.getLotId() != null) {
            criteria.andIdEqualTo(filter.getLotId());
        }
        lotExExample.setOrderByClause("code, description, id");
        int count = lotExMapper.countByExample(lotExExample);
        List<LotEx> list = lotExMapper.selectByExampleWithRowbounds(lotExExample, rowBounds);
        return new Tuple2<>(list, count);
    }
}
