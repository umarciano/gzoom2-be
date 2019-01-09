package it.mapsgroup.gzoom.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.StatusItemExt;
import it.mapsgroup.gzoom.service.StatusItemService;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StatusItemController {

	private final StatusItemService statusItemService;

	@Autowired
	public StatusItemController(StatusItemService statusItemService) {
		this.statusItemService = statusItemService;
	}

	@RequestMapping(value = "status-items/{parentTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<StatusItemExt> getStatusItems(@PathVariable(value = "parentTypeId") String parentTypeId) {
		return Exec.exec("status-items get", () -> statusItemService.getStatusItems(parentTypeId));
	}
}
