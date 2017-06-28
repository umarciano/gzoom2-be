package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.types.AbstractJSR310DateTimeType;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import javax.annotation.Nullable;
import java.sql.*;
import java.time.LocalDate;

/**
 * JSR310LocalDateType maps {@linkplain LocalDate}
 * to {@linkplain Date} on the JDBC level
 *
 * see {@link com.github.javaplugs.mybatis.LocalDateTypeHandler}
 */
@IgnoreJRERequirement
public class JSR310LocalDateType extends AbstractJSR310DateTimeType<LocalDate> {

    public JSR310LocalDateType() {
        super(Types.DATE);
    }

    public JSR310LocalDateType(int type) {
        super(type);
    }

    @Override
    public String getLiteral(LocalDate value) {
        return dateFormatter.format(value);
    }

    @Override
    public Class<LocalDate> getReturnedClass() {
        return LocalDate.class;
    }

    @Nullable
    @Override
    public LocalDate getValue(ResultSet rs, int startIndex) throws SQLException {
        Date date = rs.getDate(startIndex);
        return date != null ? date.toLocalDate() : null;
    }

    @Override
    public void setValue(PreparedStatement st, int startIndex, LocalDate value) throws SQLException {
        st.setDate(startIndex, Date.valueOf(value));
    }
}
