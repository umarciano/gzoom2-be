package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class WorkLogPersonEventExExample extends WorkLogPersonEventExample {

    /**
     * joined person_employment table
     *
     * @param criteria
     * @param value
     * @return
     */
    public Criteria andCompanyIdEqualTo(Criteria criteria, Long value) {
        criteria.addCriterion("pe_company_id =", value, "companyId");
        return criteria;
    }

    /**
     * joined person_employment table
     *
     * @param criteria
     * @param value
     * @return
     */
    public Criteria andPersonIdEqualTo(Criteria criteria, Long value) {
        criteria.addCriterion("pe_person_id =", value, "personId");
        return criteria;
    }

    /**
     * Joined work_log table
     *
     * @param criteria
     * @param value
     * @return
     */
    public Criteria andConstructionSiteIdEqualTo(Criteria criteria, Long value) {
        criteria.addCriterion("work_log_construction_site_id =", value, "constructionSiteId");
        return criteria;
    }

    /**
     * Joined work_log table
     *
     * @param criteria
     * @return
     */
    public Criteria andConstructionSiteIdIsNull(Criteria criteria) {
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
    public Criteria andOwnerNodeIdEqualTo(Criteria criteria, Long value) {
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