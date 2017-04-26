package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroup;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroupEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroupExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.NotificationEventType;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.NotificationGroupExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationGroupDao {
    // private static final Logger LOG = getLogger(LotDao.class);

    private final NotificationGroupExMapper groupExMapper;

    @Autowired
    public NotificationGroupDao(NotificationGroupExMapper groupExMapper) {
        this.groupExMapper = groupExMapper;
    }

    public NotificationGroup findById(long id) {
        return groupExMapper.selectByPrimaryKey(id);
    }

    public NotificationGroupEx findExById(long id) {
        return groupExMapper.selectByPrimaryKeyEx(id);
    }

    public Tuple2<List<NotificationGroupEx>, Integer> findByFilter(NotificationGroupFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());

        NotificationGroupExample notificationGroupExExample = new NotificationGroupExample();
        NotificationGroupExample.Criteria criteria = notificationGroupExExample.createCriteria();
        if (StringUtils.isNotBlank(filter.getFilterText())) {
            criteria.andDescriptionLikeInsensitive("%" + filter.getFilterText() + "%");
        }
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        notificationGroupExExample.setOrderByClause("description, id");
        int count = groupExMapper.countByExample(notificationGroupExExample);
        List<NotificationGroupEx> list = groupExMapper.selectExByExampleWithRowbounds(notificationGroupExExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(NotificationGroup record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = groupExMapper.insert(record);
        return result > 0 ? record.getId() : null;
    }

    public boolean update(NotificationGroup record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = groupExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = groupExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public List<String> findRecipients(NotificationEventType eventId, Long ownerNodeId) {
        return findRecipients(eventId, ownerNodeId, null);
    }

    public List<String> findRecipients(NotificationEventType eventId, Long ownerNodeId, Long notificationGroupId) {
        if (notificationGroupId == null && ownerNodeId == null)
            throw new RuntimeException("notificationGroupId and ownerNodeId cannot be null both");
        return groupExMapper.selectRecipients(eventId, notificationGroupId, ownerNodeId);
    }

    public void insertUserAssoc(Long id, List<Long> users, UserLogin user) {
        groupExMapper.insertUserAssoc(id, users, user.getActiveNode().getId());
    }

    public void deleteUserAssoc(Long id) {
        groupExMapper.deleteUserAssocByNotificationGroupId(id);
    }
}
