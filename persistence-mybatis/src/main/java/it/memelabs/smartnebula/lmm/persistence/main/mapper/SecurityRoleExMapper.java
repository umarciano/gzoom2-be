package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRoleEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRoleExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityUserRole;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SecurityRoleExMapper extends SecurityRoleMapper {
    List<SecurityRoleEx> selectByExampleWithRowboundsEx(SecurityRoleExample example, RowBounds rowBounds);

    SecurityRoleEx selectByPrimaryKeyEx(Long id);

    List<SecurityUserRole> selectByUserLoginId(Long userLoginId);
}