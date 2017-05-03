// LoginOfBizClient.java, created on 14/dic/2012
package it.mapsgroup.gzoom.ofbiz.client.impl;

import it.mapsgroup.gzoom.ofbiz.client.AuthenticationOfBizClient;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClient;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClientConfig;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * OfBiz login service client.
 *
 * @author Andrea Fossi
 */
public class AuthenticationOfBizClientImpl extends OfBizClient implements AuthenticationOfBizClient {
    private static final Logger log = LoggerFactory
            .getLogger(AuthenticationOfBizClientImpl.class);

    /**
     * @param config
     * @param connectionManager
     */
    public AuthenticationOfBizClientImpl(OfBizClientConfig config, HttpConnectionManager connectionManager) {
        super(config, connectionManager);
    }

    public AuthenticationOfBizClientImpl(OfBizClientConfig config) {
        super(config);
    }

    @Override
    public Map<String, Object> login(String username, String password, String loginSourceId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("login.username", username);
        paramMap.put("login.password", password);
        paramMap.put("loginSourceId", loginSourceId);

        Map<String, Object> result = execute("gnLogin", paramMap);
        String sessionId = (String) result.get("sessionId");
        log.debug("Login reply sessionId: {}", sessionId);
        return result;
    }

    public Map<String, Object> login(String username, String password, String loginSourceId,
                                     String deviceId,String deviceDescription,String appId,String appVersion) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("login.username", username);
        paramMap.put("login.password", password);
        
        Map<String, Object> result = execute("gzSimpleLogin", paramMap);
        String sessionId = (String) result.get("sessionId");
        log.debug("Login reply sessionId: {}", sessionId);
        return result;
    }


    /**
     * Logout method.
     *
     * @param sessionId
     * @return
     */
    @Override
    public String logout(String sessionId) {
        Map<String, String> paramMap = new HashMap<String, String>();

        Map<String, Object> result = execute("gzLogout", sessionId, paramMap);
        String resp = (String) result.get("result");
        log.debug("Logout  reply message: {}", resp);
        return resp;
    }


    /**
     * Get paramMap context
     *
     * @param paramMap
     * @param sessionId
     * @return
     */
    @Override

    public Map<String, Object> getSessionContext(Map<String, Object> paramMap, String sessionId) {
        Map<String, Object> result = execute("gnFindContextById", sessionId, paramMap);
        String resp = (String) result.get("result");
        log.debug("Logout  reply message: {}", resp);
        return result;
    }

    public Map<String, Object> rollbackDb() {
        
        return null;
    }


}
