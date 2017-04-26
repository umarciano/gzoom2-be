package it.memelabs.smartnebula.lmm.persistence.enumeration;


/**
 * @author Andrea Fossi.
 */
public enum JobStatus {
    SCHEDULED,
    RUNNING,
    MISFIRE,
    CANCELLED,
    CONSUMED,
    //COMMITTED,
    //INCOMPLETE,
    FAILED
}
