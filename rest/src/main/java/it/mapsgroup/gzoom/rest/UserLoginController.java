package it.mapsgroup.gzoom.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
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

	@Autowired
    public UserLoginController(UserLoginService userLoginService, UserPreferenceService userPreferenceService) {
	    this.userLoginService = userLoginService;
    }


    @RequestMapping(value = "user-login", method = RequestMethod.GET)
    @ResponseBody
    public UserLogin getUserLogin() {
        return Exec.exec("user-login", () -> userLoginService.getUserLogin());
    }

	@RequestMapping(value = "change-password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, Object> req, HttpServletRequest request) {
        return Exec.exec("change-password post", () -> userLoginService.changePassword(req, request));
    }

    @RequestMapping(value = "change-language", method = RequestMethod.POST)
    @ResponseBody
    public boolean changeLanguage(@RequestBody Map<String, String> req, HttpServletRequest request) {
	    return Exec.exec("change-languages", () -> userLoginService.changeLang(req, request));
    }
}
