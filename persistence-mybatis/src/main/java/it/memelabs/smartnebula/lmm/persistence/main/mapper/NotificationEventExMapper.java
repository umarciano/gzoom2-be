package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationEventExMapper extends NotificationEventMapper {

    List<NotificationEvent> selectByNotificationGroup(long notificationGroupId);

    int batchInsert(@Param("events") List<NotificationEvent> events);
}