package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

/**
 * @author Andrea Fossi.
 */
public enum NotificationEventType {
    /*
    * notifications are shown on web-view with same order as listed below
    */
    ANTIMAFIA_PROCESS_DOCUMENT_EXPIRATION(true, true, true),
    ANTIMAFIA_PROCESS_EXPIRATION(true, true, true),//
    ANTIMAFIA_PROCESS_INTEGRATION_NEEDED(false, true, true),//
    ANTIMAFIA_PROCESS_INTEGRATION_NEEDED_IMMEDIATE(false, false, true),//
    ANTIMAFIA_PROCESS_TO_BE_VALIDATED(false, true, true),//
    ANTIMAFIA_PROCESS_TO_BE_VALIDATED_IMMEDIATE(false, false, true),//
    ATI_DOCUMENT_EXPIRATION(true, true, true),
    ATI_SECURITY_DOCUMENT_EXPIRATION(true, true, true),
    COMPANY_DOCUMENT_EXPIRATION(true, true, true),
    COMPANY_SECURITY_DOCUMENT_EXPIRATION(true, true, true),
    CONTRACT_DOCUMENT_EXPIRATION(true, true, true),
    CONTRACT_EXPIRATION(true, true, true),//
    CONTRACT_INTEGRATION_NEEDED(false, true, true),//
    CONTRACT_INTEGRATION_NEEDED_IMMEDIATE(false, false, true),//
    CONTRACT_MGO_MISSING_DATA(true, true, true),//
    CONTRACT_REGULARITY_DOCUMENT_EXPIRATION(true, true, true),
    CONTRACT_TO_BE_VALIDATED(false, true, true),//
    CONTRACT_TO_BE_VALIDATED_IMMEDIATE(false, false, true),//
    CONTRACT_TRACEABILITY_DOCUMENT_EXPIRATION(true, true, true),
    EQUIPMENT_DOCUMENT_EXPIRATION(true, true, true),
    EQUIPMENT_EMPLOYMENT_EXPIRATION(true, true, true),
    PERSON_DOCUMENT_EXPIRATION(true, true, true),
    PERSON_EMPLOYMENT_DOCUMENT_EXPIRATION(true, true, true),
    PERSON_EMPLOYMENT_EXPIRATION(true, true, true),//

    TIMESHEET_EVENT_REJECTED(false, false, true),//
    ;

    private final boolean frequencyAllowed;
    private final boolean notificationPeriodAllowed;
    private final boolean readOnly;

    NotificationEventType(boolean notificationPeriodAllowed, boolean frequencyAllowed, boolean readOnly) {
        this.notificationPeriodAllowed = notificationPeriodAllowed;
        this.frequencyAllowed = frequencyAllowed;
        this.readOnly = readOnly;
    }

    public String getDescription() {
        return this.name();
    }

    public boolean isFrequencyAllowed() {
        return frequencyAllowed;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isNotificationPeriodAllowed() {
        return notificationPeriodAllowed;
    }
}
