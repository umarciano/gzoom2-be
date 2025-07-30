package it.mapsgroup.gzoom.birt;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * @author Andrea Fossi.
 */
@Component
public class BirtConfig {

    public static final String DEFAULT_LOGGING_DIRECTORY = "defaultBirtLoggingDirectory/";

    private final String birtLoggingDirectory;
    private final String birtTempFileOutputDir;
    private final String birtReportInputDir;
    private final String birtReportOutputDir;
    private final Level birtLoggingLevel;
    private final String odaUrl;
    private final String odaDriverClass;
    private final String odaPassword;
    private final String odaUser;
    private final String odaDialect;
    private final String developerBirtPath;
    private final String odaIsolationMode;


    public BirtConfig(Environment env) {
        birtLoggingDirectory = "C:/GZOOM/workspace/gzoom2temp/birt/logs";
        birtLoggingLevel = Level.parse("FINE");
        birtTempFileOutputDir = "C:/GZOOM/workspace/gzoom2temp/birt/tmp";
        birtReportInputDir = "C:/GZOOM/workspace/gzoom2-report";
        birtReportOutputDir = "C:/GZOOM/workspace/gzoom2temp/birt/report";
        odaUrl = "jdbc:postgresql://localhost:5432/cardarelli";
        odaDriverClass = "org.postgresql.Driver";
        odaPassword = "postgres";
        odaUser = "postgres";
        odaDialect = "postgresql";
        odaIsolationMode = null;
        developerBirtPath = null;
    }

    public String getBirtLoggingDirectory() {
        return birtLoggingDirectory;
    }

    public String getBirtTempFileOutputDir() {
        return birtTempFileOutputDir;
    }

    public String getBirtReportInputDir() {
        return birtReportInputDir;
    }

    public Level getBirtLoggingLevel() {
        return birtLoggingLevel;
    }

    public String getOdaUrl() {
        return odaUrl;
    }

    public String getOdaDriverClass() {
        return odaDriverClass;
    }

    public String getOdaPassword() {
        return odaPassword;
    }

    public String getOdaUser() {
        return odaUser;
    }

    public String getBirtReportOutputDir() {
        return birtReportOutputDir;
    }

    public String getOdaDialect() {
        return odaDialect;
    }

    public String getOdaIsolationMode() {return odaIsolationMode; }
    
    public String getDeveloperBirtPath() { return  developerBirtPath; }
}
