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


    public BirtConfig(Environment env) {
        birtLoggingDirectory = env.getProperty("birt_logging_directory", DEFAULT_LOGGING_DIRECTORY);
        birtLoggingLevel = Level.parse(env.getProperty("birt_logging_level", Level.SEVERE.getName()));
        birtTempFileOutputDir = env.getProperty("birt_temp_file_output_dir");
        birtReportInputDir = env.getProperty("birt_report_input_dir");
        birtReportOutputDir = env.getProperty("birt_report_output_dir");
        odaUrl = env.getProperty("oda_url");
        odaDriverClass = env.getProperty("oda_driver_class");
        odaPassword = env.getProperty("oda_password");
        odaUser = env.getProperty("oda_user");
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
}
