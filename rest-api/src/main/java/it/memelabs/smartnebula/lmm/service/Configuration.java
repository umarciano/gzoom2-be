package it.memelabs.smartnebula.lmm.service;

import java.util.Locale;
import java.util.Map;

public interface Configuration {

    /**
     * Tells whether some localization data (translations and formats) is provided for the given locale.
     *
     * @param locale The locale of the translation and formats.
     * @return True if locale is supported, false otherwise
     */
    boolean isLocaleSupported(Locale locale);

    /**
     * Retrieves the translation map for a certain locale or null if locale is not supported.
     *
     * @param locale The locale
     * @return The translation map for a certain locale or null if locale is not supported.
     */
    Map<String, String> getTranslations(Locale locale);

    /**
     * Retrieves the formats of the given locale or null if locale is not supported.
     *
     * @param locale The locale
     * @return The formats of the given locale or null if locale is not supported.
     */
    Map<String, Object> getFormats(Locale locale);

    /**
     * Retrieves the URL path to the REST API services.
     *
     * @return The URL path to the REST API services.
     */
    String getRestPath();

    /**
     * The number of days before the threshold date that identify the deadline date.
     *
     * @return The number of date before the threshold that identify the deadline date
     */
    int getDeadlineDays();
}
