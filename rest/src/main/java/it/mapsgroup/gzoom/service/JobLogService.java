package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Localization;
import it.mapsgroup.gzoom.persistence.common.SequenceGenerator;
import it.mapsgroup.gzoom.querydsl.dao.JobLogDao;
import it.mapsgroup.gzoom.querydsl.dto.JobLog;
import it.mapsgroup.gzoom.querydsl.dto.JobLogJobExecParams;
import it.mapsgroup.gzoom.querydsl.dto.JobLogLog;
import it.mapsgroup.gzoom.querydsl.dto.JobLogServiceType;
import org.jose4j.json.internal.json_simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static it.mapsgroup.gzoom.security.Principals.principal;

/**
 *
 */
@Service
public class JobLogService {

    private final JobLogDao jobLogDao;
    private final LocaleService localeService;
    private final SequenceGenerator sequenceGenerator;

    @Autowired
    public JobLogService(JobLogDao jobLogDao, LocaleService localeService, SequenceGenerator sequenceGenerator) {
        this.jobLogDao = jobLogDao;
        this.localeService = localeService;

        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * This function adds a jobLog.
     *
     * @param jobLog
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLog(JobLog jobLog){

        jobLogDao.addJobLog(jobLog, principal().getUsername());
    }

    /**
     * This function sets up a new jobLog and returns it.
     *
     * @param jobLog
     * @param serviceTypeId
     * @param serviceName
     * @return JobLog
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JobLog jobLogStart(JobLog jobLog, String serviceTypeId, String serviceName){

        String id = sequenceGenerator.getNextSeqId("JobLog");
        jobLog.setJobLogId(id);
        jobLog.setLogDate(LocalDateTime.now());
        jobLog.setServiceTypeId(serviceTypeId);
        jobLog.setServiceName(serviceName);
        jobLog.setBlockingErrors(BigInteger.valueOf(0));
        jobLog.setWarningMessages(BigInteger.valueOf(0));
        jobLog.setRecordElaborated(BigInteger.valueOf(0));
        addJobLog(jobLog);

        return jobLog;
    }

    /**
     * This function sets the end date of the jobLog.
     *
     * @param jobLog
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void jobLogEnd(JobLog jobLog) {

        jobLog.setLogEndDate(LocalDateTime.now());
        jobLogDao.updateJobLogEndDate(jobLog);

    }

    /**
     * This function sets and adds a jobLogLog.
     *
     * @param jobLogLog
     * @param logCode
     * @param parameters
     * @param addLogMessage
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogLog(JobLogLog jobLogLog, String logCode, Map<String, String> parameters, String addLogMessage){

        if (JSONValue.toJSONString(parameters).length() < 255)
            jobLogLog.setValueRef3(JSONValue.toJSONString(parameters));
        else
            jobLogLog.setValueRef3(JSONValue.toJSONString(parameters).substring(0, 251) + "...");

        jobLogLog.setLogCode(logCode);

        /*from logCode to logMessage*/
        Locale locale = localeService.getLocation(principal().getUsername());
        Localization loc = localeService.getLocalization(locale);
        jobLogLog.setValueRef2(loc.getLanguage());
        jobLogLog.setLogMessage(loc.getTranslations().get(logCode) + addLogMessage );
        /*-------------------------*/

        jobLogLog.setCreatedByUserLogin(principal().getUsername());
        jobLogDao.addJobLogLog(jobLogLog);
    }

    /**
     * This function sets and adds a START_QUERY_EXEC jobLogLog
     *
     * @param jobLogLog
     * @param parameters
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogLogStart(JobLogLog jobLogLog, Map<String, String> parameters){

        jobLogLog.setLogTypeEnumId("LOG_INFO");
        addJobLogLog(jobLogLog, "START_QUERY_EXEC", parameters, parameters.get("queryCode"));
    }

    /**
     * This function sets and adds a FINAL_QUERY_EXEC jobLogLog
     *
     * @param jobLogLog
     * @param parameters
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogLogFinal(JobLogLog jobLogLog, Map<String, String> parameters){

        jobLogLog.setLogTypeEnumId("LOG_INFO");
        addJobLogLog(jobLogLog, "FINAL_QUERY_EXEC", parameters, parameters.get("finalQuery"));
    }

    /**
     * This function sets and adds a ERROR_QUERY_EXECUTOR jobLogLog.
     *
     * @param jobLogLog
     * @param parameters
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogLogError(JobLogLog jobLogLog, Map<String, String> parameters) {

        jobLogLog.setLogTypeEnumId("LOG_ERR");
        addJobLogLog(jobLogLog, "ERROR_QUERY_EXECUTOR", parameters, parameters.get("errorMsg"));
    }

    /**
     * This function adds an error to the jobLog and adds a ERROR_QUERY_EXECUTOR jobLogLog.
     * @param jobLog
     * @param jobLogLog
     * @param e
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void setAndAddErrorLog(JobLog jobLog, JobLogLog jobLogLog, String e) {

        Map<String, String> logParameters = new HashMap<>();
        logParameters.put("errorMsg", e);
        addJobLogLogError(jobLogLog, logParameters);
        BigInteger value = jobLog.getBlockingErrors().add(BigInteger.valueOf(1));
        jobLog.setBlockingErrors(value);
        jobLogEnd(jobLog);
    }

    /**
     * This function sets and adds a ERROR_QUERY_EXECUTOR (Debug) jobLogLog.
     *
     * @param jobLogLog
     * @param parameters
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogLogDebug(JobLogLog jobLogLog, Map<String, String> parameters) {

        jobLogLog.setLogTypeEnumId("LOG_DEBUG");
        addJobLogLog(jobLogLog, "ERROR_QUERY_EXECUTOR", parameters, parameters.get("errorMsg"));
    }

    /**
     * This function adds a query execution parameter.
     *
     * @param jobLogId
     * @param condName
     * @param condComm
     * @param condInfo
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogJobExecParams (String jobLogId, String condName, String condComm, String condInfo) {

        JobLogJobExecParams jobLogJobExecParams = new JobLogJobExecParams();
        jobLogJobExecParams.setJobLogId(jobLogId);
        jobLogJobExecParams.setParameterName(condName);
        jobLogJobExecParams.setParameterDescription(condComm);
        jobLogJobExecParams.setParameterValue(condInfo);
        jobLogDao.addJobLogJobExecParams(jobLogJobExecParams, principal().getUsername());
    }

    /**
     * This function adds a jobLogServiceType.
     *
     * @param jobLogServiceType
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addJobLogServiceType (JobLogServiceType jobLogServiceType) {

        jobLogDao.addJobLogServiceType(jobLogServiceType);
    }


}
