package it.mapsgroup.gzoom.querydsl.generator;

import com.google.common.collect.ImmutableList;
import com.querydsl.sql.SchemaAndTable;
import com.querydsl.sql.codegen.DefaultNamingStrategy;
import com.querydsl.sql.codegen.support.ForeignKeyData;
import com.querydsl.sql.codegen.support.InverseForeignKeyData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {
    List<String> tables;

    public CustomNamingStrategy(String tableNamePattern) {
        tables = Arrays.asList(tableNamePattern);
        if (tableNamePattern != null && tableNamePattern.contains(",")) {
            tables = ImmutableList.copyOf(tableNamePattern.split(","));
        } else {
            tables = Collections.emptyList();
        }
    }

    @Override
    public String getClassName(String tableName) {
        if (tableName.equalsIgnoreCase("USER_LOGIN")) return "UserLoginPersistent";
        return super.getClassName(tableName);
    }

    @Override
    public boolean shouldGenerateForeignKey(SchemaAndTable schemaAndTable, ForeignKeyData foreignKeyData) {
        return tables.contains(foreignKeyData.getTable().toUpperCase());
    }


    public boolean shouldGenerateInverseForeignKeys(SchemaAndTable schemaAndTable, InverseForeignKeyData inverseForeignKeyData) {
        return tables.contains(inverseForeignKeyData.getTable().toUpperCase());
    }
}
