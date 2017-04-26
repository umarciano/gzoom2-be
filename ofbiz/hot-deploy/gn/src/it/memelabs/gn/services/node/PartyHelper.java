package it.memelabs.gn.services.node;

import it.memelabs.gn.services.AbstractServiceHelper;
import it.memelabs.gn.services.OfbizErrors;
import it.memelabs.gn.util.GnServiceException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * 08/10/13
 *
 * @author Andrea Fossi
 */
public class PartyHelper extends AbstractServiceHelper {

    public PartyHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
    }

    public Boolean gnPartyRoleCheck(String partyId, String roleTypeId) throws GenericEntityException, GnServiceException {
        return gnPartyRoleCheck(partyId, null, null, roleTypeId);
    }

    /**
     * must have a partyId, partyIdFrom, or partyIdTo in the parameters map, should be called through different service defs for each one
     *
     * @param partyId
     * @param partyIdTo
     * @param partyIdFrom
     * @param roleTypeId
     * @return
     * @throws org.ofbiz.entity.GenericEntityException
     *
     */

    public Boolean gnPartyRoleCheck(String partyId, String partyIdTo, String partyIdFrom, String roleTypeId) throws GenericEntityException, GnServiceException {
        if (UtilValidate.isEmpty(partyId)) {
            if (UtilValidate.isNotEmpty(partyIdTo)) partyId = partyIdTo;
            else partyId = partyIdFrom;
        }
        if (UtilValidate.isEmpty(partyId))
            throw new GnServiceException(OfbizErrors.INVALID_PARAMETERS, "partyId, partyIdTo, partyIdFrom cannot be empty all.");
        if (UtilValidate.isEmpty(roleTypeId)) roleTypeId = PartyRoleOfbiz._NA_.name();
        GenericValue partyRole = delegator.findOne("PartyRole", false, "partyId", partyId, "roleTypeId", roleTypeId);
        return !UtilValidate.isEmpty(partyRole);
    }
}
