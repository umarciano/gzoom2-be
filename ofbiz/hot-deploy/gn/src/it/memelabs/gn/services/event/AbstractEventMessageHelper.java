package it.memelabs.gn.services.event;

import it.memelabs.gn.services.AbstractServiceHelper;
import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * 14/01/14
 *
 * @author Andrea Fossi
 */
public abstract class AbstractEventMessageHelper extends AbstractServiceHelper {
    protected AbstractEventMessageHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }
}
