package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrea Fossi.
 */
public enum WorkLogEventDirection {
    IN, OUT;

    public static WorkLogEventDirection fromString(String s) {
        if (StringUtils.equalsIgnoreCase("I", s)) return IN;
        else if (StringUtils.equalsIgnoreCase("O", s)) return OUT;
        else return null;
    }
}
