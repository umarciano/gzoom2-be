package it.mapsgroup.gzoom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.*;

/**
 * Localization data structure.
 *
 * @author Fabio G. Strozzi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Localization {
    private String language;
    private Map<String, String> translations;
    private Map<String, Object> formats;
    private Map<String, Object> calendarLocale;

    public final static Localization DEFAULT =
            new Localization("default", unmodifiableMap(new HashMap<>()), unmodifiableMap(new HashMap<>()), unmodifiableMap(new HashMap<>()));

    public Localization(String language, Map<String, String> translations, Map<String, Object> formats, Map<String, Object> calendarLocale) {
        this.language = language;
        this.translations = translations;
        this.formats = formats;
        this.calendarLocale = calendarLocale;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public Map<String, Object> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, Object> formats) {
        this.formats = formats;
    }
    
    public Map<String, Object> getCalendarLocale() {
        return calendarLocale;
    }

    public void setCalendarLocale(Map<String, Object> calendarLocale) {
        this.calendarLocale = calendarLocale;
    }
}
