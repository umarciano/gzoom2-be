package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dao.UserLoginFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;

/**
 * @author Andrea Fossi
 */
public interface UserLoginMapper extends UserLoginPersistentMapper {

    int insertEx(UserLogin record);

    UserLogin selectByUsername(String username);


    int selectCountByUsername(String username);

    int updateUser(UserLogin record);

    int deleteNodeAssocByUserLoginId(Long userLoginId);

    List<Long> selectNodeAssicIds(Long userLoginId);

    int deleteNodeAssocByPrimaryKey(@Param("userLoginId") Long userLoginId, @Param("nodeId") Long nodeId);

    int insertNodeAssocBatch(@Param("userLoginId") Long userLoginId, @Param("nodes") Collection<Long> nodeIds);

    int insertNodeAssoc(@Param("userLoginId") Long userLoginId, @Param("nodeId") Long nodeId);

    List<UserLogin> selectUsersWithRowbounds(UserLoginFilter filter, RowBounds rowBounds);

    int countUsers(UserLoginFilter filter);

    int selectCountByEmail(@Param("email") String email);

    int selectCountByEmailAndNotUsername(@Param("email") String email, @Param("username") String username);

    /**
     * return {@link UserLogin} with {@link UserLogin#nodes} and {@link UserLogin#roles} filled
     * {@link UserLogin#aclPermissions} is ignored
     *
     * @param username
     * @param id
     * @return
     */
    UserLogin selectProfile(@Param("username") String username, @Param("id") Long id);

    /**
     * return {@link UserLogin} with {@link UserLogin#nodes} and {@link UserLogin#roles} is ignored
     * {@link UserLogin#aclPermissions} is ignored
     *
     * @param username
     * @param id
     * @return
     */
    UserLogin selectBaseProfile(@Param("username") String username, @Param("id") Long id);
}
