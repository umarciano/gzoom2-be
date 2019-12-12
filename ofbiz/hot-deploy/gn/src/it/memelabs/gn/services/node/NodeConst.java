package it.memelabs.gn.services.node;

import javolution.util.FastMap;
import javolution.util.FastSet;

import java.util.Map;
import java.util.Set;

/**
 * 02/04/13
 *
 * @author Andrea Fossi
 */
public class NodeConst {
    public static final Map<String, String> typeToRole;

    static {
        typeToRole = FastMap.newInstance();
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.COMPANY.name(), "GN_COMPANY");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.COMPANY_BASE.name(), "GN_COMPANY_BASE");

        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.PRIVATE_COMPANY.name(), "GN_PVT_COMPANY");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.PRIVATE_COMPANY_BASE.name(), "GN_PVT_COMPANY_BASE");

        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.INVITED_COMPANY.name(), "GN_INVITED_COMPANY");

        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.ISSUER.name(), "GN_ISSUER");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.ISSUER_BASE.name(), "GN_ISSUER_BASE");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.ORGANIZATION.name(), "GN_ORGANIZATION_NODE");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.ROOT.name(), "GN_ROOT");
        NodeConst.typeToRole.put(PartyNodeTypeOfbiz.STUB.name(), "GN_STUB_NODE");
    }

    public static final Set<String> propagationNodes;

    static {
        propagationNodes = FastSet.newInstance();
        propagationNodes.add(PartyNodeTypeOfbiz.COMPANY.name());
        propagationNodes.add(PartyNodeTypeOfbiz.COMPANY_BASE.name());
        propagationNodes.add(PartyNodeTypeOfbiz.ORGANIZATION.name());
        propagationNodes.add(PartyNodeTypeOfbiz.ROOT.name());
    }
}
