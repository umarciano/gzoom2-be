package it.mapsgroup.gzoom.mybatis.dao;

import it.mapsgroup.gzoom.mybatis.dto.UserLoginGZoom;
import it.mapsgroup.gzoom.mybatis.mapper.UserLoginGZoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Andrea Fossi.
 */
@Service
public class UserLoginMyBatisDao {
    private final UserLoginGZoomMapper userLoginMapper;

    @Autowired
    public UserLoginMyBatisDao(UserLoginGZoomMapper userLoginMapper) {
        this.userLoginMapper = userLoginMapper;
    }

    public UserLoginGZoom findByUserLoginId(String userLoginId) {
        return userLoginMapper.selectByPrimaryKey(userLoginId);
    }
}
