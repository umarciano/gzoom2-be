package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import it.mapsgroup.gzoom.ofbiz.client.AuthenticationOfBizClient;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.ofbiz.service.ChangePasswordServiceOfBiz;
import it.mapsgroup.gzoom.security.Tokens;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Profile service.
 *
 */
@Service
public class UserLoginService {
    private static final Logger LOG = getLogger(UserLoginService.class);
    
    private final ChangePasswordServiceOfBiz changeService;
    private final UserLoginDao userLoginDao;

    @Autowired
    public UserLoginService(ChangePasswordServiceOfBiz changeService, UserLoginDao userLoginDao) {
        this.changeService = changeService;
        this.userLoginDao = userLoginDao;
    }

    public Map<String, Object> changePassword(Map<String, Object> req, HttpServletRequest request) {
    	String token = Tokens.token(request);    	
    	Map<String, Object> result = changeService.changePassword(token, (String)req.get("username"), (String)req.get("password"), (String)req.get("newPassword"));
        return result;
    }

    public boolean changeLang(Map<String, String> req, HttpServletRequest request) {
        UserLogin user = userLoginDao.getUserLogin(req.get("username"));
        String token = Tokens.token(request);
        if(user!=null) {
            //this.changeService.changeSessionLocale(token,user.getUsername(),user.getCurrentPassword(),req.get("lang"));
           user.setLastLocale(req.get("lang"));
            return userLoginDao.update(user);
           // return true;
        }
        return false;
    }
    
    
}
