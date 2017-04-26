package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.SecurityRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityRolePermissionExMapper extends SecurityRolePermissionMapper {
    SecurityRolePermission selectByRoleId(@Param("roleId") Long roleId);

    List<SecurityRolePermission> selectByUserLoginId(@Param("userLoginId") Long userLoginId, @Param("nodeId") Long nodeId);

    int batchInsert(@Param("roles") List<SecurityRolePermission> roles);

}