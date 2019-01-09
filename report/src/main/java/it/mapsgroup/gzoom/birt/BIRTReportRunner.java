package it.mapsgroup.gzoom.birt;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.core.internal.registry.RegistryProviderFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import it.mapsgroup.gzoom.querydsl.dto.ReportParam;
import it.mapsgroup.gzoom.querydsl.dto.ReportParams;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@Qualifier("birt")
public class BIRTReportRunner implements ReportRunner {
	public static final String BIRT_PARAMETERS = "birtParameters";

	private Logger logger = getLogger(BIRTReportRunner.class);

	private String reportTempDirectory;

	private IReportEngine birtReportEngine = null;

	private final BirtConfig config;

	@Autowired
	public BIRTReportRunner(BirtConfig config) {
		this.config = config;
	}

	/**
	 * Starts up and configures the BIRT Report Engine
	 */
	@PostConstruct
	@SuppressWarnings("unchecked")
	public void startUp() {
		if (StringUtils.isEmpty(config.getBirtReportInputDir()))
			throw new RuntimeException("Cannot start application since birt report input directory was not specified.");
		try {
			// String birtLoggingDirectory =
			// env.getProperty("birt.logging.directory") == null ?
			// DEFAULT_LOGGING_DIRECTORY :
			// env.getProperty("birt.logging.directory");
			// Level birtLoggingLevel = env.getProperty("birt.logging.level") ==
			// null ? Level.SEVERE :
			// Level.parse(env.getProperty("birt.logging.level"));
			EngineConfig engineConfig = new EngineConfig();
			logger.info("BIRT LOG DIRECTORY SET TO : {}", config.getBirtLoggingDirectory());
			logger.info("BIRT LOGGING LEVEL SET TO {}", config.getBirtLoggingLevel());

			engineConfig.setLogConfig(config.getBirtLoggingDirectory(), config.getBirtLoggingLevel());
			engineConfig.setLogger(java.util.logging.Logger.getLogger(BIRTReportRunner.class.getName()));

			engineConfig.setResourcePath(config.getBirtReportInputDir());

			// Required due to a bug in BIRT that occurs in calling Startup
			// after the Platform has already been started up
			RegistryProviderFactory.releaseDefault();
			Platform.startup(engineConfig);

			// data source
			if (!StringUtils.isEmpty(config.getOdaUrl())) {
				logger.info("Setting data source url: {}", config.getOdaUrl());
				logger.info("Setting data source driver: {}", config.getOdaDriverClass());
				engineConfig.getAppContext().put("odaURL", config.getOdaUrl());
				engineConfig.getAppContext().put("odaDriverClass", config.getOdaDriverClass());
				engineConfig.getAppContext().put("odaUser", config.getOdaUser());
				engineConfig.getAppContext().put("odaPassword", config.getOdaPassword());
				engineConfig.getAppContext().put("odaDialect", config.getOdaDialect());
			}

			IReportEngineFactory reportEngineFactory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			birtReportEngine = reportEngineFactory.createReportEngine(engineConfig);

		} catch (BirtException e) {
			// TODO add logging aspect and find out how to log a platform
			// startup problem from this catch block, if possible, using the
			// aspect.
			// Possibly rethrow the exception here and catch it in the aspect.
			logger.error("Birt Startup Error: {}", e.getMessage());
		}

		reportTempDirectory = config.getBirtTempFileOutputDir();
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
		String reportDirectory = config.getBirtReportInputDir();
		Path birtReport = Paths.get(reportDirectory + File.separator + reportName + File.separator + reportName + ".rptdesign");
		if (!Files.isReadable(birtReport))
			throw new RuntimeException("Report " + reportName + " either did not exist or was not writable.");

		return birtReport.toFile();
	}

	/**
	 * This method creates and executes the report task, the main responsibility
	 * of the entire Report Service. This method is key to enabling pagination
	 * for the BIRT report. The IRunTask run task is created and then used to
	 * generate an ".rptdocument" binary file. This binary file is then read by
	 * the separately created IRenderTask render task. The render task renders
	 * the binary document as a binary PDF output stream which is then returned
	 * from the method.
	 * <p>
	 *
	 * @param birtReport
	 *            the report object created at the controller to hold the data
	 *            of the report request.
	 * @return Returns a ByteArrayOutputStream of the PDF bytes generated by the
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ReportHandler runReport(Report birtReport) {
		OutputStream byteArrayOutputStream;
		File rptDesignFile;
		File reportContentTempFile;

		// get the path to the report design file
		try {
			rptDesignFile = getReportFromFilesystem(birtReport.getName());
		} catch (Exception e) {
			logger.error("Error while loading rptdesign: {}.", e.getMessage());
			throw new RuntimeException("Could not find report");
		}

		// process any additional parameters
		// Map<String, String> parsedParameters =
		// parseParametersAsMap(birtReport.getParameters());
		Map<String, Object> reportParameters = birtReport.getParameters();

		// byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			reportContentTempFile = new File(
					reportTempDirectory + File.separator + "birt_report_temp_file_" + birtReport.getTaskId() + ".tmp");
			reportContentTempFile.deleteOnExit();
			byteArrayOutputStream = new FileOutputStream(reportContentTempFile);

			IReportRunnable reportDesign = birtReportEngine.openReportDesign(rptDesignFile.getPath());

			// ----------------
			// TODO ASSUNTINA
			IGetParameterDefinitionTask task = birtReportEngine.createGetParameterDefinitionTask(reportDesign);
			Collection<?> params = task.getParameterDefns(true);
			logger.info("---> params", params);
			this.conversionParam(params);
			// ----------------

			// setting locale
			// see
			// https://stackoverflow.com/questions/25281571/birt-is-not-finding-properties-file-containing-localization-at-runtime-servlet
			try {
				reportDesign.getDesignHandle().setStringProperty("locale", birtReport.getReportLocale().toString());
			} catch (SemanticException e) {
				logger.error("error", e);
				throw new RuntimeException(e);
			}
			IRunTask runTask = birtReportEngine.createRunTask(reportDesign);

			runTask.setLocale(birtReport.reportLocale);

			logger.info("ResourcePath: {}", birtReportEngine.getConfig().getResourcePath());

			runTask.getAppContext().put(BIRT_PARAMETERS, reportParameters);
			if (reportParameters.size() > 0) {
				for (Map.Entry<String, Object> entry : reportParameters.entrySet()) {
					runTask.setParameterValue(entry.getKey(), entry.getValue());
				}
			}

			runTask.validateParameters();

			String rptdocument = reportTempDirectory + File.separator + "generated_" + birtReport.getTaskId()
					+ ".rptdocument";
			runTask.run(rptdocument);

			IReportDocument reportDocument = birtReportEngine.openReportDocument(rptdocument);
			IRenderTask renderTask = birtReportEngine.createRenderTask(reportDocument);
			renderTask.setLogger(java.util.logging.Logger.getLogger(IRenderTask.class.getSimpleName()));
			renderTask.setProgressMonitor(birtReport.getBirtServiceProgress());
			birtReport.getBirtServiceProgress().setTask(renderTask);

			PDFRenderOption pdfRenderOption = new PDFRenderOption();
			pdfRenderOption.setOption(IPDFRenderOption.REPAGINATE_FOR_PDF, new Boolean(true));
			pdfRenderOption.setOutputFormat("pdf");
			pdfRenderOption.closeOutputStreamOnExit(true);
			renderTask.setRenderOption(pdfRenderOption);
			pdfRenderOption.setOutputStream(byteArrayOutputStream);

			// EXCELRenderOption excelRenderOption= new EXCELRenderOption();
			// excelRenderOption.setEnableMultipleSheet(true);
			// excelRenderOption.setOutputFormat("xls");
			// excelRenderOption.setOutputStream(byteArrayOutputStream);
			// renderTask.setRenderOption(excelRenderOption);

			renderTask.render();
			renderTask.close();
		} catch (EngineException e) {
			logger.error("Error while running report task: {}.", e.getMessage());
			// TODO add custom message to thrown exception
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Error while running report task: {}. Cannot create temporary file", e.getMessage());
			// TODO add custom message to thrown exception
			throw new RuntimeException(e);
		}

		return new ReportHandler(reportContentTempFile);
	}

	/**
	 * Takes a String of parameters started by '?', delimited by '&', and with
	 * keys and values split by '=' and returnes a Map of the keys and values in
	 * the String.
	 *
	 * @param reportParameters
	 *            a String from a HTTP request URL
	 * @return a map of parameters with Key,Value entries as strings
	 */
	@Deprecated
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

	
	/**
	 * Leggo la lista di parametri dal report
	 * @param birtReport
	 * @return
	 */
	public ReportParams getReportParams(Report birtReport) {
		ReportParams reportParameters = new ReportParams();
		File rptDesignFile;

		// get the path to the report design file
		try {
			rptDesignFile = getReportFromFilesystem(birtReport.getName());
		} catch (Exception e) {
			logger.error("Error while loading rptdesign: {}.", e.getMessage());
			throw new RuntimeException("Could not find report");
		}

		try {
			IReportRunnable reportDesign = birtReportEngine.openReportDesign(rptDesignFile.getPath());
			IGetParameterDefinitionTask task = birtReportEngine.createGetParameterDefinitionTask(reportDesign);
			
			Collection<?> params = task.getParameterDefns(true);
			logger.info("---> params", params);
			reportParameters.setParams(this.conversionParam(params));

		} catch (EngineException e) {
			logger.error("Error while running report task: {}.", e.getMessage());
			// TODO add custom message to thrown exception
			throw new RuntimeException(e);
		}
			
		return reportParameters;
	}
	

	public List<ReportParam> conversionParam(Collection<?> c) {
		List<ReportParam> parmDetails = new ArrayList<ReportParam>();
    	Iterator<?> iterator = c.iterator();
    	while(iterator.hasNext()) {
    		IParameterDefnBase param = (IParameterDefnBase) iterator.next( );
    		if (param instanceof IParameterGroupDefn) {
    			//attualmente noi non abbiamo GRUPPI
	    		IParameterGroupDefn group = (IParameterGroupDefn) param;
	    		logger.info("Parameter Group: ", group.getName() );
	    		Iterator<?> i2 = group.getContents( ).iterator( );
	    		while (i2.hasNext()) {
	    			IScalarParameterDefn scalar = (IScalarParameterDefn) i2.next( );
		    		parmDetails.add(loadParameterDetails(scalar, group));
	    		}
    		} else {
	    		IScalarParameterDefn scalar = (IScalarParameterDefn) param;
	    		parmDetails.add(loadParameterDetails(scalar, null));	
    		}    		
    	}    	
    	return parmDetails;
    }

	//TODO decidere cosa mi serve
	private ReportParam loadParameterDetails(IScalarParameterDefn scalar, IParameterGroupDefn group) {
		ReportParam param = new ReportParam();
		if (!scalar.isHidden()) {
			param.setParamName(scalar.getName());
			param.setMandatory(scalar.isRequired());
			param.setParamDefault(scalar.getDefaultValue());
			param.setParamType("INPUT"); //??
			return param;
		}
		return null;
		/*
		if (group == null) {
			parameter.put("Parameter Group", "Default");
		} else {
			parameter.put("Parameter Group", group.getName());
		}
		parameter.put("Name", scalar.getName());
		parameter.put("Display Name", scalar.getDisplayName());
		parameter.put("Display Format", scalar.getDisplayFormat());

		parameter.put("Hidden", scalar.isHidden());
		parameter.put("Allow Blank", scalar.allowBlank());
		parameter.put("Allow Null", scalar.allowNull());
		parameter.put("Required", scalar.isRequired());
		parameter.put("Default Value", scalar.getDefaultValue());
		
		switch (scalar.getControlType()) {
		case IScalarParameterDefn.TEXT_BOX:
			parameter.put("Type", "Text Box");
			break;
		case IScalarParameterDefn.LIST_BOX:
			parameter.put("Type", "List Box");
			break;
		case IScalarParameterDefn.RADIO_BUTTON:
			parameter.put("Type", "List Box");
			break;
		case IScalarParameterDefn.CHECK_BOX:
			parameter.put("Type", "List  Box");
			break;
		default:
			parameter.put("Type", "Text Box");
			break;
		}

		switch (scalar.getDataType()) {
		case IScalarParameterDefn.TYPE_STRING:
			parameter.put("Data Type", "String");
			break;
		case IScalarParameterDefn.TYPE_FLOAT:
			parameter.put("Data Type", "Float");
			break;
		case IScalarParameterDefn.TYPE_DECIMAL:
			parameter.put("Data Type", "Decimal");
			break;
		case IScalarParameterDefn.TYPE_DATE_TIME:
			parameter.put("Data Type", "Date Time");
			break;
		case IScalarParameterDefn.TYPE_BOOLEAN:
			parameter.put("Data Type", "Boolean");
			break;
		default:
			parameter.put("Data Type", "Any");
			break;
		}

		ScalarParameterHandle parameterHandle = (ScalarParameterHandle) scalar.getHandle();
		parameter.put("Default Value", scalar.getDefaultValue());
		parameter.put("Prompt Text", scalar.getPromptText());
		parameter.put("Data Set Expression", parameterHandle.getValueExpr());
		
		
		Iterator<String> iter = parameter.keySet().iterator();
		String mapString = "";
		while (iter.hasNext()) {
			String name = (String) iter.next();
			mapString += name + " = " + parameter.get(name) + ", ";
		}
		logger.info("====================== Parameter = " + scalar.getName() + ": " + mapString);*/

	}

}
