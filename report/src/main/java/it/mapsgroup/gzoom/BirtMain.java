package it.mapsgroup.gzoom;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.slf4j.Logger;

import java.util.logging.Level;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * @author Andrea Fossi.
 */
public class BirtMain {
    private static final Logger LOG = getLogger(BirtMain.class);


    public static void main(String... args) {
        IReportEngine engine = null;
        EngineConfig config = null;

        try {
            config = new EngineConfig();
            config.setBIRTHome("/Users/anfo/projects/gzoom/report-designer/birt-runtime-4.6.0-20160607/ReportEngine/lib");
            config.setLogConfig("/Users/anfo/projects/gzoom/logs", Level.FINEST);
            //config.setResourcePath();
            Platform.startup(config);
            IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = factory.createReportEngine(config);


        } catch (Exception e) {
            LOG.error("Generic error", e);
        }
    }
}
