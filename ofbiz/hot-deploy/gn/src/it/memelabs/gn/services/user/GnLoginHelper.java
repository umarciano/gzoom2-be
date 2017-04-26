package it.memelabs.gn.services.user;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.services.login.LoginSourceOfbiz;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 17/05/13
 *
 * @author Andrea Fossi
 */
public class GnLoginHelper extends AbstractServiceHelper {
    private static final String module = GnContextHelper.class.getName();

    public GnLoginHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    /**
     * @param userLoginId
     * @param originId    {@link it.memelabs.gn.services.authorization.AuthorizationOriginOfbiz}
     * @throws GenericEntityException
     */
    public void updateUserLoginOriginId(String userLoginId, String originId) throws GenericEntityException {
        GenericValue ul = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", (Object) userLoginId), false);
        ul.set("originId", originId);
        delegator.store(ul);
        Debug.log("Updated originId[" + originId + "] to userLogin[" + userLoginId + "]", module);

    }

    /**
     * @param userLoginId
     * @param loginSourceId {@link it.memelabs.gn.services.login.LoginSourceOfbiz}
     * @throws GenericEntityException
     */
    public void addUserLoginLoginSourceId(String userLoginId, String loginSourceId) throws GenericEntityException, GnServiceException {
        if (LoginSourceOfbiz.valueOf(loginSourceId) == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "invalid loginSourceId");
        GenericValue ul = delegator.findOne("GnUserLoginSource", false, "userLoginId", userLoginId, "enumId", loginSourceId);
        if (UtilValidate.isEmpty(ul)) {
            GenericValue gv = delegator.makeValue("GnUserLoginSource");
            gv.set("userLoginId", userLoginId);
            gv.set("enumId", loginSourceId);
            delegator.create(gv);
            Debug.log("Added loginSourceId[" + loginSourceId + "] to userLogin[" + userLoginId + "]", module);
        } else
            Debug.log("Already exist loginSourceId[" + loginSourceId + "] associated userLogin[" + userLoginId + "]", module);


    }

    /**
     * @param userLoginId
     * @param loginSourceId {@link it.memelabs.gn.services.login.LoginSourceOfbiz}
     * @throws GenericEntityException
     */
    public void removeUserLoginLoginSourceId(String userLoginId, String loginSourceId) throws GenericEntityException, GnServiceException {
        if (LoginSourceOfbiz.valueOf(loginSourceId) == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "invalid loginSourceId");
        GenericValue ul = delegator.findOne("GnUserLoginSource", false, "userLoginId", userLoginId, "enumId", loginSourceId);
        if (UtilValidate.isNotEmpty(ul)) {
            delegator.removeValue(ul);
            Debug.log("Deleted loginSourceId[" + loginSourceId + "] to userLogin[" + userLoginId + "]", module);
        } else
            Debug.log("Not exist loginSourceId[" + loginSourceId + "] associated userLogin[" + userLoginId + "]", module);


    }

    /**
     * @param userLoginId
     * @param loginSourceId {@link it.memelabs.gn.services.login.LoginSourceOfbiz}
     * @throws GenericEntityException
     */
    public boolean checkLoginSourceId(String userLoginId, String loginSourceId) throws GenericEntityException, GnServiceException {
        if (LoginSourceOfbiz.valueOf(loginSourceId) == null)
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "invalid loginSourceId");
        GenericValue ul = delegator.findOne("GnUserLoginSource", false, "userLoginId", userLoginId, "enumId", loginSourceId);
        return UtilValidate.isNotEmpty(ul);

    }

    /**
     * @param userLoginId
     * @throws GenericEntityException
     */
    public List<String> findLoginSourceId(String userLoginId) throws GenericEntityException, GnServiceException {
        List<GenericValue> loginSources = delegator.findByAnd("GnUserLoginSource", "userLoginId", userLoginId);
        List<String> loginSourceIds = new ArrayList<String>(loginSources.size());
        for (GenericValue gv : loginSources) {
            loginSourceIds.add(gv.getString("enumId"));
        }
        return loginSourceIds;

    }

    public boolean hasPreviouslyLogged(String userLoginId) throws GenericEntityException {
        List<GenericValue> history = delegator.findList("UserLoginHistory", EntityCondition.makeCondition("userLoginId", userLoginId), UtilMisc.toSet("userLoginId"), null, null, false);
        return UtilValidate.isNotEmpty(history);
    }

    public void updateUserLoginDeviceUniqueId(String userLoginId, String deviceId,
                                              String deviceDescription, String appId, String appVersion,
                                              String isActive) throws GenericEntityException {
        GenericValue ul = delegator.findOne("GnUserLoginDeviceUniqueId", UtilMisc.toMap("userLoginId", (Object) userLoginId, "deviceId", deviceId), false);
        ul.set("isActive", isActive);
        if (UtilValidate.isNotEmpty(deviceDescription)) ul.set("deviceDescription", deviceDescription);
        if (UtilValidate.isNotEmpty(appId)) ul.set("appId", appId);
        if (UtilValidate.isNotEmpty(appVersion)) ul.set("appVersion", appVersion);
        delegator.store(ul);
        Debug.log(String.format("Updated GnUserLoginDeviceUniqueId userLoginId[%s],deviceId[%s]," +
                "deviceDescription[%s],appId[%s],appVersion[%s]," +
                "isActive[%s]", userLoginId, deviceId, deviceDescription, appId, appVersion, isActive), module);

    }

    public void createUserLoginDeviceUniqueId(String userLoginId, String deviceId, String isActive, String deviceDescription, String appId, String appVersion) throws GenericEntityException {
        GenericValue ul = delegator.makeValue("GnUserLoginDeviceUniqueId");
        ul.set("userLoginId", userLoginId);
        ul.set("deviceId", deviceId);
        ul.set("isActive", isActive);
        ul.set("deviceDescription", deviceDescription);
        ul.set("appId", appId);
        ul.set("appVersion", appVersion);
        delegator.create(ul);
        Debug.log(String.format("Created  GnUserLoginDeviceUniqueId userLoginId[%s],deviceId[%s],isActive[%s]", userLoginId, deviceId, isActive), module);

    }

    public void addActiveUserLoginDeviceUniqueId(String userLoginId, String deviceId, String deviceDescription, String appId, String appVersion) throws GenericEntityException {
        List<GenericValue> devices = findUserLoginDeviceUniqueIds(userLoginId, null, "Y");
        for (GenericValue device : devices) {
            device.set("isActive", "N");
            delegator.store(device);
        }
        createUserLoginDeviceUniqueId(userLoginId, deviceId, "Y", deviceDescription, appId, appVersion);
    }

    public List<GenericValue> findUserLoginDeviceUniqueIds(String userLoginId, String deviceId, String isActive) throws GenericEntityException {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        if (UtilValidate.isNotEmpty(userLoginId)) {
            conds.add(EntityCondition.makeCondition("userLoginId", userLoginId));
        }
        if (UtilValidate.isNotEmpty(deviceId)) {
            conds.add(EntityCondition.makeCondition("deviceId", deviceId));
        }
        if (UtilValidate.isNotEmpty(isActive)) {
            conds.add(EntityCondition.makeCondition("isActive", isActive));
        }
        EntityListIterator ret = delegator.find("GnUserLoginDeviceUniqueId", EntityCondition.makeCondition(conds), null, null, null, null);
        List<GenericValue> completeList = ret.getCompleteList();
        ret.close();
        return completeList;


    }
}
