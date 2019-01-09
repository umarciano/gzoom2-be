package it.mapsgroup.gzoom.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.RoleType;
import it.mapsgroup.gzoom.service.RoleTypeService;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RoleTypeController {
	private final RoleTypeService roleTypeService;

	@Autowired
	public RoleTypeController(RoleTypeService roleTypeService) {
		this.roleTypeService = roleTypeService;
	}

	@RequestMapping(value = "role-types", method = RequestMethod.GET)
	@ResponseBody
	public Result<RoleType> getRoleTypes() {
		return Exec.exec("role-types get", () -> roleTypeService.getRoleTypes());
	}
}
