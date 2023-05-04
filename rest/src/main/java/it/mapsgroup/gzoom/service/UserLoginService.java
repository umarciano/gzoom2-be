package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import it.mapsgroup.gzoom.ofbiz.client.OfBizClientException;
import org.apache.xmlrpc.XmlRpcException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.ofbiz.client.AuthenticationOfBizClient;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import it.mapsgroup.gzoom.rest.ValidationException;
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


    public UserLogin getUserLogin() {
        return userLoginDao.getUserLogin(principal().getUserLoginId());
   }

    public Map<String, Object> changePassword(Map<String, Object> req, HttpServletRequest request) {
    	String token = Tokens.token(request);
    	Validators.assertNotNull(req, Messages.USER_LOGIN_ID_REQUIRED);
        Validators.assertNotBlank(token, Messages.INVALID_USERNAME);
        Validators.assertNotBlank((String)req.get("username"), Messages.USER_LOGIN_ID_REQUIRED);
        Validators.assertNotBlank((String)req.get("password"), Messages.MISSING_PASSWORD);
        Validators.assertNotBlank((String)req.get("newPassword"), Messages.MISSING_PASSWORD);
        try {
            Map<String, Object> result = changeService.changePassword(token, (String) req.get("username"), (String) req.get("password"), (String) req.get("newPassword"));
            return result;
        } catch (OfBizClientException ex) {
            LOG.error("changePassword: {}", ex.getMessage());
            throw new ValidationException(ex.getMessage());
        } catch (Exception e) {
            LOG.error("changePassword: {}", e);
            return null;
        }
    }

    public boolean changeLang(Map<String, String> req, HttpServletRequest request) {
        UserLogin user = userLoginDao.getUserLogin(req.get("username"));
        String externalLoginKey = req.get("externalLoginKey");
        if(user!=null) {
            try {
                user.setLastLocale(req.get("lang")); //update sul gzoom2
                userLoginDao.update(user); //update sul gzoom2
                this.changeService.changeSessionLocale(externalLoginKey,user.getUsername(),req.get("lang")); //call update su legacy
                return true;
            }catch (Exception e) {
                LOG.error("changeLang: {}", e);
                return false;
            }
        }
        return false;
    }
}
