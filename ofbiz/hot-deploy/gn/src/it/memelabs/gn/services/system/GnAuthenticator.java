package it.memelabs.gn.services.system;

import it.memelabs.gn.security.PasswordConfiguration;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.apache.commons.lang.RandomStringUtils;
import org.ofbiz.base.crypto.HashCrypt;
import org.ofbiz.base.util.*;
import org.ofbiz.common.authentication.api.Authenticator;
import org.ofbiz.common.authentication.api.AuthenticatorException;
import org.ofbiz.common.login.LoginServices;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 08/04/2014
 *
 * @author Andrea Fossi
 */
public class GnAuthenticator implements Authenticator {
    private static final String module = GnAuthenticator.class.getName();
    protected Delegator delegator;
    protected LocalDispatcher dispatcher;
    protected float weight = 1;


    /**
     * Method called when authenticator is first initialized (the delegator
     * object can be obtained from the LocalDispatcher)
     *
     * @param dispatcher The ServiceDispatcher to use for this Authenticator
     */
    public void initialize(LocalDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.delegator = dispatcher.getDelegator();
        Debug.logInfo(this.getClass().getName() + " Authenticator initialized", module);
    }

    /**
     * Method to authenticate a user
     *
     * @param username      User's username
     * @param password      User's password
     * @param isServiceAuth true if authentication is for a service call
     * @return true if the user is authenticated
     * @throws org.ofbiz.common.authentication.api.AuthenticatorException when a fatal error occurs during authentication
     */
    public boolean authenticate(String username, String password, boolean isServiceAuth) throws AuthenticatorException {
        try {
            GenericValue userLogin = delegator.findOne("UserLogin", isServiceAuth, "userLoginId", username);
            if (userLogin != null) {
                boolean authenticated = authenticate(password, userLogin);
                Long successiveFailedLogins = userLogin.getLong("successiveFailedLogins");
                if (authenticated && successiveFailedLogins != null && successiveFailedLogins > 0) {
                    Long maxFailedLogins = getMaxFailedLogins(username);
                    //return true if maxFailedLogins is not defined or if successiveFailedLogins is more than maxFailedLogins
                    if (maxFailedLogins >= 0 && (successiveFailedLogins > maxFailedLogins)) {
                        authenticated = false;
                        Debug.log(String.format("UserLogin[%s] successiveFailedLogins[%s] is more than company maxFailedLogins[%s]", username, successiveFailedLogins, maxFailedLogins));
                    }
                }
                return authenticated;
            } else {
                return false;
            }
            //Debug.logInfo(this.getClass().getName() + " Authenticator authenticate() -- returning false", module);
            // return false;
        } catch (Exception e) {
            throw new AuthenticatorException(e);
        }
    }

    private Long getMaxFailedLogins(String username) throws GenericServiceException {
        int ret = -1;
        Map<String, Object> result = dispatcher.runSync("gnInternalGetPasswordConstraints", UtilMisc.toMap("userLoginId", username));
        String pc = (String) result.get("passwordConstraints");
        if (UtilValidate.isNotEmpty(pc)) {
            PasswordConfiguration config = new PasswordConfiguration(pc);
            if (config.isValid() && config.getMaxFailedLogins() > 0) {
                ret = config.getMaxFailedLogins();
            }
        }
        return (long) ret;
    }

    private static boolean authenticate(String password, GenericValue userLogin) {
        String salt = userLogin.getString("salt");
        String oldPassword = userLogin.getString("currentPassword");
        if (!UtilValidate.isEmpty(salt)) password = salt + password;
        String encodedPassword = HashCrypt.getDigestHash(password, HashCrypt.getHashTypeFromPrefix(oldPassword));
        boolean passwordMatches = HashCrypt.removeHashTypePrefix(encodedPassword).equals(HashCrypt.removeHashTypePrefix(oldPassword));
        return passwordMatches;
    }


    private static GenericValue updatePassword(String username, String password, String newPassword, Delegator delegator) throws AuthenticatorException {
        try {
            GenericValue userLogin = delegator.findOne("UserLogin", false, "userLoginId", username);
            if (userLogin != null) {
                if (authenticate(password, userLogin)) {
                    String salt = userLogin.getString("salt");
                    if (UtilValidate.isEmpty(salt)) {
                        salt = RandomStringUtils.randomAlphanumeric(16);
                    }
                    String oldPassword = userLogin.getString("currentPassword");
                    String encodedNewPassword = HashCrypt.getDigestHash(salt + newPassword, HashCrypt.getHashTypeFromPrefix(oldPassword));
                    userLogin.put("currentPassword", encodedNewPassword);
                    userLogin.put("salt", salt);
                    delegator.store(userLogin, true);
                    Debug.log("Password salted for user[" + userLogin.get("userLoginId") + "]");
                    return userLogin;
                }
            }
        } catch (Exception e) {
            throw new AuthenticatorException(e);
        }
        return null;
    }


    /**
     * Logs a user out
     *
     * @param username User's username
     * @throws org.ofbiz.common.authentication.api.AuthenticatorException when logout fails
     */
    public void logout(String username) throws AuthenticatorException {
        Debug.logInfo(this.getClass().getName() + " Authenticator logout()", module);
    }

    /**
     * Reads user information and syncs it to OFBiz (i.e. UserLogin, Person, etc)
     *
     * @param username User's username
     * @throws org.ofbiz.common.authentication.api.AuthenticatorException user synchronization fails
     */
    public void syncUser(String username) throws AuthenticatorException {
        Debug.logInfo(this.getClass().getName() + " Authenticator syncUser()", module);
        // no user info to sync
    }

    /**
     * Updates a user's password
     *
     * @param username    User's username
     * @param password    User's current password
     * @param newPassword User's new password
     * @throws org.ofbiz.common.authentication.api.AuthenticatorException when update password fails
     */
    public void updatePassword(String username, String password, String newPassword) throws AuthenticatorException {
        Debug.logInfo(this.getClass().getName() + " Authenticator updatePassword()", module);
    }

    /**
     * Weight of this authenticator (lower weights are run first)
     *
     * @return the weight of this Authenticator
     */
    public float getWeight() {
        return 1;
    }

    /**
     * Is the user synchronized back to OFBiz
     *
     * @return true if the user record is copied to the OFB database
     */
    public boolean isUserSynchronized() {
        Debug.logInfo(this.getClass().getName() + " Authenticator isUserSynchronized()", module);
        return true;
    }

    /**
     * Is this expected to be the only authenticator, if so errors will be thrown when users cannot be found
     *
     * @return true if this is expected to be the only Authenticator
     */
    public boolean isSingleAuthenticator() {
        Debug.logInfo(this.getClass().getName() + " Authenticator isSingleAuthenticator()", module);
        return false;
    }

    /**
     * Flag to test if this Authenticator is enabled
     *
     * @return true if the Authenticator is enabled
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * @param userLogin
     * @param password
     * @param newPassword
     * @param newPasswordVerify
     * @param delegator
     * @return
     * @throws GnServiceException
     */
    public static GenericValue updateSaltedPassword(GenericValue userLogin,
                                                    String password, String newPassword, String newPasswordVerify,
                                                    Delegator delegator) throws GnServiceException {
        return updateSaltedPassword(userLogin, password, newPassword, newPasswordVerify, delegator, true);
    }

    /**
     * @param userLogin
     * @param password
     * @param newPassword
     * @param newPasswordVerify
     * @param delegator
     * @param saveHistory       only used when password is updated to add salt
     * @return
     * @throws GnServiceException
     */
    public static GenericValue updateSaltedPassword(GenericValue userLogin,
                                                    String password, String newPassword, String newPasswordVerify,
                                                    Delegator delegator, boolean saveHistory) throws GnServiceException {
        try {
            if (!newPassword.equals(newPasswordVerify))
                throw new GnServiceException(OfbizErrors.PASSWORD_NOT_MATCH_VERIFY);
            String userLoginId = userLogin.getString("userLoginId");
            String activeContextId = userLogin.getString("activeContextId");
            GenericValue ret = updatePassword(userLoginId, password, newPassword, delegator);
            ret.put("activeContextId", activeContextId);
            if (saveHistory) createUserLoginPasswordHistory(delegator, userLoginId, password);
            return ret;
        } catch (AuthenticatorException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Error on updating password", e);
        } catch (GenericEntityException e) {
            throw new GnServiceException(OfbizErrors.GENERIC, "Error on updating password", e);
        }

    }

    /**
     * @param delegator
     * @param userLoginId
     * @param _currentPassword
     * @throws GenericEntityException
     */
    private static void createUserLoginPasswordHistory(Delegator delegator, String userLoginId, String _currentPassword) throws GenericEntityException {
        GenericValue userLogin = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
        String salt = userLogin.getString("salt");

        String currentPassword = (UtilValidate.isNotEmpty(salt)) ? salt + _currentPassword : _currentPassword;

        int passwordChangeHistoryLimit = 0;
        try {
            passwordChangeHistoryLimit = Integer.parseInt(UtilProperties.getPropertyValue("security.properties", "password.change.history.limit", "0"));
        } catch (NumberFormatException nfe) {
            //No valid value is found so don't bother to save any password history
            passwordChangeHistoryLimit = 0;
        }
        if (passwordChangeHistoryLimit == 0 || passwordChangeHistoryLimit < 0) {
            // Not saving password history, so return from here.
            return;
        }

        EntityFindOptions efo = new EntityFindOptions();
        efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);
        EntityListIterator eli = delegator.find("UserLoginPasswordHistory", EntityCondition.makeConditionMap("userLoginId", userLoginId), null, null, UtilMisc.toList("-fromDate"), efo);
        Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
        GenericValue pwdHist;
        if ((pwdHist = eli.next()) != null) {
            // updating password so set end date on previous password in history
            pwdHist.set("thruDate", nowTimestamp);
            pwdHist.store();
            // check if we have hit the limit on number of password changes to be saved. If we did then delete the oldest password from history.
            eli.last();
            int rowIndex = eli.currentIndex();
            if (rowIndex == passwordChangeHistoryLimit) {
                eli.afterLast();
                pwdHist = eli.previous();
                pwdHist.remove();
            }
        }
        eli.close();

        // save this password in history
        GenericValue userLoginPwdHistToCreate = delegator.makeValue("UserLoginPasswordHistory", UtilMisc.toMap("userLoginId", userLoginId, "fromDate", nowTimestamp));
        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));
        userLoginPwdHistToCreate.set("currentPassword", useEncryption ? HashCrypt.getDigestHash(currentPassword, LoginServices.getHashType()) : currentPassword);
        userLoginPwdHistToCreate.create();
    }
}
