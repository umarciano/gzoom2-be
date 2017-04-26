package it.memelabs.smartnebula.lmm.rest;

import it.memelabs.smartnebula.lmm.model.Localization;
import it.memelabs.smartnebula.lmm.common.Exec;
import it.memelabs.smartnebula.lmm.service.LocaleService;
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
@RequestMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProfileController {

    private final LocaleService localeService;

    @Autowired
    public ProfileController(LocaleService localeService) {
        this.localeService = localeService;
    }

    @RequestMapping(value = "/i18n", method = RequestMethod.GET)
    @ResponseBody
    public Localization i18n(HttpServletRequest req) {
        return Exec.exec("profile-18n", () -> {
            Locale locale = localeService.getLocalization(req);
            return locale != null ? localeService.getLocalization(locale) : Localization.DEFAULT;
        });
    }
}
