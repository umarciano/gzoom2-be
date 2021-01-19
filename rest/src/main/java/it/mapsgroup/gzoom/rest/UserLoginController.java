package it.mapsgroup.gzoom.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import it.mapsgroup.gzoom.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.service.UserLoginService;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserLoginController {
	
	private final UserLoginService userLoginService;
    private final UserPreferenceService userPreferenceService;
	
	@Autowired
    public UserLoginController(UserLoginService userLoginService, UserPreferenceService userPreferenceService) {
	    this.userLoginService = userLoginService;
	    this. userPreferenceService = userPreferenceService;
    }
	

	@RequestMapping(value = "change-password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, Object> req, HttpServletRequest request) {
        return Exec.exec("change-password post", () -> userLoginService.changePassword(req, request));
    }

    @RequestMapping(value = "change-language", method = RequestMethod.POST)
    @ResponseBody
    public boolean changeLanguage(@RequestBody Map<String, String> req) {
	    return Exec.exec("change-languages", () -> userLoginService.changeLang(req));
    }

    @RequestMapping(value = "user-preference", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUserPreference(@RequestBody UserPreference req) {
        return Exec.exec("user-preference put", () -> userPreferenceService.updateUserPreference(req));
    }
}
