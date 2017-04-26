package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.Collection;
import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import org.apache.ibatis.annotations.Param;

public interface WeeklyWorkLogChangeMapper {

    List<Company> selectCompanyByIds(@Param("idList") Collection<Long> idList);

    /**
     * 
     * @param idList
     * @return The id field is indeed the employment id
     */
    List<Person> selectPersonByEmplIds(@Param("idList") Collection<Long> idList);

    /**
     * 
     * @param idList
     * @return The id field is indeed the employment id
     */
    List<Equipment> selectEquipmentByEmplIds(@Param("idList") Collection<Long> idList);

    /**
     *
     * @param idList
     * @return personEmployment with need person and company fields
     */
    List<PersonEmploymentEx> selectPersonEmplByIds(@Param("idList") Collection<Long> idList);
  
    /**
     *
     * @param idList
     * @return equipmentEmployment with need equipment and company fields
     */
    List<EquipmentEmploymentEx> selectEquipmentEmplByIds(@Param("idList") Collection<Long> idList);

}
