package it.mapsgroup.gzoom.report.querydsl.dao;

import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class ReportActvityFilter {
    List<ReportActivityStatus> states;

    public ReportActvityFilter() {
        states = new ArrayList<>();
    }

    public List<ReportActivityStatus> getStates() {
        return states;
    }

    public void setStates(List<ReportActivityStatus> states) {
        this.states = states;
    }
}
