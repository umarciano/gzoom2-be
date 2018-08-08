package it.mapsgroup.gzoom.report.querydsl;

import com.querydsl.core.QueryMetadata;
import com.querydsl.sql.SQLBaseListener;
import com.querydsl.sql.SQLListenerContext;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Deprecated
public class LogListener extends SQLBaseListener {
    private static final Logger LOG = getLogger(LogListener.class);

    @Override
    public void notifyQuery(QueryMetadata md) {
    }

    @Override
    public void start(SQLListenerContext context) {
        super.start(context);
    }

    @Override
    public void prePrepare(SQLListenerContext context) {
        super.prePrepare(context);
    }

    @Override
    public void prepared(SQLListenerContext context) {

    }

    @Override
    public void rendered(SQLListenerContext context) {
        super.rendered(context);
    }

    @Override
    public void preExecute(SQLListenerContext context) {
        super.preExecute(context);
    }

}
