package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AbstractIdentity;
import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermission;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.SecurityRolePermissionExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.UserLoginMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrea Fossi
 */
@Service
public class UserLoginDao {
    private final UserLoginMapper userLoginMapper;
    private final SecurityRolePermissionExMapper permissionExMapper;


    @Autowired
    public UserLoginDao(UserLoginMapper userLoginMapper,
                        SecurityRolePermissionExMapper permissionExMapper) {
        this.userLoginMapper = userLoginMapper;
        this.permissionExMapper = permissionExMapper;
    }

    public UserLogin getUserLogin(String username) {
        if (StringUtils.isEmpty(username))
            return null;
        UserLogin user = userLoginMapper.selectProfile(username, null);
        return user;
    }

    public UserLogin getUserLogin(long id) {
        UserLogin user = userLoginMapper.selectProfile(null, id);
        return user;
    }

    public Tuple2<List<UserLogin>, Integer> find(UserLoginFilter filter) {
        int count = userLoginMapper.countUsers(filter);
        List<UserLogin> result = userLoginMapper.selectUsersWithRowbounds(filter, new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize()));
        return new Tuple2<>(result, count);
    }


    public boolean updateUserUserLogin(UserLogin userLogin, UserLogin modifier) {
        if (userLogin == null || userLogin.getId() == 0)
            return false;
        userLogin.setModifiedStamp(new Date());
        userLogin.setModifiedByUserId((modifier != null) ? modifier.getId() : null);
        userLogin.setEnabled(true);
        int ret = userLoginMapper.updateUser(userLogin);
        if (ret == 0) {
            return false;
        } else {
            if (userLogin.getNodes().size() > 0) {
                List<Long> oldIds = userLoginMapper.selectNodeAssicIds(userLogin.getId());
                List<Long> newIds = userLogin.getNodes().stream().map(AbstractIdentity::getId).collect(Collectors.toList());
                newIds.stream().filter(newId -> !oldIds.contains(newId)).forEach(newId -> userLoginMapper.insertNodeAssoc(userLogin.getId(), newId));
                oldIds.stream().filter(oldId -> !newIds.contains(oldId)).forEach(oldId -> userLoginMapper.deleteNodeAssocByPrimaryKey(userLogin.getId(), oldId));
            }
            return true;
        }
    }

    public Long createUserLogin(UserLogin userLogin, UserLogin creator) {
        if (userLogin == null || userLogin.getId() != null)

            return null;
        userLogin.setModifiedStamp(new Date());
        userLogin.setModifiedByUserId((creator != null) ? creator.getId() : null);
        userLogin.setCreatedStamp(new Date());
        userLogin.setCreatedByUserId((creator != null) ? creator.getId() : null);
        userLogin.setEnabled(true);
        int ret = userLoginMapper.insert(userLogin);
        if (ret <= 0 || userLogin.getId() == null) {
            return null;
        } else {
            if (userLogin.getNodes().size() > 0) {
                List<Long> ids = userLogin.getNodes().stream().map(AbstractIdentity::getId).collect(Collectors.toList());
                userLoginMapper.insertNodeAssocBatch(userLogin.getId(), ids);
            }
            return userLogin.getId();
        }
    }

    public boolean userLoginExist(String username) {
        if (StringUtils.isEmpty(username))
            return false;
        int result = userLoginMapper.selectCountByUsername(username);
        return result > 0;
    }

    public int userEmailCount(String email) {
        return userLoginMapper.selectCountByEmail(email);
    }

    public int userEmailCountForUpdate(String email, String username) {
        return userLoginMapper.selectCountByEmailAndNotUsername(email, username);
    }


    public UserLogin getSecurityProfile(String username, Long activeNodeId) {
        if (StringUtils.isEmpty(username))
            return null;
        UserLogin user = userLoginMapper.selectBaseProfile(username, null);
        //get permissions from current node
        if (user == null)
            return null;
        if (activeNodeId != null) {
            List<SecurityRolePermission> permissions = permissionExMapper.selectByUserLoginId(user.getId(), activeNodeId);
            user.setAclPermissions(permissions);
            user.setActiveNode(user.getNodes().stream().filter(node -> node.getId().equals(activeNodeId)).findFirst().get());
        } else if (user.getNodes().size() > 0) {
            List<SecurityRolePermission> permissions = permissionExMapper.selectByUserLoginId(user.getId(), user.getNodes().get(0).getId());
            user.setAclPermissions(permissions);
            user.setActiveNode(user.getNodes().get(0));
        }
        return user;
    }

    public boolean updatePassword(UserLogin user, UserLogin modifier) {
        user.setModifiedByUserId(modifier.getId());
        user.setModifiedStamp(new Date());
        int ret = userLoginMapper.updateUser(user);
        return ret == 1;
    }

}
