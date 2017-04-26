package it.memelabs.smartnebula.lmm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class SecurityRole extends Identifiable {
    private List<SecurityPermission> permissions;

    public SecurityRole() {
        permissions = new ArrayList<>();
    }

    public List<SecurityPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SecurityPermission> permissions) {
        this.permissions = permissions;
    }
}
