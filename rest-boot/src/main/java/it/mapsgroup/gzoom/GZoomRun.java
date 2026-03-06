package it.mapsgroup.gzoom;


import it.memelabs.smartnebula.spring.boot.config.PropertyApplicationContextInitializer;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

@ImportResource("classpath:/bootstrap-context.xml")
@SpringBootApplication(exclude = {
        JmxAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
})
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)

public class GZoomRun {
    private static final Logger LOG = getLogger(GZoomRun.class);

    public static void main(String[] args) throws Exception {
        LOG.info("logging.config [{}])", System.getProperty("logging.config"));

        new SpringApplicationBuilder(GZoomRun.class)
                .initializers(
                    // 1. Carica gzoom.properties base (dev/localhost)
                    new PropertyApplicationContextInitializer(
                        "file:" + System.getProperty("gzoom.conf.dir") + "/gzoom.properties"),
                    // 2. GZOOM multi-environment: se GZOOM_ENV è impostato, carica
                    //    gzoom-{GZOOM_ENV}.properties come overlay (sovrascrive le chiavi del base).
                    //    Es: GZOOM_ENV=collaudo → carica config/gzoom-collaudo.properties
                    //    Se il file non esiste, viene ignorato silenziosamente.
                    ctx -> {
                        String gzoomEnv = System.getProperty("GZOOM_ENV");
                        if (gzoomEnv != null && !gzoomEnv.trim().isEmpty()) {
                            String overlayPath = System.getProperty("gzoom.conf.dir")
                                    + "/gzoom-" + gzoomEnv.trim() + ".properties";
                            FileSystemResource overlayResource = new FileSystemResource(overlayPath);
                            if (overlayResource.exists()) {
                                try {
                                    ctx.getEnvironment().getPropertySources()
                                        .addFirst(new ResourcePropertySource(overlayPath, overlayResource));
                                    LOG.info("[GZoomRun] GZOOM_ENV={}: caricato overlay {}", gzoomEnv, overlayPath);
                                } catch (Exception e) {
                                    LOG.warn("[GZoomRun] Errore nel caricamento dell'overlay {}: {}", overlayPath, e.getMessage());
                                }
                            } else {
                                LOG.warn("[GZoomRun] GZOOM_ENV={}: file overlay non trovato: {}", gzoomEnv, overlayPath);
                            }
                        } else {
                            LOG.info("[GZoomRun] GZOOM_ENV non impostato, uso gzoom.properties base");
                        }
                    }
                )
                .run(args);
    }

}
