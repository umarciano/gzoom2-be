package it.mapsgroup.gzoom.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Andrea Fossi.
 */
public abstract class ReportCallback {
    private final ReportCallbackManager callBackManager;

    @Autowired
    public ReportCallback(ReportCallbackManager callBackManager) {
        this.callBackManager = callBackManager;
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        this.callBackManager.register(this);
    }

    public abstract ReportCallbackType getType();

    public abstract void run(String reportActivityId, Map<String, Object> params);
}
