package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EntityState;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EntityStateExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class EntityStateDao {
    private static final org.slf4j.Logger LOG = getLogger(EntityStateDao.class);

    private final EntityStateMapper entityStateMapper;

    @Autowired
    public EntityStateDao(EntityStateMapper entityStateMapper) {
        this.entityStateMapper = entityStateMapper;
    }

    public EntityState findById(Long id) {
        return id != null ? entityStateMapper.selectByPrimaryKey(id) : null;
    }

    public EntityState getFirst(String entity, Long parentId, Integer currentOrdinal, UserLogin user) {
        List<EntityState> list = getNextList(entity, parentId, currentOrdinal, user);
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    public List<EntityState> getNextList(String entity, Long parentId, Integer currentOrdinal, UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        EntityStateExample.Criteria criteria = entityStateExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        if (StringUtils.isNotBlank(entity)) {
            criteria.andEntityEqualTo(entity);
        }
        if (parentId != null) {
            if (parentId < 0L) {
                criteria.andParentIdIsNull();
            } else {
                criteria.andParentIdEqualTo(parentId);
            }
        }
        if (currentOrdinal != null) {
            criteria.andOrdinalGreaterThan(currentOrdinal);
        }
        entityStateExample.setOrderByClause("ordinal, id");
        return entityStateMapper.selectByExample(entityStateExample);
    }

    public EntityState findByTag(EntityStateTag tag, EntityStateReference entity, UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        EntityStateExample.Criteria criteria = entityStateExample.createCriteria();
        criteria.andTagEqualTo(tag);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andEntityEqualTo(entity.name());
        List<EntityState> result = entityStateMapper.selectByExample(entityStateExample);
        if (result.isEmpty()) {
            LOG.error("EntityState not found {EntityStateTag: {}, EntityStateReference: {}, UserLogin: {} }", tag, entity, user.getUsername());
            throw new RuntimeException("EntityState not found");
        } else
            return result.get(0);
    }

    public Tuple2<List<EntityState>, Integer> findByTag(List<EntityStateTag> tags, EntityStateReference entity, UserLogin user) {
        EntityStateExample entityStateExample = new EntityStateExample();
        EntityStateExample.Criteria criteria = entityStateExample.createCriteria();
        criteria.andTagIn(tags);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andEntityEqualTo(entity.name());
        Integer count = entityStateMapper.countByExample(entityStateExample);
        List<EntityState> ret = entityStateMapper.selectByExample(entityStateExample);
        return new Tuple2<List<EntityState>, Integer>(ret, count);
    }
}
