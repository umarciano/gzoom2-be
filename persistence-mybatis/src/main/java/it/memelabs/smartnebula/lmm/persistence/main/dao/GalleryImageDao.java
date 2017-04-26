package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImage;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImageEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImageExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.GalleryImageExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class GalleryImageDao {
    private static final Logger LOG = getLogger(GalleryImageDao.class);

    private final GalleryImageExMapper imageMapper;

    @Autowired
    public GalleryImageDao(GalleryImageExMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public long selectNextId() {
        return imageMapper.selectNextId();
    }

    public Long create(GalleryImage record, UserLogin user) {
        Date createdStamp = new Date();
        record.setCreatedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setModifiedStamp(createdStamp);
        record.setUploadedByUserId(user.getId());
        record.setUploadedStamp(createdStamp);
        record.setOwnerNodeId(user.getActiveNode().getId());
        int insert = imageMapper.insert(record);
        if (insert > 0)
            return record.getId();
        else
            return null;
    }

    public GalleryImageEx findById(Long id) {
        return imageMapper.selectExByPrimaryKey(id);
    }

    public boolean deleteById(Long id) {
        int ret = imageMapper.deleteByPrimaryKey(id);
        return ret > 0;
    }

    public boolean update(GalleryImage record, UserLogin user) {
        record.setModifiedByUserId(user.getId());
        record.setModifiedStamp(new Date());
        int ret = imageMapper.updateByPrimaryKey(record);
        return ret > 0;
    }

    public Tuple2<List<GalleryImageEx>, Integer> findByFilter(GalleryImageFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());

        GalleryImageExample example = new GalleryImageExample();
        GalleryImageExample.Criteria criteria = example.createCriteria();
        if (filter.getFrom() != null)
            criteria.andValidSinceGreaterThanOrEqualTo(DateUtil.getStartOfDay(filter.getFrom()));
        if (filter.getTo() != null) criteria.andValidUntilLessThanOrEqualTo(DateUtil.getEndOfDay(filter.getTo()));
        if (filter.getCsId() != null) criteria.andConstructionSiteIdEqualTo(filter.getCsId());
        if (filter.getWbsId() != null) criteria.andWbsIdEqualTo(filter.getWbsId());
        if (filter.getCompanyId() != null) criteria.andCompanyIdEqualTo(filter.getCompanyId());
        if (StringUtils.isNoneBlank(filter.getText())) criteria.andNoteLikeInsensitive("%" + filter.getText() + "%");
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        example.setOrderByClause("valid_since DESC, uploaded_stamp DESC");
        int count = imageMapper.countByExample(example);
        List<GalleryImageEx> result = imageMapper.selectExByExampleWithRowbounds(example, rowBounds);
        return new Tuple2<>(result, count);
    }

}
