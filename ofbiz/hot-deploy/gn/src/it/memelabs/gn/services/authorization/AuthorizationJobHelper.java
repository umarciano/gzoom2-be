package it.memelabs.gn.services.authorization;

import it.memelabs.gn.services.communication.CommunicationEventAttributeKeyOfbiz;
import it.memelabs.gn.services.communication.EventTypeOfbiz;
import it.memelabs.gn.services.job.AbstractJobHelper;
import it.memelabs.gn.services.job.JobAttribute;
import it.memelabs.gn.services.job.JobStatusOfbiz;
import it.memelabs.gn.services.job.JobTypeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.util.EntityConditionUtil;
import it.memelabs.gn.util.find.GnFindUtil;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;

import static it.memelabs.gn.services.communication.CommunicationEventAttributeKeyOfbiz.*;

/**
 * 08/05/2014
 *
 * @author Andrea Fossi
 */
public class AuthorizationJobHelper extends AbstractJobHelper {
    private static final String module = AuthorizationJobHelper.class.getName();

    public AuthorizationJobHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    //AUTH_EXPIRATION_RECIPIENT
    private static final String HOLDER = "HOLDER";
    private static final String USER = "USER";
    //AUTH_EXPIRATION_RECIPIENT
    private static final Long TYPE_1 = 1l;
    private static final Long TYPE_2 = 2l;
    private static final Long TYPE_3 = 3l;
    private static final Long TYPE_4 = 4l;


    /**
     * @param prefix
     * @param source
     * @return
     */
    private Map<String, Object> extractItem(String prefix, Map<String, Object> source) {
        Map<String, Object> ret = new HashMap<String, Object>();
        for (String fieldName : source.keySet()) {
            if (fieldName.startsWith(prefix))
                ret.put(GnFindUtil.removePrefix(prefix, fieldName), source.get(fieldName));
        }
        return ret;
    }

    //******************************************************************************
    //* scheduleValidateAuthorizationRemindJobs                                   *
    //******************************************************************************

    /**
     * @param ownerNodeId
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public void scheduleValidateAuthorizationRemindJobs(String ownerNodeId) throws GenericServiceException, GenericEntityException {
        scheduleValidateAuthorizationRemindJobs(ownerNodeId, false);
    }

    private void scheduleValidateAuthorizationRemindJobs(String ownerNodeId, boolean ignoreScheduledAlready) throws GenericServiceException, GenericEntityException {
        AuthorizationSearchHelper authorizationSearchHelper = new AuthorizationSearchHelper(dctx, context);
        long authToBeVal = authorizationSearchHelper.getToBeValidatedAuthorizationCount(null, ownerNodeId);
        if (authToBeVal > 0) {
            List<Map<String, Object>> jobs = findJobs(null, JobStatusOfbiz.GN_ACT_JOB_SCHEDULED, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER, ownerNodeId, false);
            if (ignoreScheduledAlready || jobs.size() == 0) {
                String ownerNodeDescription = (String) authorizationSearchHelper.findPartyNodeById(ownerNodeId).get("description");
                Calendar cal = Calendar.getInstance();
                cal.setTime(UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()));
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Timestamp scheduledTimestamp = new Timestamp(cal.getTimeInMillis());
                Map<String, Object> jobAttributes = new HashMap<String, Object>();
                jobAttributes.put(CommunicationEventAttributeKeyOfbiz.OWNER_NODE_DESCRIPTION.name(), ownerNodeDescription);
                String jobId = gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER, true, ownerNodeId, 0l, scheduledTimestamp, jobAttributes);
                Debug.log(String.format("Schedules job[%s] type[%s] on %s", jobId, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER.name(), cal.toString()), module);
            }
        }
    }

    public void gnConsumeValidateAuthorizationRemindJob(String jobId) throws GenericServiceException, GenericEntityException {
        Map<String, Object> job = findJobs(jobId, JobStatusOfbiz.GN_ACT_JOB_SCHEDULED, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_TO_BE_VALIDATED_REMINDER, null, true).get(0);
        String ownerNodeId = (String) job.get("referenceKey");
        AuthorizationSearchHelper authorizationSearchHelper = new AuthorizationSearchHelper(dctx, context);
        long authToBeVal = authorizationSearchHelper.getToBeValidatedAuthorizationCount(null, ownerNodeId);
        Map<String, Object> jobAttributes = UtilMisc.getMapFromMap(job, "attributes");
        if (authToBeVal > 0) {
            //send notification to contactList GN_COM_EV_AUTH_TO_VA, but notification has type GN_COM_EV_AUTH_V_PND
            gnSendCommunicationEventToContactListEM(ownerNodeId, EventTypeOfbiz.GN_COM_EV_AUTH_TO_VA, EventTypeOfbiz.GN_COM_EV_AUTH_V_PND, jobAttributes);
            scheduleValidateAuthorizationRemindJobs(ownerNodeId, true);
        } else {

        }
    }

    public void gnSchedulePreviousAuthorizationValidateRemindJobs() throws GenericServiceException, GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("statusId", AuthorizationStatusOfbiz.GN_AUTH_TO_BE_VALID.name()));
        conds.add(EntityCondition.makeCondition("ownerNodeId", EntityComparisonOperator.NOT_EQUAL, "GN_ROOT"));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        List<GenericValue> nodeIds = delegator.findList("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), UtilMisc.toSet("ownerNodeId"), UtilMisc.toList("ownerNodeId"), GnFindUtil.getFindOptDistinct(), false);
        for (GenericValue _nodeId : nodeIds) {
            String ownerNodeId = _nodeId.getString("ownerNodeId");
            scheduleValidateAuthorizationRemindJobs(ownerNodeId, false);
        }
    }

    //******************************************************************************
    //* scheduleAuthorizationExpirationRemindJobs                                   *
    //******************************************************************************


    /**
     * Schedule job to remind authorization expiration
     *
     * @param authorization
     * @return
     * @throws GenericServiceException
     * @throws GenericEntityException
     */
    public int scheduleAuthorizationExpirationRemindJobs(Map<String, Object> authorization) throws GenericServiceException, GenericEntityException {
        List<Map<String, Object>> authorizationItems = UtilMisc.getListFromMap(authorization, "authorizationItems");
        String typeId = (String) authorization.get("typeId");
        String authorizationKey = (String) authorization.get("authorizationKey");

        Map<Long, Object> tempJobDetail = new HashMap<Long, Object>();

        WrapInt wrapInt = new WrapInt();


        if (AuthorizationTypesOfbiz.GN_AUTH_ALBO.name().equals(typeId)) {
            //grouping
            for (Map<String, Object> item : authorizationItems) {
                Timestamp validThruDate = (Timestamp) item.get("validThruDate");
                Long key = UtilDateTime.getDayStart(validThruDate).getTime();
                String categoryClassEnumId = (String) item.get("categoryClassEnumId");
                if (UtilValidate.isNotEmpty(categoryClassEnumId)) {
                    GenericValue categoryClassEnum = delegator.findOne("GnCategoryClassEnum", true, "enumId", categoryClassEnumId);
                    Map<String, String> categories = UtilMisc.getMapFromMap(tempJobDetail, key);
                    categories.put(categoryClassEnum.getString("categoryCode"), categoryClassEnum.getString("categoryDescription"));
                } else {
                    Map<String, String> categories = UtilMisc.getMapFromMap(tempJobDetail, key);
                    Debug.logError("GN_AUTH_ALBO authorizationItem without categoryClassEnumId", module);
                }
            }

            for (Long key : tempJobDetail.keySet()) {
                Timestamp expiration = new Timestamp(key);
                Map<String, String> categories = UtilMisc.getMapFromMap(tempJobDetail, key);
                List<Map<String, Object>> jobDetails = new ArrayList<Map<String, Object>>(categories.size());
                for (String categoryCode : categories.keySet())
                    jobDetails.add(UtilMisc.toMap(CATEGORY_CODE.name(), categoryCode, CATEGORY_DESCRIPTION.name(), (Object) categories.get(categoryCode)));

                if ("0".equals(AuthorizationKey.fromString(authorizationKey).getParentVersion())) {
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 8, 0, HOLDER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 7, 0, HOLDER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 6, 0, HOLDER, TYPE_2, expiration, wrapInt);//HN2
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 3, 0, HOLDER, TYPE_3, expiration, wrapInt);//HN3
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 1, 0, HOLDER, TYPE_3, expiration, wrapInt);//HN3
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 0, -1, HOLDER, TYPE_4, expiration, wrapInt); //HN4  one day after expiration
                } else {
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 6, 0, USER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 3, 0, USER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 1, 0, USER, TYPE_2, expiration, wrapInt);//HN2
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 0, -1, USER, TYPE_3, expiration, wrapInt); //HN3 one day after expiration
                }
            }
        } else { // !GN_AUTH_ALBO
            //grouping
            for (Map<String, Object> item : authorizationItems) {
                Timestamp validThruDate = (Timestamp) item.get("validThruDate");
                Long key = UtilDateTime.getDayStart(validThruDate).getTime();
                UtilMisc.addToListInMap(UtilMisc.toMap(DETAIL_DESCRIPTION.name(), item.get("agreementText")), tempJobDetail, key);
            }
            for (Long key : tempJobDetail.keySet()) {

                Timestamp expiration = new Timestamp(key);
                List<Map<String, Object>> jobDetails = UtilMisc.getListFromMap(tempJobDetail, key);
                if ("0".equals(AuthorizationKey.fromString(authorizationKey).getParentVersion())) {
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 8, 0, HOLDER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 7, 0, HOLDER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 6, 0, HOLDER, TYPE_2, expiration, wrapInt);//HN2
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 3, 0, HOLDER, TYPE_3, expiration, wrapInt);//HN3
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 1, 0, HOLDER, TYPE_3, expiration, wrapInt);//HN3
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 0, -1, HOLDER, TYPE_4, expiration, wrapInt); //HN4  one day after expiration
                } else {
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 6, 0, USER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 3, 0, USER, TYPE_1, expiration, wrapInt);//HN1
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 1, 0, USER, TYPE_2, expiration, wrapInt);//HN2
                    createAuthorizationExpirationRemindJob(authorization, authorizationKey, jobDetails, 0, -1, USER, TYPE_3, expiration, wrapInt); //HN3 one day after expiration
                }
            }
        }
        return wrapInt.getInt();
    }

    private static class WrapInt {
        private int i = 0;

        public void increment() {
            i++;
        }

        public int getInt() {
            return i;
        }
    }

    /**
     * Create job
     *
     * @param authorization
     * @param authorizationKey
     * @param jobDetails
     * @param monthBeforeExpiration
     * @param dayBeforeExpiration
     * @param recipient
     * @param type
     * @param expiration
     * @param wrapInt
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    private void createAuthorizationExpirationRemindJob(Map<String, Object> authorization, String authorizationKey, List<Map<String, Object>> jobDetails, int monthBeforeExpiration, int dayBeforeExpiration, String recipient, Long type, Timestamp expiration, WrapInt wrapInt) throws GenericEntityException, GenericServiceException {
        Timestamp todayTimestamp = UtilDateTime.getDayStart(UtilDateTime.nowTimestamp());
        Calendar cal = Calendar.getInstance();
        cal.setTime(UtilDateTime.getDayStart(expiration));
        cal.add(Calendar.MONTH, 0 - monthBeforeExpiration);
        cal.add(Calendar.DAY_OF_MONTH, 0 - dayBeforeExpiration);
        //Timestamp scheduledTimestamp = UtilDateTime.addDaysToTimestamp(UtilDateTime.getDayStart(expiration), 0 - dayBeforeExpiration);
        Timestamp scheduledTimestamp = new Timestamp(cal.getTimeInMillis());
        if (todayTimestamp.before(scheduledTimestamp)) {//create job
            Map<String, Object> jobAttributes = new HashMap<String, Object>();
            jobAttributes.put(JobAttribute.OWNER_NODE_ID.name(), authorization.get("ownerNodeId"));
            jobAttributes.put(AUTH_EXPIRATION_RECIPIENT.name(), recipient);//see use case
            jobAttributes.put(AUTH_EXPIRATION_TYPE.name(), type);//see use case
            jobAttributes.put(AUTH_EXPIRATION_DATE.name(), expiration);
            jobAttributes.put(AUTHORIZATION_NUMBER.name(), authorization.get("number"));
            jobAttributes.put(AUTHORIZATION_TYPE_ID.name(), authorization.get("typeId"));
            jobAttributes.put(AUTHORIZATION_HOLDER.name(), ((Map) authorization.get("otherPartyNodeTo")).get("groupName"));
            jobAttributes.put(AUTHORIZATION_HOLDER_BASE.name(), ((Map) authorization.get("partyNodeTo")).get("groupName"));
            jobAttributes.put(ISSUER.name(), findIssuer((String) authorization.get("partyIdFrom")).get("groupName"));
            jobAttributes.put(ISSUER_BASE.name(), ((Map) authorization.get("partyNodeFrom")).get("groupName"));
            jobAttributes.put(AUTH_EXPIRATION_LEFT_MONTHS.name(), monthBeforeExpiration);

            jobAttributes.put(CommunicationEventAttributeKeyOfbiz.AUTHORIZATION_DETAILS.name(), jobDetails);

            gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING, true, authorizationKey, 0l, scheduledTimestamp, jobAttributes);
            if (wrapInt != null) wrapInt.increment();
        }
        return;
    }

    public void disableJob(String referenceKey) throws GenericServiceException {
        gnUpdateJobStatusByReferenceKey(referenceKey, JobStatusOfbiz.GN_ACT_JOB_CANCELLED, null, true);
    }

    /**
     * @param issuerBasePartyId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     */
    private Map<String, Object> findIssuer(String issuerBasePartyId) throws GenericEntityException {
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("REL", "PartyRelationship");
        dv.addMemberEntity("PG", "PartyGroup");
        dv.addAlias("REL", "partyIdTo");
        dv.addAlias("REL", "partyIdFrom");
        dv.addAlias("REL", "roleTypeIdFrom");
        dv.addAlias("REL", "roleTypeIdTo");
        dv.addAlias("REL", "partyRelationshipTypeId");
        dv.addAlias("REL", "thruDate");
        dv.addAlias("REL", "fromDate");
        dv.addAliasAll("PG", null);
        dv.addViewLink("REL", "PG", false, ModelKeyMap.makeKeyMapList("partyIdFrom", "partyId"));
        EntityCondition cond = EntityConditionUtil.makeRelationshipCondition(null, PartyRoleOfbiz.GN_ISSUER, issuerBasePartyId, PartyRoleOfbiz.GN_ISSUER_BASE, RelationshipTypeOfbiz.GN_OWNS, true);
        EntityListIterator listIteratorByCondition = delegator.findListIteratorByCondition(dv, cond, null, null, null, null);
        List<GenericValue> ret = listIteratorByCondition.getCompleteList();
        listIteratorByCondition.close();
        if (ret.size() == 0) return new HashMap<String, Object>();
        else return UtilMisc.makeMapWritable(ret.get(0));
    }

//    --- used to consume jobs

    /**
     * Schedule job to remind authorization expiration for authorization created before this feature
     *
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public void gnScheduleAuthorizationExpirationRemindJobsForPreviousAuthorization() throws GenericEntityException, GenericServiceException {
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("AUTH", "GnAuthorizationAndAgreement");
        dv.addMemberEntity("ITEM", "GnAuthorizationAndAgreementItem");
        dv.addMemberEntity("PTO", "GnPartyGroupPartyNode");
        dv.addMemberEntity("OPTO", "GnPartyGroupPartyNode");
        dv.addMemberEntity("PFROM", "GnPartyGroupPartyNode");
        dv.addAliasAll("AUTH", "AUTH_");
        dv.addAliasAll("ITEM", "ITEM_");
        dv.addAliasAll("PFROM", "PFROM_");
        dv.addAliasAll("PTO", "PTO_");
        dv.addAliasAll("OPTO", "OPTO_");
        dv.addViewLink("AUTH", "PFROM", false, ModelKeyMap.makeKeyMapList("partyIdFrom", "partyId"));
        dv.addViewLink("AUTH", "ITEM", false, ModelKeyMap.makeKeyMapList("agreementId", "agreementId"));
        dv.addViewLink("AUTH", "PTO", false, ModelKeyMap.makeKeyMapList("partyIdTo", "partyId"));
        dv.addViewLink("AUTH", "OPTO", false, ModelKeyMap.makeKeyMapList("otherPartyIdTo", "partyId"));

        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("AUTH_StatusId", AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name()));
        conds.add(GnFindUtil.makeIsEmptyCondition("AUTH_InternalStatusId"));
        EntityListIterator it = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition(conds), null, null, UtilMisc.toList("AUTH_AgreementId", "ITEM_AgreementItemSeqId"), null);
        List<GenericValue> list = it.getCompleteList();
        list.size();
        it.close();
        Map<String, Map<String, Object>> authTree = new TreeMap<String, Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            GenericValue gv = list.get(i);
            String authKey = gv.getString("ITEM_" + "AuthorizationKey");

            //authKey/auth
            if (authTree.get(authKey) == null) {
                Map<String, Object> auth = extractItem("AUTH_", gv);
                authTree.put(authKey, auth);
            }
            //authorizationItems
            Map<String, Object> item = extractItem("ITEM_", gv);
            UtilMisc.addToListInMap(item, authTree.get(authKey), "authorizationItems");
            //partyNodeFrom
            authTree.get(authKey).put("partyNodeFrom", extractItem("PFROM_", gv));
            //partyNodeTo
            authTree.get(authKey).put("partyNodeTo", extractItem("PTO_", gv));
            //otherPartyNodeTo
            authTree.get(authKey).put("otherPartyNodeTo", extractItem("OPTO_", gv));
        }

        List<Map<String, Object>> jobs = findJobs(null, JobStatusOfbiz.GN_ACT_JOB_SCHEDULED, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING, null, false);
        Set<String> scheduledKey = new HashSet<String>(jobs.size());
        for (Map<String, Object> job : jobs) {
            scheduledKey.add((String) job.get("referenceKey"));
        }
        int count = 0;
        for (Map<String, Object> auth : authTree.values()) {
            String authorizationKey = (String) auth.get("authorizationKey");
            String agreementId = (String) auth.get("agreementId");
            if (!scheduledKey.contains(authorizationKey)) {
                count += scheduleAuthorizationExpirationRemindJobs(auth);
                Debug.log(String.format("Scheduled %s jobs for authorization[%s,%s]", JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING.name(), agreementId, authorizationKey));
            } else {
                Debug.log(String.format("Authorization[%s,%s] as already %s jobs scheduled.", agreementId, authorizationKey, JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING.name()));
            }


            if (count > 100) {
                Debug.log("Scheduled [" + count + "] authorization expiration jobs");
                gnCreateJob(JobTypeOfbiz.GN_ACT_JOB_AUTHORIZATION_EXPIRING_MANAGE_OLD, false, null, 0, UtilDateTime.nowTimestamp(), new HashMap<String, Object>(0));
                return;
            }
        }
        return;
    }

    /**
     * Count published authorization and draft authorization
     *
     * @param ownerNodeId
     * @return
     * @throws GenericEntityException
     */
    public boolean gnCheckInvitedCompanyHasPublishedAuthorization(String ownerNodeId) throws GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("parentVersion", "0"));
        conds.add(GnFindUtil.makeOrConditionById("statusId", Arrays.asList(AuthorizationStatusOfbiz.GN_AUTH_PUBLISHED.name())));
        conds.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        conds.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        long published = delegator.findCountByCondition("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds), null, null);

        List<EntityCondition> conds2 = new ArrayList<EntityCondition>();
        conds2.add(EntityCondition.makeCondition("parentVersion", "0"));
        conds2.add(GnFindUtil.makeOrConditionById("statusId", Arrays.asList(AuthorizationStatusOfbiz.GN_AUTH_DRAFT.name())));
        conds2.add(GnFindUtil.makeIsEmptyCondition("internalStatusId"));
        conds2.add(EntityCondition.makeCondition("ownerNodeId", ownerNodeId));
        long draft = delegator.findCountByCondition("GnAuthorizationAndAgreement", EntityCondition.makeCondition(conds2), null, null);

        if (published == 0 && draft >= 0) {
            return false;
        } else {
            return true;
        }

    }

}
