package it.mapsgroup.gzoom.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }
	

	@RequestMapping(value = "change-password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, Object> req, HttpServletRequest request) {
        return Exec.exec("change-password post", () -> userLoginService.changePassword(req, request));
    }
}
