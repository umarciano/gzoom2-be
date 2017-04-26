package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface EmploymentToExportExMapper extends EmploymentToExportMapper {

    int markPersonEmploymentAsSent(@Param("modifiedStamp") Date modifiedStamp, @Param("ownerNodeId") Long ownerNodeId, @Param("sessionId") Long sessionId);

    int markEquipmentEmploymentAsSent(@Param("modifiedStamp") Date modifiedStamp, @Param("ownerNodeId") Long ownerNodeId, @Param("sessionId") Long sessionId);

}