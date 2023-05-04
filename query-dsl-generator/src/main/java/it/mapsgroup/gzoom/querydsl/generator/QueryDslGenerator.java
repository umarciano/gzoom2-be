package it.mapsgroup.gzoom.querydsl.generator;

import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
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

    //PATH LINUX: la cartella "." è la root del progetto backend su Linux
    //PATH WINDOWS: va bene il path scommentato con i doppi slash

    // public static final String TARGET_FOLDER = "";
    public static final String TARGET_FOLDER = "persistence-query-dsl/src/generated/java";

    private Connection getConnection() throws SQLException {
        // Funziona solo con mysql, prima funzionava anche con jdk1.8 e postgres
        // return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ltprod", "lmm", "lmm");
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gzoom_comune_follonica", "postgres", "P0stgres.81");
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.9.32:5432/gzoom_regione_campania", "gzoom_test", "gzoom_test");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gzoom_milanocamcom?nullDatabaseMeansCurrent=true&autoReconnect=true&useOldAliasMetadataBehavior=true&useSSL=false&serverTimezone=Europe/Rome", "root", "F14Tomcat!");
        // Connection connection = DriverManager.getConnection("jdbc:sqlserver://MG-19BT\\\\SQLEXPRESS:55975;databaseName=gzoom_comune_genovanew;SelectMethod=cursor;", "sa", "sa.12345");
        // return DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle-maps.maps1.mapsengineering.com)(PORT=1521))" +
        //        "(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME= devdb.maps1.mapsengineering.com)))", "ANFO", "@4ndr34_77");*/
        System.out.println(" getConnection() " + connection);
        return connection;
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
        Configuration configuration = new Configuration(new MySQLTemplates());
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
        System.out.println(" getConnection() " + getConnection());
        System.out.println(" getConnection() getSchema " + getConnection().getSchema());
        System.out.println(" getConnection() getCatalog " + getConnection().getCatalog());
        // per farlo funzionare con mysql serve impostare come schemaPattern il catalog
        exporter.setSchemaPattern(getConnection().getCatalog());
        exporter.setNamingStrategy(new CustomNamingStrategy(tables));
        exporter.setExportInverseForeignKeys(true);
        exporter.export(getConnection().getMetaData());

    }

    public String getTables() {
        try {
            File file = new File(this.getClass().getResource("/tables.txt").getFile());
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.size() > 0 && StringUtils.isNotEmpty(lines.get(0))) {
                String tables = lines.stream()
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .filter(s -> s.length() > 0)
                        .collect(Collectors.joining(","));
                System.out.println("Follow tables will be processed: \n" + tables);
                return tables;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
