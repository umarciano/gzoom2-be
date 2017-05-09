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
public class ConfigurationImpl implements Configuration, SecurityConfiguration, OfBizClientConfig {
    private static final Logger LOG = getLogger(ConfigurationImpl.class);

    // Localizations
    private final Path localeDirPath;
    private final List<String> translationResources;
    private volatile Map<String, Map<String, Object>> localizations;

    // REST
    private final int tokenExpiryMinutes;
    private final String restPath;

    // AD
    private final boolean adEnabled;
    private final String adHost;
    private final int adPort;
    private final String adDomain;
    private final MessageFormat adUserFormat;
    private final MessageFormat adUserFilter;
    private final String adUserRoot;
    private final String[] adUserAttributes;
    private final int adTimeout;

    // non-REST properties
    private final int deadlineDays;


    private final String configurationPath;

    private final String ofbizServerXmlrpcUrl;

    @Autowired
    public ConfigurationImpl(Environment env) {
        // localization
        this.localeDirPath = Paths.get(env.getProperty("gzoom.conf.dir") + "/locales");
        this.translationResources = Arrays.asList("/lmm/locales/it.json", "/lmm/locales/en.json");
        this.localizations = initLocalization();

        // rest properties
        this.restPath = env.getProperty("rest.path", "../rest");
        this.tokenExpiryMinutes = env.getProperty("rest.token.expiry.minutes", Integer.class, 43200);

        // AD
        this.adEnabled = env.getProperty("ad.enabled", Boolean.class, false);
        this.adHost = env.getProperty("ad.host", "");
        this.adPort = env.getProperty("ad.port", Integer.class, 389);
        this.adDomain = env.getProperty("ad.domain");
        this.adTimeout = env.getProperty("ad.timeout", Integer.class, 3000);
        this.adUserFormat = isBlank(env.getProperty("ad.user.format", "")) ? null : new MessageFormat(env.getProperty("ad.user.format"));
        this.adUserFilter = isBlank(env.getProperty("ad.user.filter", "")) ? null : new MessageFormat(env.getProperty("ad.user.filter"));
        this.adUserAttributes = splitToArray(env.getProperty("ad.user.attributes", "distinguishedName,cn,name,uid,sn,memberOf,samaccountname,userPrincipalName"), ',');
        this.adUserRoot = emptyToNull(env.getProperty("ad.user.root", ""));

        if (adEnabled) {
            if (isBlank(adHost)) {
                LOG.error("AD host is not set but AD support is enabled");
                throw new IllegalArgumentException("AD host is not set but AD support is enabled");
            }

            if (isBlank(adDomain)) {
                LOG.error("AD domain is not set but AD support is enabled");
                throw new IllegalArgumentException("AD domain is not set but AD support is enabled");
            }

            if (this.adUserFormat == null) {
                LOG.error("AD user format is not set but AD support is enabled");
                throw new IllegalArgumentException("AD user format is not set but AD support is enabled");
            }

            if (this.adUserFilter == null) {
                LOG.error("AD user filter is not set but AD support is enabled");
                throw new IllegalArgumentException("AD user filter is not set but AD support is enabled");
            }
        }

        this.deadlineDays = env.getProperty("deadline.days", Integer.class, 5);


        this.configurationPath = env.getProperty("gzoom.conf.dir");

        this.ofbizServerXmlrpcUrl = env.getProperty("ofbiz.server.xmlrpc.url");
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
    public boolean isADEnabled() {
        return adEnabled;
    }

    @Override
    public String getADHost() {
        return adHost;
    }

    @Override
    public int getADPort() {
        return adPort;
    }

    @Override
    public String getADDomain() {
        return adDomain;
    }

    @Override
    public MessageFormat getADUser() {
        return adUserFormat;
    }

    @Override
    public MessageFormat getADUserFilter() {
        return adUserFilter;
    }

    @Override
    public String getADUserRoot() {
        return adUserRoot;
    }

    @Override
    public String[] getADUserAttributes() {
        return adUserAttributes;
    }

    @Override
    public int getADTimeout() {
        return adTimeout;
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


}
