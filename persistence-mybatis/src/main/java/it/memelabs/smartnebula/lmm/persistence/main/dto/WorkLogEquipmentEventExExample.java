package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogEquipmentEventExExample extends WorkLogEquipmentEventExample {
    /**
     * joined equipment_employment table
     *
     * @param criteria
     * @param value
     * @return
     */
    public Criteria andCompanyIdEqualTo(WorkLogEquipmentEventExample.Criteria criteria, Long value) {
        criteria.addCriterion("ee_company_id =", value, "companyId");
        return criteria;
    }

    /**
     * joined equipment_employment table
     *
     * @param criteria
     * @param value
     * @return
     */
    public Criteria andEquipmentIdEqualTo(WorkLogEquipmentEventExample.Criteria criteria, Long value) {
        criteria.addCriterion("ee_equipment_id =", value, "equipmentId");
        return criteria;
    }


    /**
     * Joined work_log table
     *
     * @param criteria
     * @param value
     * @return
     */
    public WorkLogEquipmentEventExample.Criteria andConstructionSiteIdEqualTo(WorkLogEquipmentEventExample.Criteria criteria, Long value) {
        criteria.addCriterion("work_log_construction_site_id =", value, "constructionSiteId");
        return criteria;
    }

    /**
     * Joined work_log table
     *
     * @param criteria
     * @return
     */
    public WorkLogEquipmentEventExample.Criteria andConstructionSiteIdIsNull(WorkLogEquipmentEventExample.Criteria criteria) {
        criteria.addCriterion("work_log_construction_site_id is null");
        return criteria;
    }

    /**
     * Joined work_log table
     *
     * @param criteria
     * @param value
     * @return
     */
    public WorkLogEquipmentEventExample.Criteria andOwnerNodeIdEqualTo(WorkLogEquipmentEventExample.Criteria criteria, Long value) {
        criteria.addCriterion("work_log_owner_node_id =", value, "ownerNodeId");
        return criteria;
    }

    /**
     * if value !=0 query return events with personEmploymentId is
     */
    private Long notInWeeklyWorkLogId;

    public Long getNotInWeeklyWorkLogId() {
        return notInWeeklyWorkLogId;
    }

    public void setNotInWeeklyWorkLogId(Long notInWeeklyWorkLogId) {
        this.notInWeeklyWorkLogId = notInWeeklyWorkLogId;
    }

}