package it.mapsgroup.gzoom.security.controllers;

import it.mapsgroup.gzoom.ofbiz.service.LoginResponseOfBiz;
import it.mapsgroup.gzoom.ofbiz.service.LoginServiceOfBiz;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import it.mapsgroup.gzoom.security.JwtOfBizLoginAuthenticationProvider;
import it.mapsgroup.gzoom.security.JwtService;
import it.mapsgroup.gzoom.security.PermitsStorage;
import it.mapsgroup.gzoom.security.dto.models.AuthRequest;
import it.mapsgroup.gzoom.security.dto.models.TokenDto;
import it.mapsgroup.gzoom.security.model.JwtAuthentication;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final JwtService jwtService;
    private JwtOfBizLoginAuthenticationProvider jwtOfBizLoginAuthenticationProvider;
    private LoginServiceOfBiz loginService;
    private UserLoginDao userLoginDao;
    private PermitsStorage permitsStorage;
    private final String apiKeyHeaderName = "gzoom2apikey";
    private final String apiKeyPropertyName = "ofbiz.server.xmlrpc.apikey";
    private final String oneLoginSSOEnabledPropertyName = "ofbiz.server.sso.onelogin.enable";
    private final String oneLoginSSOLoginUrlPropertyName = "ofbiz.server.sso.onelogin.login.url";
    private final String oneLoginSSOLogoutUrlPropertyName = "ofbiz.server.sso.onelogin.logout.url";
    private final String defaultLoginMethod = "GzoomNativeLogin";
    private final String oneLoginSSOLoginMethod = "OneLogin";
    private final String logoutUsernameHeaderName = "ofbiz.server.sso.sirac.logout.username.header.name";
    private final String enableChangePassword = "security.enableChangePassword";
    private Environment env;

    @Autowired
    public AuthController(JwtOfBizLoginAuthenticationProvider jwtOfBizLoginAuthenticationProvider, JwtService jwtService, LoginServiceOfBiz loginService, UserLoginDao userLoginDao, PermitsStorage permitsStorage, Environment env){
        super();
        this.jwtService = jwtService;
        this.jwtOfBizLoginAuthenticationProvider = jwtOfBizLoginAuthenticationProvider;
        this.loginService = loginService;
        this.userLoginDao = userLoginDao;
        this.permitsStorage = permitsStorage;
        this.env =env;
    }


    @RequestMapping(value = "/getEnableChangePassword", method = RequestMethod.GET)
    public boolean getEnableChangePassword() {
        Boolean enableChangePsw = this.env.getProperty(enableChangePassword, Boolean.class, true);
        if(enableChangePsw != null){
            return enableChangePsw;
        }
        else{
            LOG.error("no security.enableChangePassword configuration found, (default true)");
            return true;
        }
    }


    @RequestMapping(value = "/getOneLogin-LogoutUrl", method = RequestMethod.GET)
    public String getOneLoginLogoutUrl() {
        String oneLoginSSOLogoutUrl = this.env.getProperty(oneLoginSSOLogoutUrlPropertyName);
        if(oneLoginSSOLogoutUrl != null){
            return oneLoginSSOLogoutUrl;
        }
        else{
            LOG.error("no oneLoginSSOLogoutUrlPropertyName configuration found");
            return null;
        }
    }


    @RequestMapping(value = "/getOneLogin-LoginUrl", method = RequestMethod.GET)
    public String getOneLoginLoginUrl() {
        String oneLoginSSOLoginUrl = this.env.getProperty(oneLoginSSOLoginUrlPropertyName);
        if(oneLoginSSOLoginUrl != null){
            return oneLoginSSOLoginUrl;
        }
        else{
            LOG.error("no oneLoginSSOLoginUrlPropertyName configuration found");
            return null;
        }
    }



    @RequestMapping(value = "/getLoginMethod", method = RequestMethod.GET)
    public String getLoginMethod() {
        String oneLoginSSOEnabled = this.env.getProperty(oneLoginSSOEnabledPropertyName);
        if(oneLoginSSOEnabled != null){
            if(oneLoginSSOEnabled.equals("true")){
                return oneLoginSSOLoginMethod;
            }
            else{
                return defaultLoginMethod;
            }
        }
        else{
            LOG.error("no oneLoginSSOEnabledPropertyName configuration found");
            return defaultLoginMethod;
        }
    }

    /*
        API utilizzata per eseguire il logout richiesto da un altro SP, ovvero SSO Global Logout
        lo username di cui effettuare il logout deve essere passato nell'header con nome configurato
        nella property :
     */
    @RequestMapping(value = "/doLogout", method = RequestMethod.POST)
    public String doLogout(@RequestHeader MultiValueMap<String, String> headers) {
        String err = null;
        String logoutUsernameHeader = this.env.getProperty(logoutUsernameHeaderName);
        if(logoutUsernameHeader != null && !logoutUsernameHeader.isEmpty()){
            if(headers != null && !headers.isEmpty() && headers.getFirst(logoutUsernameHeader) != null){
                String userName = headers.getFirst(logoutUsernameHeader);
                if(userName != null && !userName.isEmpty()){
                    loginService.logout(userName);
                    return "Logout succeed";
                }
                else{
                    err = "no username found on request header " + logoutUsernameHeader;
                    LOG.error(err);
                }
            }
            else{
                err = "no request headers found or not request header found with name " + logoutUsernameHeader;
                LOG.error(err);
            }
        }
        else{
            err = "no value found for configuration property " + logoutUsernameHeaderName;
            LOG.error(err);
        }
        return "Logout not succeed. " + err;
    }





    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public TokenDto getToken(@RequestBody AuthRequest credentials,@RequestHeader MultiValueMap<String, String> headers) {
        if(headers != null){
            String requestApiKey = headers.getFirst(apiKeyHeaderName);
            if(requestApiKey != null){
                String confApiKey = this.env.getProperty(apiKeyPropertyName);
                if(confApiKey != null){
                    if(requestApiKey.equals(confApiKey)){
                        UserLogin principal = new UserLogin();
                        principal.setUserLoginId(credentials.uid);
                        UserLogin profile = userLoginDao.getUserLogin(principal.getUserLoginId());
                        LoginResponseOfBiz response = loginService.loginWithOnlyUserLoginId(credentials.uid);
                        Person person = new Person();
                        person.setFirstName(response.getFirstName());
                        person.setLastName(response.getLastName());
                        principal.setPerson(person);
                        profile.setExternalLoginKey(response.getExternalLoginKey());
                        String token =null;
                        try {
                            token = jwtService.generate(profile);
                        } catch (JoseException e) {
                            e.printStackTrace();
                        }
                        permitsStorage.save(token, profile.getUsername());
                        JwtAuthentication auth = new JwtAuthentication(token, profile);
                        LOG.info("Authenticated with username : " + credentials.uid);
                        return new TokenDto(token);
                    }
                    else{
                        LOG.error("wrong apikey found");
                        return null;
                    }
                }
                else{
                    LOG.error("no apikey configuration found");
                    return null;
                }
            }
            else{
                LOG.error("no apikey header found");
                return null;
            }
        }
        else{
            LOG.error("no headers found");
            return null;
        }
    }
}

