package it.mapsgroup.gzoom.ofbiz.client;

import java.util.Map;

public interface AuthenticationOfBizClient {

    /**
     * Login method.
     * Return a token that will can be used by next request.
     *
     * @param username
     * @param password
     * @param loginSourceId {@link it.memelabs.gn.services.login.LoginSourceOfbiz} values
     * @return <ul>
     * <li>{@link String} sessionId</li>
     * <li>{@link Map} companyProfile (containing entries: rootPublicationEnabled, privateAuthorizationEnabled, privateAuthCheckEnabled)</li>
     * </ul>
     */
    public abstract Map<String, Object> login(String username, String password, String loginSourceId);

    /**
     * Login method.
     * Return a token that will can be used by next request.
     *
     * @param username
     * @param password
     * @param loginSourceId {@link it.memelabs.gn.services.login.LoginSourceOfbiz} values
     * @param deviceId-     device ID
     * @return <ul>
     * <li>{@link String} sessionId</li>
     * <li>{@link Map} companyProfile (containing entries: rootPublicationEnabled, privateAuthorizationEnabled, privateAuthCheckEnabled)</li>
     * </ul>
     */
    public abstract Map<String, Object> login(String username, String password, String loginSourceId, String deviceId, String deviceDescription, String appId, String appVersion);


    public abstract Map<String, Object> getSessionContext(Map<String, Object> session, String sessionId);

    /**
     * Logout method.
     *
     * @param sessionId
     * @return
     */
    public abstract String logout(String sessionId);


    public abstract Map<String, Object> rollbackDb();

}
