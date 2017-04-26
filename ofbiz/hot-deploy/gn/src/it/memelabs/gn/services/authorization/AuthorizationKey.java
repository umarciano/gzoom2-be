package it.memelabs.gn.services.authorization;

import java.util.HashMap;
import java.util.Map;

/**
 * 10/04/13
 *
 * @author Andrea Fossi
 */
public class AuthorizationKey {
    private String lastModifierNodeKey;
    private String ownerNodeKey;
    private String parentVersion;
    private Long version;
    private String uuid;

    private AuthorizationKey(String authorizationKey) {
        parseString(authorizationKey);
    }

    private AuthorizationKey(Map<String, Object> result) {
        parseMap(result);
    }

    private AuthorizationKey(String uuid, String lastModifierNodeKey, String ownerNodeKey, Long version, String parentVersion) {
        init(uuid, lastModifierNodeKey, ownerNodeKey, version, parentVersion);
    }


    public static AuthorizationKey fromMap(Map<String, Object> map) {
        return new AuthorizationKey(map);
    }

    public static AuthorizationKey fromString(String key) {
        return new AuthorizationKey(key);
    }

    public static AuthorizationKey newInstance(String uuid, String lastModifierNodeKey, String ownerNodeKey, Long version, String parentVersion) {
        return new AuthorizationKey(uuid, lastModifierNodeKey, ownerNodeKey, version, parentVersion);
    }

    private void init(String uuid, String lastModifierNodeKey, String ownerNodeKey, Long version, String parentVersion) {
        this.lastModifierNodeKey = lastModifierNodeKey;
        this.ownerNodeKey = ownerNodeKey;
        this.parentVersion = parentVersion;
        this.uuid = uuid;
        this.version = version;
    }

    private void parseMap(Map<String, Object> result) {
        uuid = (String) result.get("uuid");
        lastModifierNodeKey = (String) result.get("lastModifierNodeKey");
        ownerNodeKey = (String) result.get("ownerNodeKey");
        parentVersion = (String) result.get("parentVersion");
        version = (Long) result.get("version");
    }


    /**
     * Parse Key String and return a map with separate fields
     * UUID `#' LAST_MODIFIER_NODE_ID `#' OWNER_NODE_ID `#' VERSION `#' PARENT_VERSIONS
     * <p/>
     * ovvero PARENT_VERSIONS viene dopo VERSION (mentre prima PARENT_VERSION veniva prima)
     *
     * @param authorizationKey key fields
     */
    private Map<String, Object> parseString(String authorizationKey) {
        String[] pp = authorizationKey.split("#");


        Map<String, Object> result = new HashMap<String, Object>();

        //Debug.log(m.group(1) + m.group(2) + m.group(3) + m.group(4) + m.group(5));
        uuid = pp[0];
        lastModifierNodeKey = pp[1];
        ownerNodeKey = pp[2];
        version = Long.parseLong(pp[3]);
        parentVersion = pp[4];


        /*Pattern pp = Pattern.compile("(.*)\\#(.*)\\#(.*)\\#(\\d*)\\#(\\d*)");

        Matcher m = pp.matcher(authorizationKey);
        Map<String, Object> result = new HashMap<String, Object>();
        while (m.find()) {
            //Debug.log(m.group(1) + m.group(2) + m.group(3) + m.group(4) + m.group(5));
            uuid = m.group(1);
            lastModifierNodeKey = m.group(2);
            ownerNodeKey = m.group(3);
            version = Long.parseLong(m.group(4));
            parentVersion = m.group(5);

        }*/
        return result;
    }

    public String getLastModifierNodeKey() {
        return lastModifierNodeKey;
    }

    public String getOwnerNodeKey() {
        return ownerNodeKey;
    }

    public String getParentVersion() {
        return parentVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setLastModifierNodeKey(String lastModifierNodeKey) {
        this.lastModifierNodeKey = lastModifierNodeKey;
    }

    public void setOwnerNodeKey(String ownerNodeKey) {
        this.ownerNodeKey = ownerNodeKey;
    }

    public void setParentVersion(String parentVersion) {
        this.parentVersion = parentVersion;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("uuid", uuid);
        result.put("lastModifierNodeKey", lastModifierNodeKey);
        result.put("ownerNodeKey", ownerNodeKey);
        result.put("version", version);
        result.put("parentVersion", parentVersion);
        return result;
    }

    /**
     * * UUID `#' LAST_MODIFIER_NODE_ID `#' OWNER_NODE_ID `#' VERSION `#' PARENT_VERSIONS
     * <p/>
     * ovvero PARENT_VERSIONS viene dopo VERSION (mentre prima PARENT_VERSION veniva prima)
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s#%s#%s#%s#%s", uuid, lastModifierNodeKey, ownerNodeKey, version, parentVersion);
    }
}
