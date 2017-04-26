package it.memelabs.smartnebula.spring.boot.config;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class PropertyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger LOG = getLogger(PropertyApplicationContextInitializer.class);
    private final String path;

    public PropertyApplicationContextInitializer(String path) {
        this.path = path;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            applicationContext.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource(path));
            LOG.info("Loaded configuration from {}", path);
        } catch (IOException e) {
            LOG.error("Error loading configuration from {}", path);
            throw new RuntimeException(e);
        }
    }
}