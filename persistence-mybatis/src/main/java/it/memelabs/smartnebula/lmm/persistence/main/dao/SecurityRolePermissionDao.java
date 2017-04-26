package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermission;
import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermissionExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.SecurityRolePermissionExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrea Fossi
 */
@Service
public class SecurityRolePermissionDao {
    private final SecurityRolePermissionExMapper securityRolePermissionExMapper;

    @Autowired
    public SecurityRolePermissionDao(SecurityRolePermissionExMapper securityRolePermissionExMapper) {
        this.securityRolePermissionExMapper = securityRolePermissionExMapper;
    }

    public int create(List<SecurityRolePermission> roles) {
        if (roles.size() > 0)
            return securityRolePermissionExMapper.batchInsert(roles);
        else return 0;
    }

    public int deleteByRoleId(Long roleId) {
        SecurityRolePermissionExample example = new SecurityRolePermissionExample();
        SecurityRolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        return securityRolePermissionExMapper.deleteByExample(example);
    }
}
