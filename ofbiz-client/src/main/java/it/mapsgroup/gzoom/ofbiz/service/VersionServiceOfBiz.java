package it.mapsgroup.gzoom.ofbiz.service;

import it.mapsgroup.gzoom.ofbiz.client.VersionOfBizClient;

import java.util.Map;

/**
 * @author Andrea Fossi.
 */
public class VersionServiceOfBiz {

    private final VersionOfBizClient versionClient;

    public VersionServiceOfBiz(VersionOfBizClient versionClient) {
        this.versionClient = versionClient;
    }

    public String version() {
        Map<String, Object> response = versionClient.getVersion();
//        Map<String, Object> response = loginClient.login("admin", "MapsGzoom01",null);
        String version = (String) response.get("versions");
        return version;
    }
}
