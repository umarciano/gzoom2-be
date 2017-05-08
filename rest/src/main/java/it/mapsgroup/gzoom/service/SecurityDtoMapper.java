package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.*;
import it.mapsgroup.gzoom.security.model.SecurityDomainObject;
import it.memelabs.smartnebula.lmm.model.*;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Fabio G. Strozzi
 */
@Component
public class SecurityDtoMapper {
    private static final Logger LOG = getLogger(SecurityDtoMapper.class);


    /**
     * Convert {@link it.memelabs.smartnebula.lmm.persistence.main.dto.Node} into {@link Node}
     *
     * @param from
     * @return Node
     */
    public Node copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Node from, Node to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public NodeConfiguration copy(it.memelabs.smartnebula.lmm.persistence.main.dto.NodeConfigurationEx from, NodeConfiguration to) {
        copy(from.getNode(), to);
        to.setShowCompanyCciaa(from.getCompanyCciaa());
        to.setShowContractAuthorizedAmount(from.getContractAuthorizedAmount());
        to.setShowContractCustomerAuthorization(from.getContractCustomerAuthorization());
        to.setShowContractAuthorization(from.getContractAuthorization());
        to.setGenerateContractNumber(from.getGenerateContractNumber());
        to.setContractMgoDataAlwaysEditable(from.getContractMgoDataAlwaysEditable());
        return to;
    }


    /**
     * Full copy user method
     * if you need simplified version {#link {@link DtoMapper#copy(UserLogin, User)}}
     *
     * @param from
     * @param to
     * @return
     */
    public User copy(UserLogin from, User to) {
        to.setId(from.getId());
        to.setUsername(from.getUsername());
        to.setFirstName(from.getName());
        to.setLastName(from.getSurname());
        to.setEmail(from.getEmail());
        if (from.getNodes() != null)
            to.setNodes(from.getNodes()
                    .stream()
                    .map(unit -> copy(unit, new Node()))
                    .collect(toList()));
        //user.setPermissions(LmmPermission.maskAll(LmmPermission.fromList(userLogin.getPermissions())));
        to.setAuthenticationType(from.getAuthenticationType().name());


        Map<Long, Node> nodes = to.getNodes().stream().collect(Collectors.toMap(Node::getId, v -> v));
        from.getRoles().forEach(r -> {
            Node node = nodes.get(r.getNodeId());
            if (node == null)
                LOG.error("Node[{}] not valid for user[{}].", r.getNodeId(), from.getUsername());
            else
                node.getRoles().add(copy(r, new SecurityRole()));
        });
        //if (from.getRoles() != null && from.getRoles().size() > 0)
        //    to.setRoles(from.getRoles().stream().map(r -> copy(r, new SecurityRole())).collect(Collectors.toList()));
        return to;
    }

    public SecurityRole copy(it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRole from, SecurityRole to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public SecurityRole copy(it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRoleEx from, SecurityRole to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setPermissions(from.getPermissions().stream().map(p -> copy(p, new SecurityPermission())).filter(p -> p.getEntity() != null).collect(Collectors.toList()));
        return to;
    }

    public SecurityPermission copy(it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermission from, SecurityPermission to) {
        SecurityDomainObject sdo = SecurityDomainObject.fromString(from.getEntityId());
        if (sdo == null) {
            LOG.error("{}.{} not exist", SecurityDomainObject.class.getSimpleName(), from.getEntityId());
            return to;
        } else {
            to.setEntity(copy(sdo, new SecurityEntity()));
            to.setBasic(from.getBasic());
            to.setRead(from.getRead());
            to.setWrite(from.getWrite());
            to.setDelete(from.getDelete());
            to.setValidate(from.getValidate());
            return to;
        }
    }

    public SecurityEntity copy(SecurityDomainObject from, SecurityEntity to) {
        to.setId(from.getId());
        to.setBasic(from.isBasic());
        to.setDescription(from.getId());
        to.setReadable(from.isRead());
        to.setWritable(from.isWrite());
        to.setDeletable(from.isDelete());
        to.setValidable(from.isValidate());
        return to;
    }


}
