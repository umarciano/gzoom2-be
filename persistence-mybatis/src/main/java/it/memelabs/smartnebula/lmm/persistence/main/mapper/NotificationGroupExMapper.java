package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroupEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroupExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.NotificationEventType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;

public interface NotificationGroupExMapper extends NotificationGroupMapper {
    List<UserLogin> selectUserByNotificationGroup(long notificationGroupId);

    NotificationGroupEx selectByPrimaryKeyEx(long id);

    List<NotificationGroupEx> selectExByExampleWithRowbounds(NotificationGroupExample notificationGroupExExample, RowBounds rowBounds);

    List<NotificationGroupEx> selectExByExample(NotificationGroupExample notificationGroupExExample);

    int insertUserAssoc(@Param("notificationGroupId") Long notificationGroupId, @Param("users") Collection<Long> userIds,@Param("ownerNodeId") Long ownerNodeId);

    int deleteUserAssocByNotificationGroupId(long notificationGroupId);

    List<String> selectRecipients(@Param("eventId") NotificationEventType eventId, @Param("notificationGroupId") Long notificationGroupId, @Param("ownerNodeId") Long ownerNodeId);

}