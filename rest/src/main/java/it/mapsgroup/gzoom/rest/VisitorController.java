package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Localization;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.Visitor;
import it.mapsgroup.gzoom.service.LocaleService;
import it.mapsgroup.gzoom.service.ProfileService;
import it.mapsgroup.gzoom.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Fabio G. Strozzi
 */
@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VisitorController {

    private VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @RequestMapping(value = "/visitor", method = RequestMethod.GET)
    @ResponseBody
    public Result<Visitor> i18n(HttpServletRequest req) {
        return Exec.exec("", () -> visitorService.getVisitors());
    }
}
