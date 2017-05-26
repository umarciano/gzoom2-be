package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.model.AbstractMenu;
import it.mapsgroup.gzoom.model.Localization;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.model.RootMenu;
import it.mapsgroup.gzoom.service.LocaleService;
import it.mapsgroup.gzoom.service.MenuService;
import it.mapsgroup.gzoom.service.ProfileService;
import it.mapsgroup.gzoom.common.Exec;
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
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public AbstractMenu getUserPermission(HttpServletRequest req) {
        return Exec.exec("menu", () -> menuService.getMenu());
    }
}
