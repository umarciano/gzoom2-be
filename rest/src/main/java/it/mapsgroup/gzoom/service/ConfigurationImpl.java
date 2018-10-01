package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.ofbiz.client.OfBizClientConfig;
import it.mapsgroup.gzoom.security.model.SecurityConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Strings.emptyToNull;
import static it.mapsgroup.commons.Strings.splitToArray;
import static it.mapsgroup.gzoom.common.JsonLocalizationReader.readClasspathResources;
import static it.mapsgroup.gzoom.common.JsonLocalizationReader.readDirectory;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Configuration implementation.
 */
public class ConfigurationImpl implements Configuration, SecurityConfiguration, OfBizClientConfig, GzoomReportClientConfig {
    private static final Logger LOG = getLogger(ConfigurationImpl.class);

    // Localizations
    private final Path localeDirPath;
    private final List<String> translationResources;
    private volatile Map<String, Map<String, Object>> localizations;

    // REST
    private final int tokenExpiryMinutes;
    private final String restPath;


    // non-REST properties
    private final int deadlineDays;


    private final String configurationPath;

    private final String ofbizServerXmlrpcUrl;

    private final String gzoomServerReportUrl;

    @Autowired
    public ConfigurationImpl(Environment env) {
        // localization
        this.localeDirPath = Paths.get(env.getProperty("gzoom.conf.dir") + "/locales");
        this.translationResources = Arrays.asList("/lmm/locales/it.json", "/lmm/locales/en.json");
        this.localizations = initLocalization();

        // rest properties
        this.restPath = env.getProperty("rest.path", "../rest");
        this.tokenExpiryMinutes = env.getProperty("rest.token.expiry.minutes", Integer.class, 43200);


        this.deadlineDays = env.getProperty("deadline.days", Integer.class, 5);


        this.configurationPath = env.getProperty("gzoom.conf.dir");

        this.ofbizServerXmlrpcUrl = env.getProperty("ofbiz.server.xmlrpc.url");
        
        this.gzoomServerReportUrl = env.getProperty("gzoom.server.report.url");
    }


    @Override
    public int getTokenExpiryMinutes() {
        return tokenExpiryMinutes;
    }

    @Override
    public String getRestPath() {
        return restPath;
    }

    @Override
    public int getDeadlineDays() {
        return deadlineDays;
    }

    @Override
    public boolean isLocaleSupported(Locale locale) {
        return localizationOf(locale) != null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getTranslations(Locale locale) {
        Map<String, Object> map = localizationOf(locale);
        if (map == null)
            return null;
        return (Map<String, String>) map.get("translations");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getFormats(Locale locale) {
        Map<String, Object> map = localizationOf(locale);
        if (map == null)
            return null;
        return (Map<String, Object>) map.get("formats");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCalendarLocale(Locale locale) {
        Map<String, Object> map = localizationOf(locale);
        if (map == null)
            return null;
        return (Map<String, Object>) map.get("calendarLocale");
    }

    private Map<String, Object> localizationOf(Locale locale) {
        // copy reference, this will synchronize writing of the volatile reference
        Map<String, Map<String, Object>> map = localizations;

        // attempt #1: using the locale complete formatting
        if (map.containsKey(locale.toString()))
            return map.get(locale.toString());
        // attempt #2: with language and country only
        // this assumes the passed locale used variant, script or extensions that are
        // not considered here
        String l = new Locale(locale.getLanguage(), locale.getCountry()).toString();
        if (map.containsKey(l))
            return map.get(l);
        // attempt #3: with the language only
        if (map.containsKey(locale.getLanguage()))
            return map.get(locale.getLanguage());

        return null;
    }

    private Map<String, Map<String, Object>> initLocalization() {
        Map<String, Map<String, Object>> cpLoc = readClasspathResources(translationResources);
        Map<String, Map<String, Object>> dirLoc = readDirectory(localeDirPath);
        cpLoc.putAll(dirLoc);
        return cpLoc;
    }


    public String getConfigurationPath() {
        return configurationPath;
    }

    @Override
    public URL getServerXmlRpcUrl() {
        try {
            return new URL(ofbizServerXmlrpcUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("ofbizServerXmlrpcUrl is wrong");
        }
    }

    @Override
    public URL getServerReportUrl() {
        try {
            return new URL(gzoomServerReportUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("gzoomServerReportUrl is wrong");
        }
    }
}
