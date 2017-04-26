package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

/**
 * @author Andrea Fossi
 */
public enum DocumentStatus {
    DRAFT,
    PUBLISHED, status;

    private static final ImmutableMap<String, DocumentStatus> STR2AUTH = getMap();

    private static ImmutableMap<String, DocumentStatus> getMap() {
        ImmutableBiMap.Builder<String, DocumentStatus> b = ImmutableBiMap.builder();
        b.put("DRAFT", DRAFT);
        b.put("PUBLISHED", PUBLISHED);
        return b.build();
    }

    public static DocumentStatus fromString(String str) {
        return STR2AUTH.get(str);
    }
}
