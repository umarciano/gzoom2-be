package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.enumeration.EventStatus;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationEvent;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationEventExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationEventKey;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.NotificationEventType;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.NotificationEventExMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationEventDao {
    // private static final Logger LOG = getLogger(LotDao.class);

    private final NotificationEventExMapper eventMapper;

    @Autowired
    public NotificationEventDao(NotificationEventExMapper eventMapper) {
        this.eventMapper = eventMapper;
    }


    public boolean create(NotificationEvent record) {
        int result = eventMapper.insert(record);
        return result > 0;
    }

    public boolean create(List<NotificationEvent> record) {
        int result = eventMapper.batchInsert(record);
        return result > 0;
    }

    public boolean update(NotificationEvent record) {
        int result = eventMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean update(NotificationEvent record, EventStatus status) {
        NotificationEventExample example = new NotificationEventExample();
        NotificationEventExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);
        criteria.andNotificationGroupIdEqualTo(record.getNotificationGroupId());
        criteria.andEventIdEqualTo(record.getEventId());
        int result = eventMapper.updateByExample(record, example);
        return result > 0;
    }

    public boolean delete(long notificationGroupId, NotificationEventType eventType) {
        NotificationEventKey key = new NotificationEventKey();
        key.setEventId(eventType);
        key.setNotificationGroupId(notificationGroupId);
        int result = eventMapper.deleteByPrimaryKey(key);
        return result > 0;
    }

    public boolean deleteByNotificationGroup(long notificationGroupId) {
        NotificationEventExample example = new NotificationEventExample();
        NotificationEventExample.Criteria criteria = example.createCriteria();
        criteria.andNotificationGroupIdEqualTo(notificationGroupId);
        int result = eventMapper.deleteByExample(example);
        return result > 0;
    }

    public NotificationEvent find(Long notificationGroupId, NotificationEventType eventId) {
        NotificationEventKey key = new NotificationEventKey();
        key.setEventId(eventId);
        key.setNotificationGroupId(notificationGroupId);
        return eventMapper.selectByPrimaryKey(key);
    }

    public NotificationEvent find(NotificationEventKey key) {
        return eventMapper.selectByPrimaryKey(key);
    }

    public List<NotificationEvent> findByFilter(NotificationEventFilter filter) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        NotificationEventExample example = new NotificationEventExample();
        NotificationEventExample.Criteria criteria = example.createCriteria();
        if (filter.getPromiseDateEnd() != null)
            criteria.andPromiseDateLessThanOrEqualTo(filter.getPromiseDateEnd());
        if (filter.getEnabled() != null)
            criteria.andEnabledEqualTo(filter.getEnabled());
        if (filter.getStatus() != null)
            criteria.andStatusEqualTo(filter.getStatus());
        example.setOrderByClause("promise_date ASC");
        return eventMapper.selectByExampleWithRowbounds(example, rowBounds);
    }
}
