package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.mybatis.dto.Visitor;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @RequestMapping(value = "/visitor", method = RequestMethod.GET)
    @ResponseBody
    public Result<Visitor> getVisitor() { return Exec.exec("visitor", () -> visitorService.getVisitors());
    }

    @RequestMapping(value = "/visit", method = RequestMethod.GET)
    @ResponseBody
    public Result<Visitor> getVisit() { return Exec.exec("visit", () -> visitorService.getVisitors());
    }
}
