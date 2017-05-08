package it.mapsgroup.gzoom.ofbiz.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * 24/02/14
 *
 * @author Elisa Spada
 */
public class OfbizClientConfigImpl implements OfBizClientConfig {

    private static Properties props;

    public OfbizClientConfigImpl(Properties props) {
        OfbizClientConfigImpl.props = props;
    }

    @Override
    public URL getServerXmlRpcUrl() {
        try {
            return new URL((String) props.get("ofbiz.server.xmlrpc.url"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getOfbizDefaultUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOfbizDefaultPwd() {
        throw new UnsupportedOperationException();
    }
/*
    @Override
    public String getOfbizServiceUser(String ofbizPipelineServiceName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOfbizServicePwd(String ofbizPipelineServiceName) {
        throw new UnsupportedOperationException();
    }*/

}
