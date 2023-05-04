package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dao.SecurityGroupPermissionDao;
import it.mapsgroup.gzoom.querydsl.dao.UserLoginSecurityGroupDao;
import it.mapsgroup.gzoom.querydsl.dao.UserPreferenceDao;
import it.mapsgroup.gzoom.querydsl.dto.SecurityGroupPermission;
import it.mapsgroup.gzoom.querydsl.dto.UserLoginSecurityGroup;
import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionRestService {

    private static String SUP_ADMIN = "SUP_ADMIN";
    private static String TOP_ADMIN = "TOP_ADMIN";
    private static String ORG_ADMIN = "ORG_ADMIN";
    private static String ROLE_ADMIN = "ROLE_ADMIN";
    private static String MGR_ADMIN = "MGR_ADMIN";
    private static String FULLADMIN = "FULLADMIN";

    private static String BSCPERF = "BSCPERF";

    private UserLoginSecurityGroupDao userLoginSecurityGroupDao;
    private SecurityGroupPermissionDao securityGroupPermissionDao;
    private UserPreferenceDao userPreferenceDao;



    @Autowired
    public PermissionRestService(UserLoginSecurityGroupDao userLoginSecurityGroupDao, SecurityGroupPermissionDao securityGroupPermissionDao, UserPreferenceDao userPreferenceDao) {
        this.userLoginSecurityGroupDao = userLoginSecurityGroupDao;
        this.securityGroupPermissionDao = securityGroupPermissionDao;
        this.userPreferenceDao = userPreferenceDao;
    }
    /**
     * Data il localDispatcherName, ritorno il nome del gruppo di sicurezza
     * @param localDispatcherName
     * @return
     */
    public String permissionLocalDispatcherName(String localDispatcherName) {
        String permission = localDispatcherName.toUpperCase();
        if ("STRATPERF".equals(permission)) {
            permission = BSCPERF;
        }

        return permission;
    }

    /**
     * Get default ORGANIZATION_PARTY by Username
     * @param user
     * @return company
     */
    public String userPrefereceOrganizationUnitId(String user) {
        String company = "Company";
        UserPreference userPreference = this.userPreferenceDao.getUserPreference(user, "ORGANIZATION_PARTY");
        if(userPreference!=null) company = userPreference.getUserPrefValue();
        return company;
    }

    public boolean hasSecurityGroup(String userLoginId, String gruopId) {
        List<UserLoginSecurityGroup> list = userLoginSecurityGroupDao.getUserLoginSecurityGroups(userLoginId, gruopId);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasPermission(String permissionId, String userLoginId) {
        //getPermission
        List<UserLoginSecurityGroup> list = userLoginSecurityGroupDao.getUserLoginSecurityGroups(userLoginId);
        for(UserLoginSecurityGroup ele: list) {
            List<SecurityGroupPermission> sgpList = securityGroupPermissionDao.getSecurityGroupPermissions(ele.getGroupId(), permissionId);
            if (sgpList != null && sgpList.size() > 0 ) return true;
        }
        return false;
    }

    public boolean isFullAdmin(String userLoginId, String permission ) {
        boolean x =  hasPermission(permission + MGR_ADMIN, userLoginId) || hasSecurityGroup(userLoginId, FULLADMIN);
        return x;
    }
}
