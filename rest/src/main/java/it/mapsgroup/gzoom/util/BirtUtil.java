package it.mapsgroup.gzoom.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.querydsl.dao.ReportDao;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortAnalysisDao;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAnalysis;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAssoc;

@Service
public class BirtUtil {
	
	private final ReportDao reportDao;
	private final WorkEffortAnalysisDao workEffortAnalysisDao;
	
	
	public final static String pattern_date="dd/MM/yyyy";
	public final static String from_date_default="01/01/1900";
	public final static String thru_date_default="31/12/2100";
	
	
	@Autowired
    public BirtUtil( ReportDao reportDao, WorkEffortAnalysisDao workEffortAnalysisDao) {
        this.reportDao = reportDao;
        this.workEffortAnalysisDao = workEffortAnalysisDao;
    }
	
	public void checkChildRootEquality(Map<String, Object> context) {
		String workEffortId = (String)context.get("workEffortId");
		String workEffortIdChild = (String)context.get("workEffortIdChild");
		if (workEffortIdChild != null) {
			if (workEffortId != null) {
				if (!workEffortIdChild.equals(workEffortId)) {
					context.put("workEffortId", "");
				} else {						
					List<WorkEffortAssoc> assocList = reportDao.getChildRootEquality(workEffortId);
					if (!assocList.isEmpty() && assocList.size() == 1) {						
						context.put("workEffortIdChild", "");
					} else {
						context.put("workEffortId", "");
					}
				}
			} 
		} else if (workEffortId != null) {
			List<WorkEffortAssoc> assocList = reportDao.getChildRootEquality(workEffortId);
			if (!assocList.isEmpty() && assocList.size() > 1) {
				context.put("workEffortId", "");
				context.put("workEffortIdChild", workEffortId);
				
			}			
		}
		
	}
	
	public void cleanRootValue(Map<String, Object> context) {
		String workEffortId = (String)context.get("workEffortId");
		String workEffortIdChild = (String)context.get("workEffortIdChild");

		if (workEffortId != null && workEffortIdChild != null) {
			context.put("workEffortId", "");
		}
	}
	/**
	 * 
	 * @param context
	 * @throws ParseException
	 */
	public void setMonitoringDate(Map<String, Object> context) throws ParseException {
		if (context.get("monitoringDate") == null) {
			context.put("monitoringDate", Calendar.getInstance().getTime());
		}		
		
	}	

	/**
	 * utilizzato per il problema delle stampe null su birt, setta la data al 01/01/1900 se gli arriva un valore null 
	 * @param context
	 * @throws ParseException
	 */
	public void setDefaultFromDate(Map<String, Object> context) throws ParseException {
		
		if (context.get("fromDate") == null) {
			DateFormat dateFormat = new SimpleDateFormat(pattern_date);
			Date date = dateFormat.parse(from_date_default);
			long time = date.getTime();
			Timestamp fromDateTimestamp= new Timestamp(time);
			context.put("fromDate", fromDateTimestamp);
		}		
		
	}
	
	
	/**
	 * utilizzato per il problema delle stampe null su birt, setta la data al 31/12/2100 se gli arriva un valore null 
	 * @param context
	 * @throws ParseException
	 */
	public void setDefaultThruDate(Map<String, Object> context) throws ParseException {
		
		if (context.get("thruDate") == null) {
			DateFormat dateFormat = new SimpleDateFormat(pattern_date);
			Date date = dateFormat.parse(thru_date_default);
			long time = date.getTime();
			Timestamp fromDateTimestamp= new Timestamp(time);
			context.put("thruDate", fromDateTimestamp);
		}		
		
	}
	
	/**
     * Nelle stampe analisi andiamo a settare la referenceDate: parametro obbligatorio. 
     * @param context
     * @throws ParseException
     */
	public void setReferenceDate(Map<String, Object> context) {
        if (context.get("monitoringDate") == null) {
            String workEffortAnalysisId = (String)context.get("workEffortAnalysisId");
            if (workEffortAnalysisId != null) {
                WorkEffortAnalysis workEffortAnalysis = workEffortAnalysisDao.getWorkEffortAnalysis(workEffortAnalysisId);
                context.put("monitoringDate", workEffortAnalysis.getReferenceDate());
            }
        }   
                        
    }
}
