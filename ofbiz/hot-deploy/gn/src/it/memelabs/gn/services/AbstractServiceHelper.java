package it.memelabs.gn.services;

import it.memelabs.gn.util.DispatcherDecorator;
import it.memelabs.gn.util.GnDispatcher;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import java.util.Locale;
import java.util.Map;

/**
 * 04/03/13
 *
 * @author Andrea Fossi
 */
public abstract class AbstractServiceHelper {
    protected DispatchContext dctx;
    protected Delegator delegator;
    protected LocalDispatcher dispatcher;
    protected Map<String, ? extends Object> context;
    protected Locale locale;
    protected GenericValue userLogin;


    /**
     * @param dctx    OFBiz DispatchContext
     * @param context Service Context
     */
    public AbstractServiceHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        this.dctx = dctx;
        this.delegator = dctx.getDelegator();
        this.dispatcher = new GnDispatcher(new DispatcherDecorator(dctx.getDispatcher()));
        this.context = context;
        this.userLogin = (GenericValue) context.get("userLogin");
        this.locale = (Locale) context.get("locale");
    }

    public DispatchContext getDctx() {
        return dctx;
    }

    public void setDctx(DispatchContext dctx) {
        this.dctx = dctx;
    }

    public Delegator getDelegator() {
        return delegator;
    }

    public void setDelegator(Delegator delegator) {
        this.delegator = delegator;
    }

    public LocalDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(LocalDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Map<String, ? extends Object> getContext() {
        return context;
    }

    public void setContext(Map<String, ? extends Object> context) {
        this.context = context;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public GenericValue getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(GenericValue userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     *
     */

    protected Map<String, Object> getCurrentContext() throws GenericServiceException {
        String contextId = userLogin.getString("activeContextId");
        Map<String, Object> gnContext = dctx.getDispatcher().runSync("gnFindContextById", UtilMisc.toMap("userLogin", userLogin, "partyId", contextId));
        return gnContext;
    }

    /**
     * @return
     * @throws GenericServiceException
     */
    protected String getCurrentContextId()  {
        String contextId = userLogin.getString("activeContextId");
        return contextId;
    }
}
