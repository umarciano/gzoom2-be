package it.mapsgroup.gzoom.ofbiz.service;

import it.mapsgroup.gzoom.ofbiz.client.AuthenticationOfBizClient;

import java.util.Map;

/**
 * @author 
 */
public class ChangePasswordServiceOfBiz {

    private final AuthenticationOfBizClient loginClient;

    public ChangePasswordServiceOfBiz(AuthenticationOfBizClient loginClient) {
        this.loginClient = loginClient;
    }

    public Map<String, Object> changePassword(String sessionId, String username, String password, String newPassword) {
    	return loginClient.changePassword(sessionId, username, password, newPassword);
    }
}
