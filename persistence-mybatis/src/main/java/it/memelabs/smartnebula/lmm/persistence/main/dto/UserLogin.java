package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;

public class UserLogin extends UserLoginPersistent {
    private List<Node> nodes;
    private List<SecurityUserRole> roles;
    private List<SecurityRolePermission> aclPermissions;
    private Node activeNode;

    public UserLogin() {
        nodes = new ArrayList<>();
        roles = new ArrayList<>();
        aclPermissions = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<SecurityUserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecurityUserRole> roles) {
        this.roles = roles;
    }

    public List<SecurityRolePermission> getAclPermissions() {
        return aclPermissions;
    }

    public void setAclPermissions(List<SecurityRolePermission> aclPermissions) {
        this.aclPermissions = aclPermissions;
    }

    public Node getActiveNode() {
        return activeNode;
    }

    public void setActiveNode(Node activeNode) {
        this.activeNode = activeNode;
    }
}