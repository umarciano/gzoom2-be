package it.mapsgroup.gzoom;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.core.internal.registry.RegistryProviderFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import static org.slf4j.LoggerFactory.getLogger;


@Service
@Qualifier("birt")
public class BIRTReportRunner implements ReportRunner {
    private static final String DEFAULT_LOGGING_DIRECTORY = "defaultBirtLoggingDirectory/";
    private Logger logger = getLogger(BIRTReportRunner.class);

    private static String reportOutputDirectory;

    private IReportEngine birtReportEngine = null;

    @Autowired
    private Environment env;

    /**
     * Starts up and configures the BIRT Report Engine
     */
    @PostConstruct
    public void startUp() {
        if (env.getProperty("birt_report_input_dir") == null)
            throw new RuntimeException("Cannot start application since birt report input directory was not specified.");
        try {
            String birtLoggingDirectory = env.getProperty("birt_logging_directory") == null ? DEFAULT_LOGGING_DIRECTORY : env.getProperty("birt_logging_directory");
            Level birtLoggingLevel = env.getProperty("birt_logging_level") == null ? Level.SEVERE : Level.parse(env.getProperty("birt_logging_level"));
            EngineConfig engineConfig = new EngineConfig();
            logger.info("BIRT LOG DIRECTORY SET TO : {}", birtLoggingDirectory);
            logger.info("BIRT LOGGING LEVEL SET TO {}", birtLoggingLevel);

            engineConfig.setLogConfig(birtLoggingDirectory, birtLoggingLevel);
            engineConfig.setLogger(java.util.logging.Logger.getLogger(BIRTReportRunner.class.getName()));

            engineConfig.setResourcePath(env.getProperty("birt_report_input_dir"));
            //engineConfig.setLogger();

            // Required due to a bug in BIRT that occurs in calling Startup after the Platform has already been started up
            RegistryProviderFactory.releaseDefault();
            Platform.startup(engineConfig);

            // data source
            //engineConfig.setProperty("odaURL", "jdbc:mysql://localhost/gzoom?autoReconnect=true&amp;useOldAliasMetadataBehavior=true&amp;generateSimpleParameterMetadata=true");
            //engineConfig.setProperty("odaDriverClass", "com.mysql.jdbc.Driver");
            //engineConfig.setProperty("odaPassword", "root");
            //engineConfig.setProperty("odaUser", "root");

            if (!StringUtils.isEmpty(env.getProperty("oda_url"))) {
                logger.info("Setting data source url: {}", env.getProperty("oda_url"));
                logger.info("Setting data source driver: {}", env.getProperty("oda_driver_class"));
                engineConfig.getAppContext().put("odaURL", env.getProperty("oda_url"));
                engineConfig.getAppContext().put("odaDriverClass", env.getProperty("oda_driver_class"));
                engineConfig.getAppContext().put("odaPassword", env.getProperty("oda_password"));
                engineConfig.getAppContext().put("odaUser", env.getProperty("oda_user"));
            }

            IReportEngineFactory reportEngineFactory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            birtReportEngine = reportEngineFactory.createReportEngine(engineConfig);

        } catch (BirtException e) {
            // TODO add logging aspect and find out how to log a platform startup problem from this catch block, if possible, using the aspect.
            // Possibly rethrow the exception here and catch it in the aspect.
            logger.error("Birt Startup Error: {}", e.getMessage());
        }

        reportOutputDirectory = env.getProperty("birt_temp_file_output_dir");
    }

    /**
     * Shuts down the BIRT Report Engine
     */
    @PreDestroy
    public void shutdown() {
        birtReportEngine.destroy();
        RegistryProviderFactory.releaseDefault();
        Platform.shutdown();
    }

    public File getReportFromFilesystem(String reportName) throws RuntimeException {
        String reportDirectory = env.getProperty("birt_report_input_dir");
        Path birtReport = Paths.get(reportDirectory + File.separator + reportName + ".rptdesign");
        if (!Files.isReadable(birtReport))
            throw new RuntimeException("Report " + reportName + " either did not exist or was not writable.");

        return birtReport.toFile();
    }

    /**
     * This method creates and executes the report task, the main responsibility
     * of the entire Report Service.
     * This method is key to enabling pagination for the BIRT report. The IRunTask run task
     * is created and then used to generate an ".rptdocument" binary file.
     * This binary file is then read by the separately created IRenderTask render
     * task. The render task renders the binary document as a binary PDF output
     * stream which is then returned from the method.
     * <p>
     *
     * @param birtReport the report object created at the controller to hold the data of the report request.
     * @return Returns a ByteArrayOutputStream of the PDF bytes generated by the
     */
    @Override
    public ByteArrayOutputStream runReport(Report birtReport) {

        ByteArrayOutputStream byteArrayOutputStream;
        File rptDesignFile;

        // get the path to the report design file
        try {
            rptDesignFile = getReportFromFilesystem(birtReport.getName());
        } catch (Exception e) {
            logger.error("Error while loading rptdesign: {}.", e.getMessage());
            throw new RuntimeException("Could not find report");
        }

        // process any additional parameters
        Map<String, String> parsedParameters = parseParametersAsMap(birtReport.getParameters());

        byteArrayOutputStream = new ByteArrayOutputStream();
        try {


            IReportRunnable reportDesign = birtReportEngine.openReportDesign(rptDesignFile.getPath());

            //setting locale
            //see https://stackoverflow.com/questions/25281571/birt-is-not-finding-properties-file-containing-localization-at-runtime-servlet
            try {
                reportDesign.getDesignHandle().setStringProperty("locale", Locale.ITALIAN.toString());
            } catch (SemanticException e) {
                logger.error("error", e);
                throw new RuntimeException(e);
            }
            IRunTask runTask = birtReportEngine.createRunTask(reportDesign);

            //todo config locale
            runTask.setLocale(Locale.ITALIAN);

            logger.info("ResourcePath: {}", birtReportEngine.getConfig().getResourcePath());

            if (parsedParameters.size() > 0) {
                for (Map.Entry<String, String> entry : parsedParameters.entrySet()) {
                    runTask.setParameterValue(entry.getKey(), entry.getValue());
                }
            }


            runTask.validateParameters();

            String rptdocument = reportOutputDirectory + File.separator
                    + "generated" + File.separator
                    + birtReport.getName() + ".rptdocument";
            runTask.run(rptdocument);


            IReportDocument reportDocument = birtReportEngine.openReportDocument(rptdocument);
            IRenderTask renderTask = birtReportEngine.createRenderTask(reportDocument);
            renderTask.setProgressMonitor(new BirtServiceProgress());

            PDFRenderOption pdfRenderOption = new PDFRenderOption();
            pdfRenderOption.setOption(IPDFRenderOption.REPAGINATE_FOR_PDF, new Boolean(true));
            pdfRenderOption.setOutputFormat("pdf");
            pdfRenderOption.setOutputStream(byteArrayOutputStream);
            renderTask.setRenderOption(pdfRenderOption);

            renderTask.render();
            renderTask.close();
        } catch (EngineException e) {
            logger.error("Error while running report task: {}.", e.getMessage());
            // TODO add custom message to thrown exception
            throw new RuntimeException();
        }

        return byteArrayOutputStream;
    }

    /**
     * Takes a String of parameters started by '?', delimited by '&', and with
     * keys and values split by '=' and returnes a Map of the keys and values
     * in the String.
     *
     * @param reportParameters a String from a HTTP request URL
     * @return a map of parameters with Key,Value entries as strings
     */
    public Map<String, String> parseParametersAsMap(String reportParameters) {
        Map<String, String> parsedParameters = new HashMap<String, String>();
        String[] paramArray;
        if (reportParameters.isEmpty()) {
            throw new IllegalArgumentException("Report parameters cannot be empty");
        } else if (!reportParameters.startsWith("?") && !reportParameters.contains("?")) {
            throw new IllegalArgumentException("Report parameters must start with a question mark '?'!");
        } else {
            String noQuestionMark = reportParameters.substring(1, reportParameters.length());
            paramArray = noQuestionMark.split("&");
            for (String param : paramArray) {
                String[] paramGroup = param.split("=");
                if (paramGroup.length == 2) {
                    parsedParameters.put(paramGroup[0], paramGroup[1]);
                } else {
                    parsedParameters.put(paramGroup[0], "");
                }

            }
        }
        return parsedParameters;
    }
}
