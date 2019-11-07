package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.model.User;
import it.mapsgroup.gzoom.querydsl.dto.Activity;

import org.springframework.stereotype.Component;

/**
 * @author Fabio G. Strozzi
 */
@Component
public class DtoMapper {

    private static final String SEP = " ";


    public Timesheet copy(it.mapsgroup.gzoom.querydsl.dto.TimesheetEx from, Timesheet to) {

        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate().toLocalDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate().toLocalDate());
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        to.setActualHours(from.getActualHours());
        to.setContractHours(from.getContractHours());
        to.setPartyName(from.getParty().getPartyName());
        return to;
    }

    public TimeEntry copy(it.mapsgroup.gzoom.querydsl.dto.TimeEntryEx from, TimeEntry to) {
        if (from.getFromDate() != null)
            to.setFromDate(from.getFromDate());
        if (from.getThruDate() != null)
            to.setThruDate(from.getThruDate());
        to.setTimesheetId(from.getTimesheetId());
        to.setWorkEffortId(from.getWorkEffortId());
        to.setPercentage(from.getPercentage());
        to.setTimeEntryId(from.getTimeEntryId());
        to.setDescription(from.getWorkEffort1().getWorkEffortName()+" - "+
                            from.getWorkEffort2().getWorkEffortName()+" - "+
                            from.getWorkEffort3().getWorkEffortName());
        return to;
    }

    public it.mapsgroup.gzoom.model.Activity copy(Activity from, it.mapsgroup.gzoom.model.Activity to) {
        to.setWorkEffortId(from.getWorkEffort3().getWorkEffortId());
        to.setTimesheetId(from.getTimesheetId());
        to.setAttivitaLiv1(from.getWorkEffort1().getWorkEffortName());
        to.setAttivitaLiv2(from.getWorkEffort2().getWorkEffortName());
        to.setAttivitaLiv3(from.getWorkEffort3().getWorkEffortName());
        to.setDescription(from.getWorkEffort1().getWorkEffortName()+" - "+
                from.getWorkEffort2().getWorkEffortName()+" - "+
                from.getWorkEffort3().getWorkEffortName());
        return to;
    }
 
    
    public it.mapsgroup.gzoom.model.Report copy(it.mapsgroup.gzoom.querydsl.dto.Report from, it.mapsgroup.gzoom.model.Report to) {
        if (from == null) {
        	return to;
        }
    	to.setWorkEffortTypeId(from.getWorkEffortType().getWorkEffortTypeId());
        to.setParentTypeId(from.getWorkEffortType().getParentTypeId());
        to.setDescription(from.getDescription());        
        to.setDescriptionLang(from.getDescriptionLang());
        to.setServiceName(from.getServiceName());
        to.setReportContentId(from.getContentId());
        to.setContentName(from.getContentName());
        
    	//workEffortAnalysis campi provenente dall'analisi
        if (from.getWorkEffortAnalysis() != null) {
        	to.setWorkEffortAnalysisId(from.getWorkEffortAnalysis().getWorkEffortAnalysisId());
        	to.setAnalysis(true);
            to.setEtch(from.getWorkEffortAnalysis().getDescription5());
            to.setEtchLang(from.getWorkEffortAnalysis().getDescription5()); 
            
        } else {
        	to.setAnalysis(false);        	
        }
        
    	//workEffortTypeContent campi provenenti dal report
        if (from.getWorkEffortTypeContent() != null) {
        	to.setSequenceNum(from.getWorkEffortTypeContent().getSequenceNum());
            to.setEtch(from.getWorkEffortTypeContent().getEtch());
            to.setEtchLang(from.getWorkEffortTypeContent().getEtchLang()); 
            to.setUseFilter(from.getWorkEffortTypeContent().getUseFilter());
        	
        }
        
        //TODO gestire il LANG
        to.setReportName(to.getEtch() == null ? to.getDescription() : to.getEtch());
        
        return to;
    }

    public it.mapsgroup.gzoom.model.Person copy(it.mapsgroup.gzoom.querydsl.dto.PersonEx from , it.mapsgroup.gzoom.model.Person to) {
        if (from == null) {
            return to;
        }
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setParentRoleCode(from.getPartyParentRole() != null? from.getPartyParentRole().getParentRoleCode() : "");

        to.setEmail(from.getContactMech() != null? from.getContactMech().getInfoString() : "");
        // TODO to.setFromDate(from.getFirstName());
        to.setEndDate(from.getParty() != null? from.getParty().getEndDate() : null);
        to.setStatusDescription(from.getStatusItem() != null? from.getStatusItem().getDescription() : "");

        to.setEmplPositionTypeDescription(from.getEmplPositionType() != null? from.getEmplPositionType().getDescription() : "");
        to.setEmploymentAmount(from.getEmploymentAmount());

        return to;
    }

}
