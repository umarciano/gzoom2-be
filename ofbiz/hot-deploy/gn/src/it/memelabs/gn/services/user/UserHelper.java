package it.memelabs.gn.services.user;

import it.memelabs.gn.security.PasswordConfiguration;
import it.memelabs.gn.security.PasswordValidator;
import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.communication.CommunicationEventTypeOfbiz;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.services.node.CompanyAttributeOfbiz;
import it.memelabs.gn.services.node.PartyRoleOfbiz;
import it.memelabs.gn.services.node.RelationshipTypeOfbiz;
import it.memelabs.gn.services.security.PermissionsOfbiz;
import it.memelabs.gn.services.system.GnAuthenticator;
import it.memelabs.gn.util.*;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.crypto.HashCrypt;
import org.ofbiz.base.util.*;
import org.ofbiz.common.login.LoginServices;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericPK;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 31/01/13
 *
 * @author Andrea Fossi
 */
public class UserHelper extends AbstractServiceHelper {

    public static final long PASSWORD_RESET_UUID_VALIDITY = 24 * 60 * 60 * 1000L;

    private static final String module = UserHelper.class.getName();
    private GnContextHelper gnContextHelper;
    private GnLoginHelper gnLoginHelper;

    public UserHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        gnContextHelper = new GnContextHelper(dctx, context);
        gnLoginHelper = new GnLoginHelper(dctx, context);
    }

    protected Map<String, Object> getPartyContact(String partyId) throws GenericServiceException {
        Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("gnGetPartyContact", "IN", context);
        srvContext.put("partyId", partyId);
        Map<String, Object> srvResult = dispatcher.runSync("gnGetPartyContact", srvContext);
        return UtilGenerics.checkMap(srvResult.get("contact"));
    }

    protected Map<String, Object> findUserByUserLoginId(String userLoginId, boolean fetchContext, boolean fetchCompanyProfile) throws GenericEntityException, GenericServiceException {
        Map<String, Object> filter = UtilMisc.toMap("userLoginId", (Object) userLoginId);
        List<GenericValue> ret;
        try {
            ret = delegator.findByAnd("GnPartyAndUserLoginAndPerson", filter);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error");
            return GnServiceUtil.returnError(e.getMessage());
        }
        if (UtilValidate.isEmpty(ret)) {
            //    throw new GenericEntityException("no user found with userLoginId[" + userLoginId + "]");
            Map<String, Object> result = FastMap.newInstance();
            result.put("lastName", "");
            result.put("firstName", "");
            result.put("userLoginId", userLoginId);
            if (fetchContext)
                result.put("fetchCompanyProfile", Collections.EMPTY_LIST);
            if (fetchCompanyProfile)
                result.put("companyProfile", FastMap.newInstance());
            return result;
        } else {
            if (ret.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "more than one users found with userLoginId[" + userLoginId + "]");
            else {
                GenericValue person = ret.get(0);
                String partyId = person.getString("partyId");
                Map<String, Object> result = FastMap.newInstance();
                result.put("lastName", person.getString("lastName"));
                result.put("firstName", person.getString("firstName"));
                result.put("userLoginId", userLoginId);

                Map<String, Object> userCompany = null;
                if (fetchContext || fetchCompanyProfile) {
                    userCompany = findCompanyWhereUserIsEmployed(person.getString("partyId"));
                }
                if (fetchContext) {
                    List<Map<String, Object>> userContexts = gnContextHelper.findContextByUserLoginId(userLoginId);
                    result.put("contexts", userContexts);
                    if (null != userContexts && userContexts.size() > 0) {
                        result.put("contextId", userContexts.get(0).get("partyId"));
                    }
                    result.put("company", userCompany);
                }
                result.put("contact", getPartyContact(partyId));

                if (fetchCompanyProfile) {
                    Map<String, Object> companyProfile = FastMap.newInstance();
                    String companyId = (userCompany != null) ? (String) userCompany.get("partyId") : null;
                    if (companyId == null || companyId.isEmpty()) {
                        Debug.log("WARNING: No company associated to user[" + userLoginId + "].");
                        companyProfile.put("rootPublicationEnabled", "N");
                        companyProfile.put("privateAuthorizationEnabled", "N");
                        companyProfile.put("privateAuthCheckEnabled", "Y");
                    } else {
                        Map<String, Object> rootPubMap = dispatcher.runSync("gnFindPartyAttributeById",
                                UtilMisc.toMap("userLogin", userLogin, "partyId", companyId, "attrName", CompanyAttributeOfbiz.ROOT_PUBLISH.name()));
                        Map<String, Object> privAuthPubMap = dispatcher.runSync("gnFindPartyAttributeById",
                                UtilMisc.toMap("userLogin", userLogin, "partyId", companyId, "attrName", CompanyAttributeOfbiz.PRIVATE_AUTH_PUBLISH.name()));
                        Map<String, Object> privAuthCheck = dispatcher.runSync("gnFindPartyAttributeById",
                                UtilMisc.toMap("userLogin", userLogin, "partyId", companyId, "attrName", CompanyAttributeOfbiz.PRIVATE_AUTH_CHECK.name()));
                        companyProfile.put("rootPublicationEnabled", (rootPubMap != null && rootPubMap.get("attrValue") != null) ? rootPubMap.get("attrValue") : "N");
                        companyProfile.put("privateAuthorizationEnabled", (privAuthPubMap != null && privAuthPubMap.get("attrValue") != null) ? privAuthPubMap.get("attrValue") : "N");
                        companyProfile.put("privateAuthCheckEnabled", (privAuthCheck != null && privAuthCheck.get("attrValue") != null) ? privAuthCheck.get("attrValue") : "Y");
                    }
                    result.put("companyProfile", companyProfile);
                }

                return result;
            }
        }
    }


    protected Map<String, Object> findUserByPartyId(String partyId, boolean fetchContext) throws GenericEntityException, GenericServiceException {
        Map<String, Object> filter = UtilMisc.toMap("partyId", (Object) partyId);
        List<GenericValue> ret;
        try {
            ret = delegator.findByAnd("GnPartyAndUserLoginAndPerson", filter);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error");
            return GnServiceUtil.returnError(e.getMessage());
        }
        if (UtilValidate.isEmpty(ret)) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "no user found with partyId[" + partyId + "]");
            /*  Map<String, Object> result = FastMap.newInstance();
              result.put("lastName", "");
              result.put("firstName", "");
              result.put("userLoginId", "");
              if (fetchContext) result.put("contexts", Collections.EMPTY_LIST);
              return result;*/
        } else {
            if (ret.size() > 1)
                throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "more than one users found with partyId[" + partyId + "]");
            else {
                GenericValue person = ret.get(0);
                Map<String, Object> result = FastMap.newInstance();
                result.put("lastName", person.getString("lastName"));
                result.put("firstName", person.getString("firstName"));
                result.put("userLoginId", person.getString("userLoginId"));
                List<String> loginSourceIds = gnLoginHelper.findLoginSourceId(person.getString("userLoginId"));
                result.put("loginSourceIds", loginSourceIds);
                if (loginSourceIds.size() > 0) {
                    if (loginSourceIds.contains(LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name())) {
                        result.put("loginSourceId", LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.name());
                    } else {
                        result.put("loginSourceId", loginSourceIds.get(0));
                    }
                } else {
                    result.put("loginSourceId", null);
                }


                if (fetchContext) {
                    List<Map<String, Object>> userContexts = gnContextHelper.findContextByUserLoginId(person.getString("userLoginId"));
                    result.put("contexts", userContexts);
                    if (null != userContexts && userContexts.size() > 0) {
                        result.put("contextId", userContexts.get(0).get("partyId"));
                    }
                    result.put("company", findCompanyWhereUserIsEmployed(person.getString("partyId")));
                }
                result.put("contact", getPartyContact(partyId));

                return result;
            }
        }
    }

    /**
     * @param userLoginPartyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected Map<String, Object> findCompanyWhereUserIsEmployed(String userLoginPartyId) throws GenericEntityException, GenericServiceException {
        List<GenericValue> ret = delegator.findList("PartyRelationship", EntityConditionUtil.makeRelationshipCondition(userLoginPartyId, "GN_USER", null, "GN_COMPANY", RelationshipTypeOfbiz.EMPLOYMENT.name(), true), null, null, null, true);
        if (ret.size() == 1) {
            return findPartyNodeById((String) ret.get(0).get("partyIdTo"), true);
        } else if (ret.size() > 1) {
            Debug.logWarning("Not unique result found. Query result " + ret.size() + " EMPLOYMENT partyRelationships.", module);
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Not unique result found. Query result " + ret.size() + " EMPLOYMENT partyRelationships.");
        }
        /*if (ret.size() == 0)*/
        return null;
    }

    /**
     * @param userLoginPartyId
     * @param companyPartyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    public boolean isUserIsEmployedInCompany(String userLoginPartyId, String companyPartyId) throws GenericEntityException, GenericServiceException {
        List<GenericValue> ret = delegator.findList("PartyRelationship", EntityConditionUtil.makeRelationshipCondition(userLoginPartyId, "GN_USER", companyPartyId, "GN_COMPANY", RelationshipTypeOfbiz.EMPLOYMENT.name(), true), null, null, null, true);
        if (ret.size() == 1) {
            return true;
        }
        if (ret.size() > 1) {
            Debug.logWarning("Not unique result found. Query result " + ret.size() + " EMPLOYMENT partyRelationships.", module);
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "Not unique result found. Query result " + ret.size() + " EMPLOYMENT partyRelationships.");
        }
        /*if (ret.size() == 0)*/
        return false;
    }

    /**
     * @param companyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    protected List<Map<String, ? extends Object>> findUsersByEmployingCompanyId(String companyId) throws GenericEntityException, GenericServiceException {
        List<Map<String, ? extends Object>> users = FastList.newInstance();
        List<GenericValue> rels = delegator.findList("PartyRelationship", EntityConditionUtil.makeRelationshipCondition(null, "GN_USER", companyId, "GN_COMPANY", RelationshipTypeOfbiz.EMPLOYMENT.name(), true), null, null, null, true);
        for (GenericValue rel : rels) {
            String userLoginPartyId = rel.getString("partyIdFrom");
            users.add(findUserByPartyId(userLoginPartyId, false));
        }
        return users;
    }

    /**
     * call service that doesn't require permissions
     *
     * @param partyId
     * @return
     * @throws GenericEntityException
     * @throws GenericServiceException
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> findPartyNodeById(String partyId, boolean findRelationships) throws GenericEntityException, GenericServiceException {
        Map<String, Object> result = dispatcher.runSync("gnInternalFindPartyNodeById", UtilMisc.toMap("userLogin", userLogin, "partyId", partyId, "findRelationships", (findRelationships) ? "Y" : "N"));
        return (Map<String, Object>) result.get("partyNode");
    }

    /**
     * Calls OfBiz updatePassword service with the current logged in user
     *
     * @param currentPassword
     * @param newPassword
     * @param newPasswordVerify
     * @return
     */
    protected Map<String, Object> updatePassword(String currentPassword, String newPassword, String newPasswordVerify) throws GenericServiceException, GenericEntityException {
        checkOldPassword(currentPassword, newPassword, userLogin.getString("salt"));

        String activeContextId = userLogin.getString("activeContextId");
        // Map<String, Object> res = dispatcher.runSync("updatePassword", UtilMisc.toMap("userLogin", userLogin, "currentPassword", currentPassword, "newPassword", newPassword, "newPasswordVerify", newPasswordVerify));
        //userLogin = (GenericValue) res.get("updatedUserLogin");

        String pc = getPasswordConstraints(userLogin.getString("userLoginId"));
        if (UtilValidate.isNotEmpty(pc)) {
            PasswordConfiguration config = new PasswordConfiguration(pc);
            PasswordValidator.Response response = new PasswordValidator().validate(config, newPassword);
            if (!response.isValid()) throw new GnServiceException(OfbizErrors.PASSWORD_TOO_WEAK, response.getMessage());
        }
        userLogin = GnAuthenticator.updateSaltedPassword(userLogin, currentPassword, newPassword, newPasswordVerify, delegator);

        userLogin.set("activeContextId", activeContextId);
        HttpServletRequest req = (HttpServletRequest) context.get("request");
        req.setAttribute("userLogin", userLogin);
        req.getSession().setAttribute("userLogin", userLogin);
        return GnServiceUtil.returnSuccess();
    }


    /**
     * Checks if the reset password operation isn't expired
     *
     * @throws GenericEntityException
     */
    protected boolean canResetPassword(String userLoginId, String uuid) throws GenericEntityException, GenericServiceException {
        GenericValue record = delegator.findOne("GnUserLoginPasswordReset", UtilMisc.toMap("uuid", uuid), false);
        if (UtilValidate.isEmpty(record)) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "GnUserLoginPasswordReset[" + uuid + "] not found.");
        }
        if (!userLoginId.equals(record.getString("userLoginId"))) {
            throw new GnServiceException(OfbizErrors.EXPIRED_OPERATION, "The UserLogin[" + userLoginId + "] hasn't any password reset record associated.");
        }
        if (!UtilDateTime.nowTimestamp().before(new Timestamp(record.getTimestamp("createdStamp").getTime() + PASSWORD_RESET_UUID_VALIDITY))) {
            throw new GnServiceException(OfbizErrors.EXPIRED_OPERATION, "The reset operation is expired");
        }
        return true;
    }

    /**
     * Force user's password change
     *
     * @param userLoginId
     * @param password
     * @throws GenericEntityException
     */
    protected void resetPassword(String userLoginId, String password) throws GenericEntityException, GnServiceException {
        String pc = getPasswordConstraints(userLoginId);
        if (UtilValidate.isNotEmpty(pc)) {
            PasswordConfiguration config = new PasswordConfiguration(pc);
            PasswordValidator.Response response = new PasswordValidator().validate(config, password);
            if (!response.isValid()) throw new GnServiceException(OfbizErrors.PASSWORD_TOO_WEAK, response.getMessage());
        }

        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));
        GenericValue userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        String salt = userLogin.getString("salt");
        if (UtilValidate.isEmpty(salt)) salt = "";
        userLogin.set("currentPassword", useEncryption ? HashCrypt.getDigestHash(salt + password, LoginServices.getHashType()) : password, false);
        userLogin.set("requirePasswordChange", "N");
        delegator.store(userLogin);
    }

    /**
     * Deletes the GnUserLoginPasswordReset row
     *
     * @param uuid
     * @throws GenericEntityException
     */
    protected void deleteUUID(String uuid) throws GenericEntityException {
        GenericPK pk = GenericPK.create(delegator, delegator.getModelEntity("GnUserLoginPasswordReset"), uuid);
        delegator.removeByPrimaryKey(pk);
    }

    /**
     * Creates the password reset uuid
     *
     * @param userLoginId
     * @return
     * @throws GenericServiceException
     */
    protected Map<String, Object> createPasswordResetUUID(String userLoginId) throws GenericEntityException, GenericServiceException {
        String uuid = UUID.randomUUID().toString() + "@" + SysUtil.getInstanceId();
        String email = "";
        GenericValue userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        if (UtilValidate.isEmpty(userLogin)) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "UserLogin[" + userLoginId + "] not found.");
        }
        delegator.create("GnUserLoginPasswordReset", UtilMisc.toMap("uuid", uuid, "userLoginId", userLoginId));

        if (UtilValidate.isNotEmpty(userLogin.getString("partyId"))) {
            Map<String, Object> srvContext = dispatcher.getDispatchContext().makeValidContext("gnGetPartyContact", "IN", context);
            srvContext.put("partyId", userLogin.getString("partyId"));
            Map<String, Object> srvResult = dispatcher.runSync("gnGetPartyContact", srvContext);
            if (UtilValidate.isNotEmpty(srvContext.get("contact"))) {
                Map<String, Object> contact = UtilGenerics.checkMap(srvContext.get("contact"));
                email = (String) contact.get("emailAddress");
            }
        }
        return UtilMisc.toMap("uuid", (Object) uuid, "email", email);
    }

    protected Map<String, Object> verifyPasswordResetUUID(String uuid) throws GenericEntityException, GnServiceException {
        GenericValue gv = delegator.findOne("GnUserLoginPasswordReset", UtilMisc.toMap("uuid", uuid), false);
        if (UtilValidate.isEmpty(gv)) {
            throw new GnServiceException(OfbizErrors.EXPIRED_OPERATION, "GnUserLoginPasswordReset[" + uuid + "] not found.");
        }
        if (!UtilDateTime.nowTimestamp().before(new Timestamp(gv.getTimestamp("createdStamp").getTime() + PASSWORD_RESET_UUID_VALIDITY))) {
            throw new GnServiceException(OfbizErrors.EXPIRED_OPERATION, "The reset operation is expired");
        }
        String userLoginId = gv.getString("userLoginId");
        String pc = getPasswordConstraints(userLoginId);
        //  getPasswordConstraints(record);

        //dv.addAlias("UL",);

       /* List<GenericValue> ret = delegator.findList("PartyRelationship", EntityConditionUtil.makeRelationshipCondition(userLoginPartyId, "GN_USER", null, "GN_COMPANY", RelationshipTypeOfbiz.EMPLOYMENT.name(), true), null, null, null, true);
        if (ret.size() == 1) {
            return findPartyNodeById((String) ret.get(0).get("partyIdTo"), true);
        }*/
        Map<String, Object> response = FastMap.newInstance();
        response.put("passwordConstraints", pc);
        return response;
    }

    protected String getPasswordConstraints(String userLoginId) throws GenericEntityException, GnServiceException {
        //   String userLoginId = record.getString("userLoginId");
        DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("UL", "UserLogin");
        dv.addMemberEntity("PR", "PartyRelationship");
        dv.addMemberEntity("PA", "PartyAttribute");

        dv.addAlias("UL", "userLoginId");
        dv.addAlias("UL", "userLoginPartyId", "partyId", null, null, null, null);
        dv.addAliasAll("PR", null);
        dv.addAliasAll("PA", null);

        dv.addViewLink("UL", "PR", false, UtilMisc.toList(new ModelKeyMap("partyId", "partyIdFrom")));
        dv.addViewLink("PR", "PA", false, UtilMisc.toList(new ModelKeyMap("partyIdTo", "partyId")));
        List<EntityCondition> conds = FastList.newInstance();
        conds.add(EntityConditionUtil.makeRelationshipCondition(null, PartyRoleOfbiz.GN_USER, null, PartyRoleOfbiz.GN_COMPANY, RelationshipTypeOfbiz.EMPLOYMENT, true));
        conds.add(EntityCondition.makeCondition("userLoginId", userLoginId));
        conds.add(EntityCondition.makeCondition("attrName", CompanyAttributeOfbiz.PASSWORD_CONSTRAINTS.name()));

        EntityListIterator it = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition(conds), null, UtilMisc.toList("attrValue", "attrName", "partyId"), null, null);
        List<GenericValue> result = it.getCompleteList();
        it.close();
        if (result.size() == 1) {
            return result.get(0).getString("attrValue");
        } else if (result.size() > 1) {
            throw new GnServiceException(OfbizErrors.GENERIC, "found more than one attribute with attrName[" + CompanyAttributeOfbiz.PASSWORD_CONSTRAINTS.name() + "]");
        } else {
            return null;
        }
    }

    public Integer getPasswordExpirationDays(String userLoginId) throws GenericEntityException, GnServiceException {
        String pc = getPasswordConstraints(userLoginId);
        Integer ret = -1;
        if (UtilValidate.isNotEmpty(pc)) {
            int interval;
            PasswordConfiguration config = new PasswordConfiguration(pc);
            List<GenericValue> result = delegator.findByAnd("UserLoginPasswordHistory", UtilMisc.toMap("userLoginId", userLoginId), UtilMisc.toList("createdStamp DESC"));
            if (result.size() == 0) {
                GenericValue gv = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", userLoginId));
                Timestamp ts = gv.getTimestamp("createdStamp");
                interval = UtilDateTime.getIntervalInDays(UtilDateTime.getDayStart(ts), UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()));
                Debug.log(String.format("Days from password setting: %s", interval));
            } else {
                Timestamp ts = result.get(0).getTimestamp("createdStamp");
                interval = UtilDateTime.getIntervalInDays(UtilDateTime.getDayStart(ts), UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()));
                Debug.log(String.format("Days from last password changing: %s", interval));
            }
            Debug.log(String.format("Days password validity: %s", config.getExpirationDays()));
            ret = config.getExpirationDays() - interval;
            if (ret < 0) ret = 0;
        }
        //  ret = 0;//TODO only for test
        // ret = 5;//TODO only for test
        return ret;
    }

    protected Map<String, Object> sendCommunicationEventToUser(String userLoginId, String eventTypeId, Map<String, Object> attributes) throws GenericServiceException {
        Map<String, Object> srvRequest = dispatcher.getDispatchContext().makeValidContext("gnSendCommunicationEventToUser", "IN", context);
        srvRequest.put("userLoginId", userLoginId);
        srvRequest.put("eventTypeId", eventTypeId);
        srvRequest.put("communicationEventTypeId", CommunicationEventTypeOfbiz.EMAIL_COMMUNICATION.name());
        srvRequest.put("attributes", attributes);
        Debug.log("Sending message for event[" + eventTypeId + "] to user[" + userLoginId + "] with attributes " + PrettyPrintingMap.toString(attributes));
        return dispatcher.runSync("gnSendCommunicationEventToUser", srvRequest);
    }

    private void checkOldPassword(String currentPassword, String newPassword, String salt) throws GnServiceException {
        if (UtilValidate.isEmpty(salt)) salt = "";
        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));

        String encodedPassword = useEncryption ? HashCrypt.getDigestHash(salt + currentPassword, LoginServices.getHashType()) : currentPassword;
        String encodedPasswordOldFunnyHexEncode = useEncryption ? HashCrypt.getDigestHashOldFunnyHexEncode(currentPassword, LoginServices.getHashType()) : currentPassword;
        String encodedPasswordUsingDbHashType = encodedPassword;

        String oldPassword = userLogin.getString("currentPassword");
        if (useEncryption && oldPassword != null && oldPassword.startsWith("{")) {
            // get encode according to the type in the database
            String dbHashType = HashCrypt.getHashTypeFromPrefix(oldPassword);
            if (dbHashType != null) {
                encodedPasswordUsingDbHashType = HashCrypt.getDigestHash(salt + currentPassword, dbHashType);
            }
        }

        // if the password.accept.encrypted.and.plain property in security is set to true allow plain or encrypted passwords
        // if this is a system account don't bother checking the passwords
        boolean passwordMatches = (oldPassword != null &&
                (HashCrypt.removeHashTypePrefix(encodedPassword).equals(HashCrypt.removeHashTypePrefix(oldPassword))
                        || HashCrypt.removeHashTypePrefix(encodedPasswordOldFunnyHexEncode).equals(HashCrypt.removeHashTypePrefix(oldPassword))
                        || HashCrypt.removeHashTypePrefix(encodedPasswordUsingDbHashType).equals(HashCrypt.removeHashTypePrefix(oldPassword))
                        || ("true".equals(UtilProperties.getPropertyValue("security.properties", "password.accept.encrypted.and.plain"))
                        && currentPassword.equals(oldPassword))));

        if ((currentPassword == null) || (userLogin != null && currentPassword != null && !passwordMatches)) {
            throw new GnServiceException(OfbizErrors.OLD_PASSWORD_WRONG, "The old password is wrong. Retype it.");
        }
        if (currentPassword.equals(newPassword) || encodedPassword.equals(newPassword)) {
            throw new GnServiceException(OfbizErrors.NEW_PASSWORD_EQUAL_OLD, "The new password must be different from the old one.");
        }
    }

    /**
     * Checks if the userLogin has GN_LOG_SRC_WEB_UI login source.
     * Throws an exception if not.
     *
     * @param userLoginId
     * @return
     * @throws GenericEntityException
     * @throws GnServiceException
     */
    public boolean hasWebLoginSource(String userLoginId) throws GenericEntityException, GnServiceException {
        if (UtilValidate.isEmpty(userLoginId))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "userLoginId field cannot be empty.");
        GenericValue userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        if (UtilValidate.isEmpty(userLogin))
            throw new GnServiceException(OfbizErrors.USERNAME_NOT_EXISTS, "The UserLogin [" + userLoginId + "] does not exist");
        boolean isWebLogin = gnLoginHelper.checkLoginSourceId(userLoginId, LoginSourceOfbiz.GN_LOG_SRC_WEB_UI.toString());
        if (!isWebLogin) {
            throw new GnServiceException(OfbizErrors.CANNOT_LOGIN_FROM_WEB, "The UserLogin [" + userLoginId + "] cannot log from WEB interface");
        }
        return true;
    }

    /**
     * @param userLoginId
     * @return
     */
    public boolean hasGnAdminPermission(String userLoginId) throws GenericEntityException, GnServiceException {
        List<GenericValue> gnContextUsers = delegator.findByAnd("GnContextUser", "userLoginId", userLoginId);
        for (GenericValue gv : gnContextUsers) {
            List<GenericValue> permissions = delegator.findByAnd("GnPartySecurityGroupPermission",
                    "partyId", gv.get("contextPartyId"), "permissionId", PermissionsOfbiz.GN_ADMIN.name());
            if (permissions.size() > 0)
                throw new GnServiceException(OfbizErrors.NOT_ENOUGH_PERMISSIONS, "The UserLogin [" + userLoginId + "] does not exist");
        }
        return false;
        /*DynamicViewEntity dv = new DynamicViewEntity();
        dv.addMemberEntity("CTX_USER", "GnContextUser");
        dv.addMemberEntity("PSGP", "GnPartySecurityGroupPermission");
        dv.addViewLink("CTX_USER", "PSGP", false, ModelKeyMap.makeKeyMapList("contextPartyId", "partyId"));
        dv.addAliasAll("CTX_USER", "CTX_USER_");
        dv.addAliasAll("PSGP", "PSGP_");
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        conds.add(EntityCondition.makeCondition("CTX_USER_" + "userLoginId", userLoginId));
        conds.add(EntityCondition.makeCondition("PSGP_" + "permissionId", PermissionsOfbiz.GN_ADMIN.name()));
        EntityListIterator listIterator = delegator.findListIteratorByCondition(dv, EntityCondition.makeCondition(conds), null, null, null, null);
        List<GenericValue> completeList = listIterator.getCompleteList();
        return completeList.size() == 0;*/
//        GnSecurity gnSecurity = new GnSecurity(dctx.getDelegator());
//        return gnSecurity.hasPermission(PermissionsOfbiz.GN_ADMIN, userLogin);
    }


    /**
     * Calls OfBiz reenableUserLogin service to enable an existing userLoginId
     *
     * @param userLoginId
     * @return true if userLogin was disabled previously; false otherwise.
     */
    protected Map<String, Object> reenableUserLogin(String userLoginId) throws GenericServiceException, GenericEntityException {
        GenericValue userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
        if (UtilValidate.isEmpty(userLogin)) {
            throw new GnServiceException(OfbizErrors.ENTITY_EXCEPTION, "UserLogin[" + userLoginId + "] not found.");
        }
        Map<String, Object> response = FastMap.newInstance();
        if ("Y".equalsIgnoreCase((String) userLogin.get("enabled"))) {
            response.put("previousDisabled", "N");
        } else {
            response.put("previousDisabled", "Y");
            userLogin.put("enabled", "Y");
            delegator.store(userLogin);
        }
        return response;
    }

}
