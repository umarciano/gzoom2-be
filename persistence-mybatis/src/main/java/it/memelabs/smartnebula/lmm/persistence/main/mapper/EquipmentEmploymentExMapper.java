package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.EquipmentEmploymentFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmploymentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmploymentExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmploymentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmploymentWwlExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface EquipmentEmploymentExMapper extends EquipmentEmploymentMapper {

    EquipmentEmploymentEx selectByPrimaryKey(Long id);

    List<EquipmentEmploymentEx> selectExByExampleWithRowbounds(EquipmentEmploymentExample example, RowBounds rowBounds);

    int countDateOverlap(EquipmentEmploymentExExample equipmentExExample);

    List<EquipmentEmploymentEx> selectWeekyWorkLogByExampleWithRowbounds(EquipmentEmploymentWwlExample example, RowBounds rowBounds);

    int countWeekyWorkLogByExample(EquipmentEmploymentWwlExample example);

    List<EquipmentEmploymentEx> selectByFreeTextWithRowbounds(EquipmentEmploymentFilter filter, RowBounds rowBounds);
}
