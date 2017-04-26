package it.memelabs.smartnebula.lmm.querydsl;

import com.querydsl.sql.codegen.MetaDataExporter;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class CustomMetaDataExporter extends MetaDataExporter {

    public void export(DatabaseMetaData md, List<String> tableWhiteList) throws SQLException {

        super.export(md);
    }
}
