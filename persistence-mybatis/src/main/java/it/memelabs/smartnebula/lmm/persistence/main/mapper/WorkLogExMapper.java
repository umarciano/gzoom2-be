package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyEmployment;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLog;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogExExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkLogExMapper extends WorkLogMapper {

    WorkLogEx selectExByPrimaryKey(long id);

    int countExByExample(WorkLogExExample example);

    List<WorkLogEx> selectExByExampleWithRowbounds(WorkLogExExample example, RowBounds rowBounds);

    List<CompanyEmployment> selectCompanyPersonEmpl(long id);

    List<CompanyEmployment> selectCompanyEquipmentEmpl(long id);

    /**
     * @param workLogId work log id
     * @return not valid PersonEmployment
     */
    List<Long> validatePersonEmployment(@Param("id") long workLogId, @Param("logDate") Date logDate);

    /**
     * @param workLogId work log id
     * @return not valid PersonEmployment
     */
    List<Long> validateEquipmentEmployment(@Param("id") long workLogId, @Param("logDate") Date logDate);

    /**
     * @param workLogId
     * @return
     */
    List<Map<String, Object>> countEventsByWorkLogId(@Param("id") long workLogId);

    WorkLog selectByConstructionSiteLogId(@Param("constructionSiteLogId") long constructionSiteLogId);

}
