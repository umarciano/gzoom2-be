package it.mapsgroup.gzoom.report.querydsl.generator;

import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.types.EnumByNameType;
import com.querydsl.sql.types.JSR310LocalDateTimeType;
import com.querydsl.sql.types.JSR310LocalDateType;
import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportActivityStatus;
import it.mapsgroup.gzoom.querydsl.BooleanCharacterType;
import it.mapsgroup.gzoom.querydsl.generator.CustomNamingStrategy;
import it.mapsgroup.gzoom.querydsl.generator.CustomSerializer2;
import it.mapsgroup.gzoom.querydsl.generator.patch.MetaDataExporter;
import org.slf4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class QueryDslGeneratorReport {
    private static final Logger LOG = getLogger(QueryDslGeneratorReport.class);

    public static final String TARGET_FOLDER = "../report-persistence-query-dsl/src/generated/java";

    private Connection getConnection() throws SQLException {
    /*    return DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle-maps.maps1.mapsengineering.com)(PORT=1521))" +
                "(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME= devdb.maps1.mapsengineering.com)))", "ANFO", "@4ndr34_77");*/
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ltprod", "lmm", "lmm");
        //return DriverManager.getConnection("jdbc:mysql://localhost/gzoom_lite", "root", "root");
        //return DriverManager.getConnection("jdbc:mysql://localhost/gzoom?autoReconnect=true&amp;useOldAliasMetadataBehavior=true&amp;generateSimpleParameterMetadata=true", "root", "root");
        return DriverManager.getConnection("jdbc:mysql://gzoom-tux-2/gzoom_comune_lecco?autoReconnect=true&amp;useOldAliasMetadataBehavior=true&amp;generateSimpleParameterMetadata=true", "gzoom_test", "gzoom_test");
    }


    public static void main(String... args) throws SQLException, InterruptedException {
        new QueryDslGeneratorReport().generate();
        Thread.sleep(1000);
    }


    public void generate() throws SQLException {
        MetaDataExporter exporter = new MetaDataExporter();
        exporter.setPackageName("it.mapsgroup.report.querydsl.dto");
        exporter.setTargetFolder(new File(TARGET_FOLDER));
        BeanSerializer beanSerializer = new BeanSerializer();
        beanSerializer.setAddToString(true);
        exporter.setBeanSerializer(beanSerializer);
        exporter.setColumnAnnotations(true);
        Configuration configuration = new Configuration(new PostgreSQLTemplates());
        exporter.setConfiguration(configuration);
        // configuration.register("company", "state_tag", new EnumByNameType<EntityStateTag>(EntityStateTag.class));
        configuration.register("report_activity", "status", new EnumByNameType<ReportActivityStatus>(ReportActivityStatus.class));

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
        return "REPORT_ACTIVITY,";
    }

}
