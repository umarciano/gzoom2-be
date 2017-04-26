package it.memelabs.smartnebula.lmm.persistence.main.enumeration;

public enum EntityStateTag {
    DRAFT, //*
    NOT_COMPLETED, COMPLETED, //*


    TO_BE_VALIDATED, VALIDATED, NOT_VALIDATED, //
    CLOSED //


    , AUTHORIZED, NOT_AUTHORIZED, TO_BE_AUTHORIZED, INTEGRATION_NEEDED,

    ERROR,
    CANCELLED,

    /**
     * VALID PER COMPANY ED EMPLOYMENT
     Autorizzato - AUTHORIZED

     In corso di autorizzazione                 TO_BE_AUTHORIZED
     Non autorizzato                NOT_AUTHORIZED
     Non gradito                NOT_AUTHORIZED
     Lavori terminati                   NOT_AUTHORIZED
     */


}
