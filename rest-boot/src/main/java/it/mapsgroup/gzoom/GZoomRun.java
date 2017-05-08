package it.mapsgroup.gzoom;


import it.memelabs.smartnebula.spring.boot.config.PropertyApplicationContextInitializer;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.StringUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@ImportResource("classpath:/bootstrap-context.xml")
@SpringBootApplication
@EnableConfigurationProperties
public class GZoomRun {
    private static final Logger LOG = getLogger(GZoomRun.class);

    public static void main(String[] args) throws Exception {
        if (StringUtils.isEmpty(System.getProperty("logging.config")))
            System.setProperty("logging.config", System.getProperty("lmm.conf.dir") + "/logback.xml");
        LOG.info("logging.config [{}])", System.getProperty("logging.config"));
        //SpringApplication.run(LmmRun.class, args);
        new SpringApplicationBuilder(GZoomRun.class)
                .initializers(new PropertyApplicationContextInitializer("file:" + System.getProperty("lmm.conf.dir") + "/lmm.properties"))
                .run(args);
    }


}
