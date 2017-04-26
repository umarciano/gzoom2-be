package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

/**
 * @author Andrea Fossi.
 */
public enum AuthenticationType {
    LDAP, DB;

    private static final ImmutableMap<String, AuthenticationType> STR2AUTH = getMap();

    private static ImmutableMap<String, AuthenticationType> getMap() {
        ImmutableBiMap.Builder<String, AuthenticationType> b = ImmutableBiMap.builder();
        b.put("LDAP", LDAP);
        b.put("DB", DB);
        return b.build();
    }

    public static AuthenticationType fromString(String str) {
        return STR2AUTH.get(str);
    }
}
