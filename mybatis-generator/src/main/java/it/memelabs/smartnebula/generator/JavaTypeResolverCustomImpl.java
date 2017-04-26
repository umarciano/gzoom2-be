package it.memelabs.smartnebula.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Andrea Fossi.
 */
public class JavaTypeResolverCustomImpl extends JavaTypeResolverDefaultImpl {

    private static final String PROP_USE_JSR_310 = "useJsr310";
    private static final String PROP_USE_CHAR_AS_BOOLEAN = "useCharAsBoolean";

    private static final String PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX = "javaTypeByColumnName.";

    private Map<String, FullyQualifiedJavaType> javaTypeByColumnName = new HashMap<String, FullyQualifiedJavaType>();

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);

        if ("true".equalsIgnoreCase(properties.getProperty(PROP_USE_JSR_310))) {
            typeMap.put(Types.DATE, new JdbcTypeInformation("DATE",
                    new FullyQualifiedJavaType(LocalDate.class.getName())));
            typeMap.put(Types.TIME, new JdbcTypeInformation("TIME",
                    new FullyQualifiedJavaType(LocalTime.class.getName())));
            typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP",
                    new FullyQualifiedJavaType(LocalDateTime.class.getName())));

        }

        if ("true".equalsIgnoreCase(properties.getProperty(PROP_USE_CHAR_AS_BOOLEAN))) {
            typeMap.put(Types.CHAR, new JdbcTypeInformation("CHAR",
                    new FullyQualifiedJavaType(Boolean.class.getName())));
        }

        for (String propName : properties.stringPropertyNames()) {
            if (propName.startsWith(PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX)) {
                String columnName = propName
                        .substring(PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX
                                .length());
                String propValue = properties.getProperty(propName);
                javaTypeByColumnName.put(columnName,
                        new FullyQualifiedJavaType(propValue));
            }
        }
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(
            IntrospectedColumn introspectedColumn) {
        String columnName = introspectedColumn.getActualColumnName();
        if (javaTypeByColumnName.containsKey(columnName)) {
            return javaTypeByColumnName.get(columnName);
        }
        return super.calculateJavaType(introspectedColumn);
    }

}