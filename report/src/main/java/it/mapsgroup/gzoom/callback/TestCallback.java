package it.mapsgroup.gzoom.callback;

import it.mapsgroup.gzoom.persistence.common.dto.enumeration.ReportCallbackType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Component
public class TestCallback extends ReportCallback {
    private static final Logger LOG = getLogger(TestCallback.class);

    @Autowired
    public TestCallback(ReportCallbackManager callBackManager) {
        super(callBackManager);
    }

    @Override
    public ReportCallbackType getType() {
        return ReportCallbackType.TEST;
    }

    @Transactional
    @Override
    public void run(Map<String, Object> params) {
        LOG.info("TEST - params[description]={}", params.get("description"));

    }
}
