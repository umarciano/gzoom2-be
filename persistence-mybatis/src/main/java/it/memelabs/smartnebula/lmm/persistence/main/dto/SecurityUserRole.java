package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class SecurityUserRole extends SecurityRole {
    private Long nodeId;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
}