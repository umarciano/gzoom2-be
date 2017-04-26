package it.memelabs.smartnebula.lmm.security.model;


import com.google.common.collect.ImmutableMap;

import static it.mapsgroup.commons.Enums.parseMap;
import static it.memelabs.smartnebula.lmm.security.model.SecurityPermissionMask.*;

/**
 * @author Andrea Fossi.
 *         <p>
 */
public enum SecurityDomainObject {
    ACCIDENT(READ, WRITE, DELETE),//
    ADMIN(BASIC),
    ANTIMAFIA_PROCESS(READ, WRITE, DELETE, VALIDATE, DELETE),
    AUDITING(READ),
    COMPANY(READ, WRITE, DELETE),
    COMPANY_ATTACHMENT(READ),
    CONSTRUCTION_SITE_LOG(READ, WRITE, DELETE),
    CONTRACT(READ, WRITE, DELETE, VALIDATE, DELETE),
    EQUIPMENT(READ, WRITE, DELETE),
    EQUIPMENT_EMPLOYMENT(READ, WRITE, DELETE),
    GALLERY(READ, WRITE, DELETE),//
    JOB_ORDER(READ, WRITE, DELETE),
    PERSON(READ, WRITE, DELETE),
    PERSON_EMPLOYMENT(READ, WRITE, DELETE),
    WEEKLY_WORK_LOG(READ, WRITE, DELETE),
    WORK_LOG(READ, WRITE, DELETE),
    TIMESHEET(READ, WRITE, DELETE),;

    /**
     * Enum lists {@link SecurityDomainObject} and {@link SecurityPermissionMask} (allowed permissions).
     * {@link SecurityPermissionMask} are permissions that can be selected by the user
     * {@link SecurityDomainObject} can group one or more {@link SecurityObject}
     *
     * @param ops allowed {@link SecurityPermissionMask}
     */
    SecurityDomainObject(Integer... ops) {
        init(ops);
    }

    private void init(Integer[] ops) {
        for (int op : ops) {
            this.basic |= op == BASIC;
            this.read |= op == READ;
            this.write |= op == WRITE;
            this.delete |= op == DELETE;
            this.validate |= op == VALIDATE;
            this.mask |= op;
        }
    }

    private boolean basic;
    private boolean read;
    private boolean write;
    private boolean delete;
    private boolean validate;
    private int mask = 0;


    public boolean isRead() {
        return read;
    }

    public boolean isWrite() {
        return write;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isValidate() {
        return validate;
    }

    public boolean isBasic() {
        return basic;
    }

    public int getMask() {
        return mask;
    }

    public String getId() {
        return this.name();
    }

    private static final ImmutableMap<String, SecurityDomainObject> STR2PERM = parseMap(SecurityDomainObject.class);

    /**
     * Retrieves the permission given its descriptive string.
     *
     * @param perm The permissione string
     * @return The Bond permission
     */
    public static SecurityDomainObject fromString(String perm) {
        return STR2PERM.get(perm);
    }


}

