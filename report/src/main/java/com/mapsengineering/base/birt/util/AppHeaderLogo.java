package com.mapsengineering.base.birt.util;

import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import it.mapsgroup.gzoom.service.NodeService;
import it.mapsgroup.gzoom.service.PartyService;
import it.mapsgroup.gzoom.service.UserPreferenceService;
import it.memelabs.smartnebula.spring.boot.config.ApplicationContextProvider;
import org.slf4j.Logger;


import static org.slf4j.LoggerFactory.getLogger;

public class AppHeaderLogo {
    private static final Logger LOG = getLogger(AppHeaderLogo.class);

    private NodeService nodeService;

    /*public AppHeaderLogo() {
        nodeService = ApplicationContextProvider.getApplicationContext().getBean(NodeService.class);
    }*/


   /**
     * cerca il logo associato alla company, se non lo trova ritorna il percorso del logo di default
     * @param delegator
     * @param partyContentTypeId
     * @param defaultOrganizationPartyId
     * @return
     */
    public static String getAppContentUrl(Object delegator, String partyContentTypeId, String defaultOrganizationPartyId) {
        LOG.error(" TODO getAppContentUrl NOT EXISTS");
        return null;
        /*List<EntityCondition> condList = FastList.newInstance();
        condList.add(EntityCondition.makeCondition(E.partyId.name(), defaultOrganizationPartyId));
        condList.add(EntityCondition.makeCondition(E.partyContentTypeId.name(), partyContentTypeId));
        condList.add(EntityCondition.makeCondition(E.thruDate.name(), GenericValue.NULL_FIELD));

        Set<String> fieldsToSelect = UtilMisc.toSet(E.contentId.name());
        List<String> orderByList = UtilMisc.toList("-contentId");
        List<GenericValue> partyContentList = delegator.findList(E.PartyContent.name(), EntityCondition.makeCondition(condList),
                fieldsToSelect, orderByList, null, true);
        GenericValue partyContent = EntityUtil.getFirst(partyContentList);

        String contentId = UtilValidate.isNotEmpty(partyContent) ? partyContent.getString(E.contentId.name()) : "";
        if(UtilValidate.isNotEmpty(contentId)) {
            return getImgUrlFromContent(contentId);
        }
        String defaultContentId = "DEF_" + partyContentTypeId;
        return getImgUrlFromContent(defaultContentId);*/
    }

    /**
     * compone la url del logo, da richiamare poi nell ftl
     * @param contentId
     * @return
     */
    private static String getImgUrlFromContent(String contentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("stream?contentId=");
        sb.append(contentId);
        return sb.toString();
    }

    /**
     * Cerco il logo associato alla Company per utilizzarlo nei report.
     *
     */
    public static String getReportContentUrl(String partyContentTypeId, String defaultOrganizationPartyId) {
        // LOG.info("stream default imagePath: " + imagePath);
        // InputStream bw = new ClassPathResource(imagePath).getInputStream();
        String fileName = null;
        NodeService nodeService = ApplicationContextProvider.getApplicationContext().getBean(NodeService.class);
        String partyContentEx = nodeService.getObjectInfo(defaultOrganizationPartyId, partyContentTypeId);

        if (partyContentEx != null) {
            LOG.info("stream partyContent path: " + partyContentEx);

        } else {
            LOG.info("No valid partyContent path found, use default... ");
        }
        return partyContentEx;
    }

    /**
     * Cerco il logo associato alla Company per utilizzarlo nei report.
      *
     */
    public static String getReportContentUrl(Object delegator, String partyContentTypeId, String defaultOrganizationPartyId) {
        LOG.info(" TODO getReportContentUrl with delegator TO DELETE ");
        return getReportContentUrl(partyContentTypeId, defaultOrganizationPartyId);
        /*
        List<EntityCondition> condList = FastList.newInstance();
        condList.add(EntityCondition.makeCondition(E.partyId.name(), defaultOrganizationPartyId));
        condList.add(EntityCondition.makeCondition(E.partyContentTypeId.name(), partyContentTypeId));
        condList.add(EntityCondition.makeCondition(E.thruDate.name(), GenericValue.NULL_FIELD));

        Set<String> fieldsToSelect = UtilMisc.toSet(E.objectInfo.name());
        List<GenericValue> partyContentList = delegator.findList(E.PartyAndContentDataResource.name(), EntityCondition.makeCondition(condList),
                fieldsToSelect, null, null, true);
        GenericValue partyContent = EntityUtil.getFirst(partyContentList);

        String objectInfo = UtilValidate.isNotEmpty(partyContent) ? partyContent.getString(E.objectInfo.name()) : "";
        if(UtilValidate.isNotEmpty(objectInfo)) {
            return objectInfo;
        } else {
            //Se non e' presente il logo custom uso quello di default
            String defaultContentId = "DEF_" + partyContentTypeId;
            List<EntityCondition> condDefaultList = FastList.newInstance();
            condDefaultList.add(EntityCondition.makeCondition(E.dataResourceId.name(), defaultContentId));
            fieldsToSelect = UtilMisc.toSet(E.objectInfo.name());
            List<GenericValue> dataResourceContentList = delegator.findList(E.DataResourceContentView.name(), EntityCondition.makeCondition(condDefaultList),
                    fieldsToSelect, null, null, true);
            GenericValue dataResourceContent = EntityUtil.getFirst(dataResourceContentList);
            objectInfo = UtilValidate.isNotEmpty(dataResourceContent) ? dataResourceContent.getString(E.objectInfo.name()) : "";

            URL url = FlexibleLocation.resolveLocation(objectInfo);

            objectInfo = url.toString();

            return objectInfo;
        }
*/
    }
    public static String getCompany(String userLoginId){
        String ret = "";
        UserPreferenceService userPreferenceService = ApplicationContextProvider.getApplicationContext().getBean(UserPreferenceService.class);
        PartyService partyService = ApplicationContextProvider.getApplicationContext().getBean(PartyService.class);
        UserPreference up = userPreferenceService.getUserPreference(userLoginId);
        if(up != null){
            ret = partyService.getUserPreference(userLoginId,up.getUserPrefValue());
        }
        return ret;
    }
}
