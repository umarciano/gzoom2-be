package it.mapsgroup.gzoom.model;

import java.time.LocalDateTime;

public class EmailRuleCustom {
    private String ruleId;
    private String name;
    private String workEffortTypeId;
    private String statusId;
    private String recipientRoleTypeId;
    private String uoList;
    private String subject;
    private String bodyTemplate;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getWorkEffortTypeId() { return workEffortTypeId; }
    public void setWorkEffortTypeId(String workEffortTypeId) { this.workEffortTypeId = workEffortTypeId; }

    public String getStatusId() { return statusId; }
    public void setStatusId(String statusId) { this.statusId = statusId; }

    public String getRecipientRoleTypeId() { return recipientRoleTypeId; }
    public void setRecipientRoleTypeId(String recipientRoleTypeId) { this.recipientRoleTypeId = recipientRoleTypeId; }

    public String getUoList() { return uoList; }
    public void setUoList(String uoList) { this.uoList = uoList; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBodyTemplate() { return bodyTemplate; }
    public void setBodyTemplate(String bodyTemplate) { this.bodyTemplate = bodyTemplate; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
