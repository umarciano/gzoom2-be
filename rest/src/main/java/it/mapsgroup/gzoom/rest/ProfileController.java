package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.model.Localization;
import it.mapsgroup.gzoom.model.Permissions;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.service.LocaleService;
import it.mapsgroup.gzoom.service.ProfileService;
import it.mapsgroup.gzoom.common.Exec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Fabio G. Strozzi
 */
@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProfileController {

    private final LocaleService localeService;
    private final ProfileService profileService;

    @Autowired
    public ProfileController(LocaleService localeService, ProfileService profileService) {
        this.localeService = localeService;
        this.profileService = profileService;
    }

    @RequestMapping(value = "/profile/i18n", method = RequestMethod.GET)
    @ResponseBody
    public Localization i18n(HttpServletRequest req) {
        return Exec.exec("profile-18n", () -> {
            Locale locale = localeService.getLocalization(req);
            return locale != null ? localeService.getLocalization(locale) : Localization.DEFAULT;
        });
    }

    @RequestMapping(value = "/profile/i18n/{user}", method = RequestMethod.GET)
    @ResponseBody
    public Localization i18n(HttpServletRequest req,@PathVariable(value = "user") String user) {
        return Exec.exec("profile-18n", () -> {
            Locale locale = localeService.getLocalization(req,user);
            return locale != null ? localeService.getLocalization(locale) : Localization.DEFAULT;
        });
    }

    @RequestMapping(value = "/profile/i18n/languages", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> languages() {
        return Exec.exec("get-languages", () -> localeService.getLanguages());
    }
    
    @RequestMapping(value = "/account/permissions", method = RequestMethod.GET)
    @ResponseBody
    public Permissions getUserPermission() {
        return Exec.exec("user-permission", () -> profileService.getUserPermission());
    }
}
