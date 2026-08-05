package it.mapsgroup.gzoom.security.controllers;

import it.mapsgroup.gzoom.ofbiz.service.LoginResponseOfBiz;
import it.mapsgroup.gzoom.ofbiz.service.LoginServiceOfBiz;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import it.mapsgroup.gzoom.security.JwtOfBizLoginAuthenticationProvider;
import it.mapsgroup.gzoom.security.JwtService;
import it.mapsgroup.gzoom.security.PermitsStorage;
import it.mapsgroup.gzoom.security.UnigateOttClient;
import it.mapsgroup.gzoom.security.dto.models.AuthRequest;
import it.mapsgroup.gzoom.security.dto.models.OttLoginRequest;
import it.mapsgroup.gzoom.security.dto.models.OttValidationResponse;
import it.mapsgroup.gzoom.security.dto.models.TokenDto;
import it.mapsgroup.gzoom.security.model.JwtAuthentication;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
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
    private UnigateOttClient unigateOttClient;

    @Autowired
    public AuthController(JwtOfBizLoginAuthenticationProvider jwtOfBizLoginAuthenticationProvider, JwtService jwtService, LoginServiceOfBiz loginService, UserLoginDao userLoginDao, PermitsStorage permitsStorage, Environment env, UnigateOttClient unigateOttClient){
        super();
        this.jwtService = jwtService;
        this.jwtOfBizLoginAuthenticationProvider = jwtOfBizLoginAuthenticationProvider;
        this.loginService = loginService;
        this.userLoginDao = userLoginDao;
        this.permitsStorage = permitsStorage;
        this.env = env;
        this.unigateOttClient = unigateOttClient;
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

    /**
     * SSO Login: scambia un externalLoginKey di OFBiz con un JWT di Spring Boot.
     * Questo endpoint viene chiamato da Angular dopo il redirect SSO da OFBiz.
     *
     * @param externalLoginKey L'external login key generato da OFBiz durante il login SSO
     * @return TokenDto contenente il JWT per Angular
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sso-login", method = RequestMethod.POST)
    public TokenDto ssoLogin(@RequestParam("externalLoginKey") String externalLoginKey) {
        LOG.info("=== SSO Login START ===");
        LOG.info("SSO Login - externalLoginKey received: " + externalLoginKey);
        
        if (externalLoginKey == null || externalLoginKey.isEmpty()) {
            LOG.error("SSO Login - externalLoginKey is null or empty");
            return null;
        }
        
        try {
            // Valida l'externalLoginKey chiamando OFBiz
            String userLoginId = validateExternalLoginKeyWithOfbiz(externalLoginKey);
            
            if (userLoginId == null) {
                LOG.error("SSO Login - Invalid or expired externalLoginKey: " + externalLoginKey);
                return null;
            }
            
            LOG.info("SSO Login - userLoginId resolved from OFBiz: " + userLoginId);
            
            // Recupera il profilo utente dal database
            UserLogin profile = userLoginDao.getUserLogin(userLoginId);
            if (profile == null) {
                LOG.error("SSO Login - User not found in database: " + userLoginId);
                return null;
            }
            
            // Usa l'externalLoginKey ricevuto da OFBiz (già validato dal SSO)
            profile.setExternalLoginKey(externalLoginKey);
            
            // Recupera i dati persona (firstName, lastName) dal database
            Person person = profile.getPerson();
            if (person == null) {
                LOG.warn("SSO Login - Person data not found for user: " + userLoginId);
                person = new Person();
                person.setFirstName(userLoginId);
                person.setLastName("");
            }
            profile.setPerson(person);
            
            // Genera il JWT
            String token = jwtService.generate(profile);
            permitsStorage.save(token, profile.getUsername());
            
            LOG.info("SSO Login - JWT generated successfully for user: " + profile.getUsername());
            LOG.info("=== SSO Login END - SUCCESS ===");
            return new TokenDto(token);
            
        } catch (Exception e) {
            LOG.error("SSO Login - Error during authentication: " + e.getMessage(), e);
            LOG.info("=== SSO Login END - ERROR ===");
            return null;
        }
    }
    
    /**
     * OTT Login: valida un One-Time Token emesso da UNIGATE e restituisce un JWT Gzoom2.
     * Il token viene verificato chiamando UNIGATE GET /api/portal/ott/validate.
     * Se valido, autentica l'utente su OFBiz via loginWithOnlyUserLoginId e genera il JWT.
     *
     * @param request body con il campo "token"
     * @return TokenDto con il JWT Gzoom2, oppure 403 se il token non è valido o l'utente non esiste
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ott-login", method = RequestMethod.POST)
    public ResponseEntity<TokenDto> ottLogin(@RequestBody OttLoginRequest request) {
        LOG.info("=== OTT Login START ===");

        if (request == null || request.token == null || request.token.isEmpty()) {
            LOG.error("OTT Login - token is null or empty");
            return ResponseEntity.badRequest().build();
        }

        LOG.info("OTT Login - token received (troncato): " + request.token.substring(0, Math.min(8, request.token.length())) + "...");

        try {
            // 1. Valida il token contro UNIGATE
            OttValidationResponse validation = unigateOttClient.validate(request.token);

            if (!validation.isValid() || validation.getAppUsername() == null || validation.getAppUsername().isEmpty()) {
                LOG.error("OTT Login - token non valido o appUsername assente");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String username = validation.getAppUsername();
            LOG.info("OTT Login - appUsername da UNIGATE: " + username);

            // 2. Recupera il profilo utente dal DB
            UserLogin profile = userLoginDao.getUserLogin(username);
            if (profile == null) {
                LOG.error("OTT Login - utente non trovato nel DB: " + username);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // 3. Autentica su OFBiz senza password (stesso pattern di getToken)
            LoginResponseOfBiz response = loginService.loginWithOnlyUserLoginId(username);
            if (StringUtils.isEmpty(response.getExternalLoginKey())) {
                LOG.error("OTT Login - OFBiz loginWithOnlyUserLoginId fallito per: " + username);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            profile.setExternalLoginKey(response.getExternalLoginKey());

            Person person = profile.getPerson();
            if (person == null) {
                person = new Person();
                person.setFirstName(username);
                person.setLastName("");
            } else {
                person.setFirstName(response.getFirstName());
                person.setLastName(response.getLastName());
            }
            profile.setPerson(person);

            // 4. Genera JWT Gzoom2
            String token = jwtService.generate(profile);
            permitsStorage.save(token, profile.getUsername());

            LOG.info("OTT Login - JWT generato per utente: " + profile.getUsername());
            LOG.info("=== OTT Login END - SUCCESS ===");
            return ResponseEntity.ok(new TokenDto(token));

        } catch (Exception e) {
            LOG.error("OTT Login - Errore: " + e.getMessage(), e);
            LOG.info("=== OTT Login END - ERROR ===");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Valida un externalLoginKey chiamando l'endpoint OFBiz.
     *
     * @param externalLoginKey La chiave da validare
     * @return userLoginId se la chiave è valida, null altrimenti
     */
    private String validateExternalLoginKeyWithOfbiz(String externalLoginKey) {
        LOG.info("=== Validating externalLoginKey with OFBiz ===");
        LOG.info("ExternalLoginKey: " + externalLoginKey);
        
        try {
            // URL dell'endpoint OFBiz (TODO: spostare in configurazione)
            String ofbizUrl = "http://localhost:8080/gzoom/control/validateExternalLoginKey";
            String urlWithParams = ofbizUrl + "?externalLoginKey=" + java.net.URLEncoder.encode(externalLoginKey, "UTF-8");
            
            LOG.info("Calling OFBiz endpoint: " + urlWithParams);
            
            // Effettua la chiamata HTTP GET
            java.net.URL url = new java.net.URL(urlWithParams);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            LOG.info("OFBiz response code: " + responseCode);
            
            if (responseCode == 200) {
                // Leggi la risposta JSON
                java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream())
                );
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                String jsonResponse = response.toString();
                LOG.info("OFBiz response: " + jsonResponse);
                
                // Parse JSON manualmente (semplice parsing per evitare dipendenze)
                // Formato atteso: {"valid":true,"userLoginId":"primario.cardiologiautic","partyId":"10420","enabled":"Y"}
                if (jsonResponse.contains("\"valid\":true")) {
                    // Estrai userLoginId dal JSON
                    int start = jsonResponse.indexOf("\"userLoginId\":\"") + 15;
                    int end = jsonResponse.indexOf("\"", start);
                    if (start > 14 && end > start) {
                        String userLoginId = jsonResponse.substring(start, end);
                        LOG.info("ExternalLoginKey validated successfully! UserLoginId: " + userLoginId);
                        return userLoginId;
                    }
                }
                
                LOG.warn("Invalid JSON response from OFBiz: " + jsonResponse);
                return null;
                
            } else if (responseCode == 401) {
                LOG.warn("OFBiz returned 401: Invalid or expired externalLoginKey");
                return null;
            } else {
                LOG.error("OFBiz returned unexpected status code: " + responseCode);
                return null;
            }
            
        } catch (Exception e) {
            LOG.error("Error calling OFBiz validateExternalLoginKey endpoint: " + e.getMessage(), e);
            return null;
        }
    }
}


