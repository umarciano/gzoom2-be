package it.mapsgroup.gzoom.service.reminder;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Report;
import it.mapsgroup.gzoom.mybatis.dao.ReminderDao;
import it.mapsgroup.gzoom.mybatis.dto.Reminder;
import it.mapsgroup.gzoom.querydsl.dao.CommEventContentAssocDao;
import it.mapsgroup.gzoom.querydsl.dao.CommunicationEventDao;
import it.mapsgroup.gzoom.querydsl.dao.ContactMechDao;
import it.mapsgroup.gzoom.querydsl.dao.ContentDao;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.CommEventContentAssoc;
import it.mapsgroup.gzoom.querydsl.dto.CommunicationEvent;
import it.mapsgroup.gzoom.querydsl.dto.ContactMech;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortType;
import it.mapsgroup.gzoom.service.report.ReportAddService;

@Service
public class ReminderService {
	private static final Logger LOG = getLogger(ReminderService.class);
	
	private final ReminderDao reminderDao;
	private final ReportAddService reportAddService;
	private final CommEventContentAssocDao commEventContentAssocDao;
	private final CommunicationEventDao communicationEventDao;
	private final WorkEffortTypeDao workEffortTypeDao;
	private final ContactMechDao contactMechDao;
	private final ContentDao contentDao;
	
	@Autowired
	public ReminderService (ReminderDao reminderDao, ReportAddService reportAddService, CommEventContentAssocDao commEventContentAssocDao, 
			CommunicationEventDao communicationEventDao, WorkEffortTypeDao workEffortTypeDao, ContactMechDao contactMechDao, ContentDao contentDao) {
		this.reminderDao = reminderDao;
		this.reportAddService = reportAddService;
		this.commEventContentAssocDao = commEventContentAssocDao;
		this.communicationEventDao = communicationEventDao;
		this.workEffortTypeDao = workEffortTypeDao;
		this.contactMechDao = contactMechDao;
		this.contentDao = contentDao;
	}	
	
	/**     
     * servizio di invio mail per il sollecito
     * @param req
     * @return
     */
    public String sendMail(Report report) {    
    	String selectName = "selectReminderWorkEffortExpiry"; //TODO ReminderReportContentIdEnum.getQuery(report.getReportContentId());
    	try {
			reminder(reportAddService.getReportParameters(report), report.getParentTypeId(), report.getContentName(), selectName);
		} catch (Exception e) {
			LOG.error("Exception "+ e);
		} 
    	return "";
    }
    
    
    /**     
     * servizio di invio mail per il sollecito
     * @param req
     * @return
     */
    public String reminderPeriodoScheduled() { 
    	return reminderScheduled("REMINDER_PERIODO");
    }
    
    
    /**     
     * servizio di invio mail per il sollecito
     * @param req
     * @return
     */
    public String reminderWorkEffortExpiry() { 
    	return reminderScheduled("REMINDER_SCADENZA");
    }
    
    
    private String reminderScheduled(String name) {
    	List<WorkEffortType> list = workEffortTypeDao.getWorkEffortTypeReminderActive(name);
    	
    	//TODO aggiungere parametri che mancano
    	HashMap<String, Object> param = new HashMap<>();
    	param.put("reportContentId", name);
    	list.forEach(wt -> {
    		LOG.info("Scheduled reminder "+wt.getWorkEffortTypeId());
    		param.put("workEffortTypeId", wt.getWorkEffortTypeId());
    		try {
				reminder(param, wt.getParentTypeId(), "BHO" ,ReminderReportContentIdEnum.getQuery(name)); //TODO
			} catch (Exception e) {
				LOG.error("Exception "+ e);
			}
    	});
    	
    	return "";
    }
	
	@SuppressWarnings("unchecked")
	public void reminder (HashMap<String, Object> reportParameters, String parentTypeId, String contentName, String selectName) throws Exception {
		LOG.info("start reminder");
		
		if (selectName.isEmpty()) {
			LOG.error("query null");
            throw new Exception("query null");
		}
		
		List<ContactMech> contactMechIdFromList = getContactMechIdFrom(parentTypeId);
		LOG.info("reminder contactMechIdFrom="+contactMechIdFromList.toString());
		
		if (contactMechIdFromList.size() <= 0) {
			LOG.error("list email address is null");
            throw new Exception("list email address is null");
		}
		
		Method setNameMethod = ReminderDao.class.getMethod(selectName, String.class, String.class, String.class, Date.class);
		List<Reminder> list = (List<Reminder>)setNameMethod.invoke(reminderDao, principal().getUserLoginId(), parentTypeId, (String)reportParameters.get("workEffortTypeId"), (Date)reportParameters.get("monitoringDate"));
		LOG.info("send mail service= : " +selectName+ " list=" + list.size() +" whit workEffortTypeId="+(String)reportParameters.get("workEffortTypeId") + " monitoringDate="+(Date)reportParameters.get("monitoringDate"));
		
		HashMap<String, Object> appMap = (HashMap<String, Object>) reportParameters.clone();
    	list.forEach(reminder -> {
    		LOG.info("reminder element : " + reminder.toString());
    		
    		//TODO controllare parametri
    		appMap.put("partyId", reminder.getPartyId());
    		LOG.info("generated report  appMap= "+ appMap.toString());
    		String activityId = reportAddService.add(reportParameters, contentName);
    		LOG.info("reminder activityId= "+ activityId);
    		
    		createCommunicationEvent(contactMechIdFromList, activityId, reminder);    		
    	});
    	
    	if (list.size() > 0) {
    		//invio mail di riepilogo
    		//invio la mail a chi ha lanciato l'invio massivo oppure alla mail di default
    		String activityId = reportAddService.add(reportParameters, contentName);
    		LOG.info("reminder activityId= "+ activityId);
    		contactMechIdFromList.forEach(contactMech -> {
    			createCommunicationEvent(contactMechIdFromList, activityId, getReportParametersAdmin((String)reportParameters.get("workEffortTypeId"), contactMech.getContactMechId()));  
    		});
    		  	
    	}
	}
	
	private Reminder getReportParametersAdmin(String workEffortTypeId, String contactMechIdFrom) {
		Reminder reminder = new Reminder();
		reminder.setContactMechIdTo(contactMechIdFrom);
		reminder.setPartyId(principal().getPartyId());
		WorkEffortType wt = workEffortTypeDao.getWorkEffortType(workEffortTypeId);
		reminder.setSubject(wt.getEtch());
		reminder.setContent(wt.getNote());
		return reminder;		
	}
	
	private void createCommunicationEvent(List<ContactMech> contactMechIdFromList, String activityId, Reminder reminder) {
		LOG.info("reminder create communication event");
		
		CommunicationEvent communicationEvent = new CommunicationEvent();
		
		communicationEvent.setContactMechIdTo(reminder.getContactMechIdTo());
		communicationEvent.setPartyIdTo(reminder.getPartyId());
		communicationEvent.setSubject(reminder.getSubject());
		communicationEvent.setContent(reminder.getContent());
		communicationEvent.setPartyIdFrom(principal().getUserLoginId());
		communicationEvent.setStatusId("COM_IN_PROGRESS");
		communicationEvent.setStatusId("COM_IN_PROGRESS");
		communicationEvent.setContentMimeTypeId("text/plain");
		communicationEvent.setCommunicationEventTypeId("AUTO_EMAIL_COMM");
		
		
		//TODO BHO li devo mettere tutti o ne basta uno??
		String contactMechIdFrom = contactMechIdFromList.get(0).getContactMechId();
		communicationEvent.setContactMechIdFrom(contactMechIdFrom);			
		
		String communicationEventId = communicationEventDao.create(communicationEvent);
		LOG.info("create communication event communicationEventId="+communicationEventId);
		
		String contentId = contentDao.getContentByActivityId(activityId).getContentId();
		CommEventContentAssoc commEventContentAssoc = new CommEventContentAssoc();
		commEventContentAssoc.setContentId(contentId);
		commEventContentAssoc.setCommunicationEventId(communicationEventId);
		commEventContentAssocDao.create(commEventContentAssoc);		
		LOG.info("create commEventContentAssoc communicationEventId="+communicationEventId + " contentId="+contentId);
		
	}
	
	/**
	 * Resistuisco la lista dei party dove inviare il riepilogo mail
	 * @return
	 */
	private List<ContactMech> getContactMechIdFrom(String parentTypeId) {
		return contactMechDao.getContactMechPartyRole("REMINDER_"+parentTypeId); 
	}

}
