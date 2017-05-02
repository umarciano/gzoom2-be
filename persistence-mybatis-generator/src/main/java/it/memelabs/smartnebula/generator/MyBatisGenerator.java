package it.memelabs.smartnebula.generator;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class MyBatisGenerator {

    public static void main(String[] args) throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException {
        boolean overwrite = true;
        List<String> warnings = new ArrayList<String>();


        InputStream configFile = MyBatisGenerator.class.getResourceAsStream("/GeneratorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        // create generated target directory if not exist
        makeTargetDirs(config);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        warnings.forEach(w -> System.out.println(w));
    }

    private static void makeTargetDirs(Configuration config) {
        if (config.getContexts() != null) {
            config.getContexts().forEach(context -> {
                if (context.getJavaClientGeneratorConfiguration() != null) {
                    makeDirs(context.getJavaClientGeneratorConfiguration().getTargetProject());
                }
                if (context.getJavaModelGeneratorConfiguration() != null) {
                    makeDirs(context.getJavaModelGeneratorConfiguration().getTargetProject());
                }
                if (context.getSqlMapGeneratorConfiguration() != null) {
                    makeDirs(context.getSqlMapGeneratorConfiguration().getTargetProject());
                }
            });
        }
    }

    private static void makeDirs(String path) {
        if (path != null && !path.isEmpty()) {
            new File(path).mkdirs();
        }
    }
}
