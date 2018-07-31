package it.mapsgroup.gzoom.service;

import java.util.Locale;
import java.util.Map;

import it.mapsgroup.gzoom.model.Localization;
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

    @Autowired
    public LocaleService(Configuration config) {
        this.config = config;
    }

    public Locale getLocalization(HttpServletRequest req) {
        String header = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
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
}
