package it.mapsgroup.gzoom.mybatis.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import it.mapsgroup.gzoom.mybatis.dto.Reminder;

public interface ReminderMapper {

	/**
	 * Ritorna la lista di partyId che devono ricevere la mail per la scadenza periodor
	 * @param workEffortTypeId
	 * @param monitoringDate
	 * @return
	 */
	List<Reminder> selectReminderPeriod(@Param("workEffortTypeId") String workEffortTypeId, @Param("monitoringDate") Date monitoringDate, @Param("filter") Map<String, Object> filter);
	
	
	/**
	 * Ritorna la lista di partyId che devono ricevere la mail per la scadenza obiettivo
	 * @param workEffortTypeId
	 * @param monitoringDate
	 * @return
	 */
	List<Reminder> selectReminderWorkEffortExpiry(@Param("workEffortTypeId") String workEffortTypeId, @Param("monitoringDate") Date monitoringDate, @Param("filter") Map<String, Object> filter);
	
}
