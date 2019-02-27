package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.ofbiz.service.ChangePasswordServiceOfBiz;
import it.mapsgroup.gzoom.security.Tokens;

/**
 * Profile service.
 *
 */
@Service
public class UserLoginService {
    private static final Logger LOG = getLogger(UserLoginService.class);
    
    private final ChangePasswordServiceOfBiz changeService;
    
    @Autowired
    public UserLoginService(ChangePasswordServiceOfBiz changeService) {
        this.changeService = changeService;
    }

    public Map<String, Object> changePassword(Map<String, Object> req, HttpServletRequest request) {
    	String token = Tokens.token(request);    	
    	Map<String, Object> result = changeService.changePassword(token, (String)req.get("username"), (String)req.get("password"), (String)req.get("newPassword"));
        return result;
    }

    
    
}
