package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import it.mapsgroup.gzoom.querydsl.generator.patch.MetaDataExporter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class QueryDslGenerator {
    private static final Logger LOG = getLogger(QueryDslGenerator.class);

    public static final String TARGET_FOLDER = "/Users/anfo/projects/gzoom/gzoom2/persistence-query-dsl/src/generated/java";

    private Connection getConnection() throws SQLException {
    /*    return DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle-maps.maps1.mapsengineering.com)(PORT=1521))" +
                "(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME= devdb.maps1.mapsengineering.com)))", "ANFO", "@4ndr34_77");*/
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ltprod", "lmm", "lmm");
        //return DriverManager.getConnection("jdbc:mysql://localhost/gzoom_lite", "root", "root");
        return DriverManager.getConnection("jdbc:mysql://localhost/gzoom_lite?autoReconnect=true&amp;useOldAliasMetadataBehavior=true&amp;generateSimpleParameterMetadata=true", "root", "root");
    }


    public static void main(String... args) throws SQLException, InterruptedException {
        new QueryDslGenerator().generate();
        Thread.sleep(1000);
    }


    public void generate() throws SQLException {
        MetaDataExporter exporter = new MetaDataExporter();
        exporter.setPackageName("it.mapsgroup.gzoom.querydsl.dto");
        exporter.setTargetFolder(new File(TARGET_FOLDER));
        BeanSerializer beanSerializer = new BeanSerializer();
        beanSerializer.setAddToString(true);
        exporter.setBeanSerializer(beanSerializer);
        exporter.setColumnAnnotations(true);
        Configuration configuration = new Configuration(new PostgreSQLTemplates());
        exporter.setConfiguration(configuration);
        // configuration.register("company", "state_tag", new EnumByNameType<EntityStateTag>(EntityStateTag.class));

        configuration.register(new JSR310LocalDateTimeType());
        configuration.register(new JSR310LocalDateType());
        configuration.register(new BooleanCharacterType());
        // configuration.registerType("DATE", LocalDate.class);
        //configuration.registerType("TIMESTAMP(6)", Timestamp.class);

        exporter.setBeanSerializer(new CustomSerializer2());

        //table to export list
        String tables = getTables();
        if (tables != null)
            exporter.setTableNamePattern(tables);
        exporter.setNamingStrategy(new CustomNamingStrategy(tables));
        exporter.setExportInverseForeignKeys(true);

        exporter.export(getConnection().getMetaData());

    }

    public String getTables() {
        try {
            File file = new File(this.getClass().getResource("/tables.txt").getFile());
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.size() > 0 && StringUtils.isNotEmpty(lines.get(0))) {
                String tables = lines.stream().map(String::trim).collect(Collectors.joining(","));
                System.out.println("Follow tables will be processed: \n" + tables);
                return tables;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
