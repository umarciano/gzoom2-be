package it.memelabs.smartnebula.lmm.persistence.main.util;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;

/**
 * @author Andrea Fossi.
 */
public class Dummies {
    public static UserLogin getUserLogin() {
        return getUserLogin(1L);
    }

    public static UserLogin getUserLogin(Long nodeId) {
        Node node = new Node();
        node.setId(nodeId);
        UserLogin userLogin = new UserLogin();
        userLogin.setActiveNode(node);
        return userLogin;
    }
}
