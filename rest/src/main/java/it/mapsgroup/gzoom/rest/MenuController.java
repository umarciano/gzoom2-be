package it.mapsgroup.gzoom.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.FolderMenu;
import it.mapsgroup.gzoom.service.MenuService;

/**
 * @author Fabio G. Strozzi
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public FolderMenu getUserPermission() {
        return Exec.exec("menu", () -> menuService.getMenu());
    }
}
