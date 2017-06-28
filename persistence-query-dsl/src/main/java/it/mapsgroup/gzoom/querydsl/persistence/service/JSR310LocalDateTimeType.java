package it.mapsgroup.gzoom.querydsl.persistence.service;

import com.querydsl.sql.types.AbstractJSR310DateTimeType;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import javax.annotation.Nullable;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

/**
 * JSR310LocalDateTimeType maps {@linkplain LocalDateTime}
 * to {@linkplain Timestamp} on the JDBC level
 *
  * see {@link com.github.javaplugs.mybatis.LocalDateTimeTypeHandler}
 */
@IgnoreJRERequirement
public class JSR310LocalDateTimeType extends AbstractJSR310DateTimeType<LocalDateTime> {

    public JSR310LocalDateTimeType() {
        super(Types.TIMESTAMP);
    }

    public JSR310LocalDateTimeType(int type) {
        super(type);
    }

    @Override
    public String getLiteral(LocalDateTime value) {
        return dateTimeFormatter.format(value);
    }

    @Override
    public Class<LocalDateTime> getReturnedClass() {
        return LocalDateTime.class;
    }

    @Nullable
    @Override
    public LocalDateTime getValue(ResultSet rs, int startIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(startIndex, utc());
        return ts != null ? LocalDateTime.ofInstant(ts.toInstant(), ZoneId.of("Europe/Rome")) : null;
    }

    @Override
    public void setValue(PreparedStatement st, int startIndex, LocalDateTime value) throws SQLException {
        st.setTimestamp(startIndex, Timestamp.valueOf(value),
                GregorianCalendar.from(ZonedDateTime.of(value, ZoneId.of("Europe/Rome"))));
    }
}
