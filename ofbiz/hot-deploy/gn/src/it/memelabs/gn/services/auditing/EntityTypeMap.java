package it.memelabs.gn.services.auditing;

import it.memelabs.gn.services.node.PartyNodeTypeOfbiz;

import java.util.HashMap;
import java.util.Map;

/**
 * 22/11/13
 *
 * @author Andrea Fossi
 */
public class EntityTypeMap {

    private static Map<PartyNodeTypeOfbiz, EntityTypeOfbiz> partyNodeTypeEntityTypeMap = new HashMap<PartyNodeTypeOfbiz, EntityTypeOfbiz>();


    static {
        EntityTypeMap.partyNodeTypeEntityTypeMap.put(PartyNodeTypeOfbiz.COMPANY, EntityTypeOfbiz.COMPANY);
        EntityTypeMap.partyNodeTypeEntityTypeMap.put(PartyNodeTypeOfbiz.COMPANY_BASE, EntityTypeOfbiz.COMPANY_BASE);
        EntityTypeMap.partyNodeTypeEntityTypeMap.put(PartyNodeTypeOfbiz.ORGANIZATION, EntityTypeOfbiz.ORGANIZATION_NODE);

       /* for (Map.Entry<String, AuthorizationState> entry : OFBIZ_TO_API.entrySet()) {
            API_TO_OFBIZ.put(entry.getValue(), entry.getKey());
        }*/
    }


    public static Map<PartyNodeTypeOfbiz, EntityTypeOfbiz> getPartyNodeTypeEntityTypeMap() {
        return partyNodeTypeEntityTypeMap;
    }


    public static EntityTypeOfbiz getPartyNodeTypeFromEntityTypeId(String entityTypeId) {
        assert entityTypeId != null;
        PartyNodeTypeOfbiz partyNodeType = PartyNodeTypeOfbiz.valueOf(entityTypeId);
        return EntityTypeMap.getPartyNodeTypeEntityTypeMap().get(partyNodeType);
    }
}
