package it.memelabs.gn.webapp.event;

import it.memelabs.gn.services.event.EventMessageUtil;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.service.GenericDispatcher;
import org.ofbiz.webapp.event.EventHandlerException;

import javax.servlet.ServletContext;

/**
 * 30/08/13
 *
 * @author Andrea Fossi
 */
public class XmlRpcEventHandlerTestDelegator extends XmlRpcEventHandler {
    protected Delegator oldDelegator;

    @Override
    public void init(ServletContext context) throws EventHandlerException {
        String delegatorName = context.getInitParameter("entityDelegatorName");
        this.oldDelegator = DelegatorFactory.getDelegator(delegatorName);
        this.delegator = this.oldDelegator.makeTestDelegator("entityDelegatorName");
        this.dispatcher = GenericDispatcher.getLocalDispatcher(delegator.getDelegatorName(), delegator);
        this.setHandlerMapping(new ServiceRpcHandler());

        String extensionsEnabledString = context.getInitParameter("xmlrpc.enabledForExtensions");
        if (UtilValidate.isNotEmpty(extensionsEnabledString)) {
            enabledForExtensions = Boolean.valueOf(extensionsEnabledString);
        }
        String exceptionsEnabledString = context.getInitParameter("xmlrpc.enabledForExceptions");
        if (UtilValidate.isNotEmpty(exceptionsEnabledString)) {
            enabledForExceptions = Boolean.valueOf(exceptionsEnabledString);
        }

        this.setTypeFactory(getCustomTypeFactory(this));

        EventMessageUtil.init(delegator);
    }
}
