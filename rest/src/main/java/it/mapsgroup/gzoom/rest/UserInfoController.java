package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.rest.dto.UserInfoDto;
import it.mapsgroup.gzoom.rest.service.UserInfoService;
import it.mapsgroup.gzoom.common.Exec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller REST per le informazioni complete dell'utente
 * Fornisce endpoint per recuperare tutti i dati necessari al modal "Informazioni Utente"
 */
@RestController
@RequestMapping("/user-info")
public class UserInfoController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoController.class);
    
    private final UserInfoService userInfoService;
    
    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    
    /**
     * Recupera le informazioni complete dell'utente dato il user_login_id
     * 
     * @param userLoginId l'ID dell'utente (es. "sdepascale")
     * @return UserInfoDto con tutte le informazioni dell'utente
     */
    @RequestMapping(value = "/{userLoginId}", method = RequestMethod.GET)
    @ResponseBody
    public UserInfoDto getUserInfo(@PathVariable("userLoginId") String userLoginId) {
        LOG.info("REST request for user info: {}", userLoginId);
        
        return Exec.exec("getUserInfo", () -> {
            UserInfoDto result = userInfoService.getUserCompleteInfo(userLoginId);
            LOG.info("User info retrieved successfully for: {}", userLoginId);
            return result;
        });
    }
    
    /**
     * Endpoint alternativo che accetta il userLoginId come parametro query
     * 
     * @param userLoginId l'ID dell'utente
     * @return UserInfoDto con tutte le informazioni dell'utente
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public UserInfoDto getUserInfoByParam(@RequestParam("userLoginId") String userLoginId) {
        LOG.info("REST request for user info (param): {}", userLoginId);
        
        return Exec.exec("getUserInfoByParam", () -> {
            UserInfoDto result = userInfoService.getUserCompleteInfo(userLoginId);
            LOG.info("User info retrieved successfully for: {}", userLoginId);
            return result;
        });
    }
}