package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;

public interface WeeklyWorkLogExMapper extends WeeklyWorkLogMapper {

    WeeklyWorkLogEx selectExByPrimaryKey(long id);

    List<WeeklyWorkLogEx> selectExByExampleWithRowbounds(WeeklyWorkLogExample example, RowBounds rowBounds);

    List<CompanyEmployment> selectCompanyPersonEmpl(long id);

    List<CompanyEmployment> selectCompanyEquipmentEmpl(long id);

    /**
     * @param id
     *            weekly work log id
     * @return not valid PersonEmployment
     */
    List<Long> validatePersonEmployment(@Param("id") long weeklyWorkLogId, @Param("employmentStartDate") Date employmentStartDate, @Param("employmentEndDate") Date employmentEndDate);

    /**
     * @param id
     *            weekly work log id
     * @return not valid PersonEmployment
     */
    List<Long> validateEquipmentEmployment(@Param("id") long weeklyWorkLogId, @Param("employmentStartDate") Date employmentStartDate, @Param("employmentEndDate") Date employmentEndDate);

    int duplicatePersonEmployment(WeeklyWorkLogPersonEx record);

    int duplicateEquipmentEmployment(WeeklyWorkLogEquipmentEx record);
}
