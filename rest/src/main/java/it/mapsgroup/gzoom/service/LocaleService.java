package it.mapsgroup.gzoom.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.mapsgroup.gzoom.model.Localization;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginDao;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Localization service.
 *
 * @author Fabio G. Strozzi
 */
@Service
public class LocaleService {
    private final Configuration config;
    private final UserLoginDao userLoginDao;

    @Autowired
    public LocaleService(Configuration config, UserLoginDao userLoginDao ) {
        this.config = config;
        this.userLoginDao = userLoginDao;
    }

    public Locale getLocalization(HttpServletRequest req) {
        return this.getLocalization(req,null);
    }

    public Locale getLocalization(HttpServletRequest req, String user) {
        String header = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);

        if(user!=null) {
            UserLogin profile = userLoginDao.getUserLogin(user);
            if (profile!=null && profile.getLastLocale()!=null && !profile.getLastLocale().equals("")){
                String [] locale = profile.getLastLocale().split("_");
                return new Locale(locale[0],locale[1]);
            }
        }

        return header != null ? req.getLocale() : null;
    }

    public Localization getLocalization(Locale locale) {
        if (!config.isLocaleSupported(locale)) {
            return Localization.DEFAULT;
        }

        Map<String, String> trans = config.getTranslations(locale);
        Map<String, Object> formats = config.getFormats(locale);
        Map<String, Object> calendarLocale = config.getCalendarLocale(locale);

        return new Localization(locale.getLanguage(), trans, formats, calendarLocale);
    }

    public Result<String> getLanguages() {
        List<String> list = config.getLanguages();
        return new Result<>(list, list.size());
    }

    public String getLanguageType() {
        return config.getLanguageType();
    }
}
