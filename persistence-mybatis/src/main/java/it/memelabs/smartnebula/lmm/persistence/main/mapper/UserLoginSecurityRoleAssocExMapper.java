package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLoginSecurityRoleAssocKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLoginSecurityRoleAssocExMapper extends UserLoginSecurityRoleAssocMapper {

    List<UserLoginSecurityRoleAssocKey> selectUserLoginId(long userLoginId);

    int bulkInsert(@Param("items") List<UserLoginSecurityRoleAssocKey> items);
}