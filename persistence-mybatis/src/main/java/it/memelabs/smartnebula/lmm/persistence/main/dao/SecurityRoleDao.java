package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.SecurityRoleExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.UserLoginSecurityRoleAssocExMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi
 */
@Service
public class SecurityRoleDao {
    private final SecurityRoleExMapper securityRoleMapper;
    private final UserLoginSecurityRoleAssocExMapper roleAssocMapper;

    @Autowired
    public SecurityRoleDao(SecurityRoleExMapper securityRoleMapper, UserLoginSecurityRoleAssocExMapper roleAssocMapper) {
        this.securityRoleMapper = securityRoleMapper;
        this.roleAssocMapper = roleAssocMapper;
    }


    public boolean update(SecurityRole record, UserLogin modifier) {
        if (record == null || record.getId() == 0)
            return false;
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId((modifier != null) ? modifier.getId() : null);
        int ret = securityRoleMapper.updateByPrimaryKey(record);
        return ret > 0;
    }

    public Long create(SecurityRole record, UserLogin creator) {
        if (record == null || record.getId() != null)
            return null;
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId((creator != null) ? creator.getId() : null);
        record.setCreatedStamp(new Date());
        record.setCreatedByUserId((creator != null) ? creator.getId() : null);
        securityRoleMapper.insert(record);
        return record.getId();
    }


    public Tuple2<List<SecurityRoleEx>, Integer> find(RoleFilter filter) {
        SecurityRoleExample example = new SecurityRoleExample();
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        SecurityRoleExample.Criteria criteria = example.createCriteria();
        if (org.apache.commons.lang3.StringUtils.isNoneBlank(filter.getFilterText()))
            criteria.andDescriptionLikeInsensitive("%" + filter.getFilterText() + "%");
        example.setOrderByClause("description");
        int count = securityRoleMapper.countByExample(example);
        List<SecurityRoleEx> result = securityRoleMapper.selectByExampleWithRowboundsEx(example, rowBounds);
        return new Tuple2<>(result, count);
    }


    public SecurityRoleEx find(long id) {
        SecurityRoleEx role = securityRoleMapper.selectByPrimaryKeyEx(id);
        return role;
    }

    public Boolean delete(long id) {
        int ret = securityRoleMapper.deleteByPrimaryKey(id);
        return ret > 0;
    }

    public int saveUserLoginAssoc(List<UserLoginSecurityRoleAssocKey> roles) {
        return roleAssocMapper.bulkInsert(roles);
    }

    public boolean deleteUserLoginAssoc(UserLogin user) {
        UserLoginSecurityRoleAssocExample example = new UserLoginSecurityRoleAssocExample();
        UserLoginSecurityRoleAssocExample.Criteria criteria = example.createCriteria();
        criteria.andUserLoginIdEqualTo(user.getId());
        int ret = roleAssocMapper.deleteByExample(example);
        return ret > 0;
    }

}
