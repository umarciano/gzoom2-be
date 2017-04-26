package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityRoleEx extends SecurityRole {
    private List<SecurityRolePermission> permissions;

    public SecurityRoleEx() {
        permissions = new ArrayList<>();
    }

    public List<SecurityRolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SecurityRolePermission> permissions) {
        this.permissions = permissions;
    }

    public Set<String> keySet() {
        return permissions.stream().map(SecurityRolePermissionKey::getEntityId).collect(Collectors.toSet());
    }
}