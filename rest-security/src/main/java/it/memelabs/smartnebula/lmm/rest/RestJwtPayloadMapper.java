package it.memelabs.smartnebula.lmm.rest;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.security.JwtPayloadMapper;
import it.memelabs.smartnebula.lmm.security.PermissionMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.memelabs.smartnebula.lmm.security.model.LmmPermission.fromList;
import static it.memelabs.smartnebula.lmm.security.model.LmmPermission.maskAll;
import static java.util.stream.Collectors.toList;

/**
 * @author Fabio G. Strozzi
 */
@Component
public class RestJwtPayloadMapper implements JwtPayloadMapper {
    private static final String PAYLOAD_ID = "id";
    private static final String PAYLOAD_USERNAME = "username";
    private static final String PAYLOAD_FIRST_NAME = "firstName";
    private static final String PAYLOAD_LAST_NAME = "lastName";
    private static final String PAYLOAD_EMAIL = "email";
    private static final String PAYLOAD_AUTH_TYPE = "authenticationType";
    private static final String PAYLOAD_PERMISSIONS = "permissions";
    private static final String PAYLOAD_NODES = "nodes";
    private static final String PAYLOAD_DESCRIPTION = "description";
    private static final String PAYLOAD_ACL_PERMISSIONS = "aclPermissions";
    private static final String PAYLOAD_ACTIVE_NODE_ID = "activeNodeId";

    @Override
    public Map<String, Object> map(UserLogin user) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(PAYLOAD_ID, user.getId());
        payload.put(PAYLOAD_USERNAME, user.getUsername());
        payload.put(PAYLOAD_FIRST_NAME, user.getName());
        payload.put(PAYLOAD_LAST_NAME, user.getSurname());
        payload.put(PAYLOAD_EMAIL, user.getEmail());
        payload.put(PAYLOAD_AUTH_TYPE, user.getAuthenticationType().toString());
        payload.put(PAYLOAD_PERMISSIONS, maskAll(fromList(user.getPermissions())));
        payload.put(PAYLOAD_NODES, mapNodes(user.getNodes()));
        payload.put(PAYLOAD_ACL_PERMISSIONS, PermissionMapper.mapPermissions(user.getAclPermissions()));
        payload.put(PAYLOAD_ACTIVE_NODE_ID, mapNodeId(user));
        return payload;
    }


    @Override
    public String getUsername(Map<String, Object> payload) {
        return (String) payload.get(PAYLOAD_USERNAME);
    }

    @Override
    public Long getActiveNodeId(Map<String, Object> payload) {
        return (Long) payload.get(PAYLOAD_ACTIVE_NODE_ID);
    }

    private static List<Map<String, Object>> mapNodes(List<Node> nodes) {
        return nodes.stream().map(RestJwtPayloadMapper::mapNode).collect(toList());
    }

    private static Map<String, Object> mapNode(Node node) {
        Map<String, Object> map = new HashMap<>();
        map.put(PAYLOAD_ID, node.getId());
        map.put(PAYLOAD_DESCRIPTION, node.getDescription());
        return map;
    }

    private static Long mapNodeId(UserLogin user) {
        return (user.getActiveNode() != null) ? user.getActiveNode().getId() : null;
    }

}
