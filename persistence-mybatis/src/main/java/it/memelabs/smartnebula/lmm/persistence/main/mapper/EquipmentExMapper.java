package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Equipment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface EquipmentExMapper {

    EquipmentEx selectByPrimaryKey(Long id);

    int countByExample(EquipmentExample example);

    List<EquipmentEx> selectByExampleWithRowbounds(EquipmentExample example, RowBounds rowBounds);

    List<Equipment> selectByWorkLogIdWithRowbounds(@Param("workLogId") Long workLogId, @Param("filterText") String filterText, RowBounds rowBounds);

    int countByWorkLogId(@Param("workLogId") Long workLogId, @Param("filterText") String filterText);
}
