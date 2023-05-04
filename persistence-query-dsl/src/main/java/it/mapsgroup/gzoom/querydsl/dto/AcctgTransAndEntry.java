package it.mapsgroup.gzoom.querydsl.dto;

public class AcctgTransAndEntry extends AcctgTrans{
    private AcctgTransEntry acctgTransEntry;

    public AcctgTransEntry getAcctgTransEntry() {
        return acctgTransEntry;
    }

    public void setAcctgTransEntry(AcctgTransEntry acctgTransEntry) {
        this.acctgTransEntry = acctgTransEntry;
    }
}
