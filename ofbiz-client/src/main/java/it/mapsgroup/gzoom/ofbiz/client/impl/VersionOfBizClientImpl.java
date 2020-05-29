package it.mapsgroup.gzoom.ofbiz.client.impl;

import it.mapsgroup.gzoom.ofbiz.client.VersionOfBizClient;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClient;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClientConfig;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class VersionOfBizClientImpl extends OfBizClient implements VersionOfBizClient {

    private static final Logger log = LoggerFactory.getLogger(VersionOfBizClientImpl.class);

    public VersionOfBizClientImpl(OfBizClientConfig config, HttpConnectionManager connectionManager) {
        super(config, connectionManager);
    }

    public VersionOfBizClientImpl(OfBizClientConfig config) {
        super(config);
    }

    @Override
    public Map<String, Object> getVersion() { // TODO aggiungere s
        Map<String, Object> result = execute("gzVersions");
        return result;
    }
}
