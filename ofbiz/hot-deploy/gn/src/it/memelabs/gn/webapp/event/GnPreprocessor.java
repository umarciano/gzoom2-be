package it.memelabs.gn.webapp.event;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.security.Security;
import org.ofbiz.security.SecurityConfigurationException;
import org.ofbiz.security.SecurityFactory;
import org.ofbiz.security.authz.Authorization;
import org.ofbiz.security.authz.AuthorizationFactory;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.webapp.control.ContextFilter;
import org.ofbiz.webapp.control.LoginWorker;
import org.ofbiz.webapp.control.RequestHandler;
import org.ofbiz.webapp.stats.VisitHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 13/12/12
 *
 * @author Andrea Fossi
 */
public class GnPreprocessor extends LoginWorker {
    public final static String module = GnPreprocessor.class.getName();

    public static String checkExternalLoginKey(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String externalKey = request.getHeader(LoginWorker.EXTERNAL_LOGIN_KEY_ATTR);
        if (UtilValidate.isEmpty(externalKey))
            externalKey = request.getParameter(LoginWorker.EXTERNAL_LOGIN_KEY_ATTR);
        /* if (UtilValidate.isEmpty(externalKey)) {
            externalKey = (String) request.getSession().getAttribute(LoginWorker.EXTERNAL_LOGIN_KEY_ATTR);
            Debug.log("No session key passed.");
        }*/

        if (externalKey == null)
            return "success";

        int indexOf = externalKey.indexOf("_");
        if (indexOf > 1) externalKey = externalKey.substring(indexOf + 1);


        GenericValue userLogin = LoginWorker.externalLoginKeys.get(externalKey);
        if (userLogin != null) {
            //to check it's the right tenant
            //in case username and password are the same in different tenants
            LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
            Delegator delegator = (Delegator) request.getAttribute("delegator");
            String oldDelegatorName = delegator.getDelegatorName();
            ServletContext servletContext = session.getServletContext();
            if (!oldDelegatorName.equals(userLogin.getDelegator().getDelegatorName())) {
                delegator = DelegatorFactory.getDelegator(userLogin.getDelegator().getDelegatorName());
                dispatcher = ContextFilter.makeWebappDispatcher(servletContext, delegator);
                setWebContextObjects(request, response, delegator, dispatcher);
            }
            // found userLogin, do the external login...

            // if the user is already logged in and the login is different, logout the other user
            GenericValue currentUserLogin = (GenericValue) session.getAttribute("userLogin");
            if (currentUserLogin != null) {
                if (currentUserLogin.getString("userLoginId").equals(userLogin.getString("userLoginId"))) {
                    // is the same user, just carry on...
                    return "success";
                }

                // logout the current user and login the new user...
                logout(request, response);
                // ignore the return value; even if the operation failed we want to set the new UserLogin
            }

            doBasicLogin(userLogin, request);
            request.setAttribute("userLogin", request.getSession().getAttribute("userLogin"));
        } else {
            Debug.logWarning("Could not find userLogin for external login key: " + externalKey, module);
        }

        return "success";
    }

    public static String _checkLogout(HttpServletRequest request, HttpServletResponse response) {
        //HttpSession session = request.getSession();
        //Object auto_login_logout_ = request.getAttribute("_AUTO_LOGOUT_");
        //if (auto_login_logout_ != null && (Boolean) auto_login_logout_) {


        /* String externalKey = request.getHeader(LoginWorker.EXTERNAL_LOGIN_KEY_ATTR);
        if (UtilValidate.isNotEmpty(externalKey)) {
            int indexOf = externalKey.indexOf("_");
            if (indexOf > 1) externalKey = externalKey.substring(indexOf + 1);
            GenericValue userLogin = LoginWorker.externalLoginKeys.get(externalKey);
            if (userLogin != null && "Y".equals(userLogin.get("hasLoggedOut"))) {
                Debug.log("Executing logout", module);
                LoginWorker.externalLoginKeys.remove(externalKey);
                return logout(request, response);
            }
        }*/
        HttpSession session = request.getSession();
        GenericValue currentUserLogin = (GenericValue) session.getAttribute("userLogin");
        if (currentUserLogin != null && "Y".equals(currentUserLogin.get("hasLoggedOut"))) {
            Debug.log("Executing logout", module);
            cleanupExternalLoginKey(session);
            session.setAttribute("userLogin", null);
            // session.invalidate();
            return logout(request, response);
        }

        return "success";
    }

    public static String logout(HttpServletRequest request, HttpServletResponse response) {
        // run the before-logout events
        RequestHandler rh = RequestHandler.getRequestHandler(request.getSession().getServletContext());
        rh.runBeforeLogoutEvents(request, response);

        // invalidate the security group list cache
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        doBasicLogout(userLogin, request, response);

        if (request.getAttribute("_AUTO_LOGIN_LOGOUT_") == null) {
            return autoLoginCheck(request, response);
        }
        return "success";
    }

    public static String checkLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        GenericValue currentUserLogin = (GenericValue) session.getAttribute("userLogin");
        if (currentUserLogin != null && "Y".equals(currentUserLogin.get("hasLoggedOut"))) {
            Debug.log("Executing logout", module);
            cleanupExternalLoginKey(session);
            session.setAttribute("userLogin", null);
            // session.invalidate();
            //return logout(request, response);
        }


        return "success";
    }


    private static void setWebContextObjects(HttpServletRequest request, HttpServletResponse response, Delegator delegator, LocalDispatcher dispatcher) {
        HttpSession session = request.getSession();

        // NOTE: we do NOT want to set this in the servletContet, only in the request and session
        session.setAttribute("delegatorName", delegator.getDelegatorName());

        request.setAttribute("delegator", delegator);
        session.setAttribute("delegator", delegator);

        request.setAttribute("dispatcher", dispatcher);
        session.setAttribute("dispatcher", dispatcher);

        // we also need to setup the security and authz objects since they are dependent on the delegator
        try {
            Security security = SecurityFactory.getInstance(delegator);
            request.setAttribute("security", security);
            session.setAttribute("security", security);
        } catch (SecurityConfigurationException e) {
            Debug.logError(e, module);
        }

        try {
            Authorization authz = AuthorizationFactory.getInstance(delegator);
            request.setAttribute("authz", authz);
            session.setAttribute("authz", authz);
        } catch (SecurityConfigurationException e) {
            Debug.logError(e, module);
        }

        // get rid of the visit info since it was pointing to the previous database, and get a new one
        session.removeAttribute("visitor");
        session.removeAttribute("visit");
        VisitHandler.getVisitor(request, response);
        VisitHandler.getVisit(session);
    }

}
