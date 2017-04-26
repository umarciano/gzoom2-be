package it.memelabs.gn.services.authorization;

import javolution.util.FastList;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;

import java.sql.Timestamp;
import java.util.*;

public class AuthorizationItemSearchHelper extends CommonAuthorizationHelper {

    public final static String module = AuthorizationItemSearchHelper.class.getName();
    private TermHelper termHelper;

    public AuthorizationItemSearchHelper(DispatchContext dctx, Map<String, ? extends Object> context) {
        super(dctx, context);
        termHelper = new TermHelper(dctx, context);
    }

    public List<Map<String, Object>> findAuthorizationOperationalDetails(String agreementId, Timestamp validityDate, List<String> wasteCerCodeIds, List<String> operationIds, List<String> registrationNumberIds) throws GenericEntityException, GenericServiceException {
        List<EntityCondition> conds = FastList.newInstance();
        if (UtilValidate.isNotEmpty(agreementId))
            conds.add(EntityCondition.makeCondition("agreementId", agreementId));

        List<GenericValue> _authItems = delegator.findList("GnAuthorizationAndAgreementItem", EntityCondition.makeCondition(conds), null, UtilMisc.toList("agreementId", "agreementItemSeqId"), null, false);
        List<Map<String, Object>> validPeriodItems = FastList.newInstance();

        for (GenericValue _authItem : _authItems) {
            Map<String, Object> authItem = UtilMisc.makeMapWritable(_authItem);
            if (UtilValidate.isNotEmpty(validityDate)) {
                Timestamp validFromDate = (Timestamp) authItem.get("validFromDate");
                Timestamp validThruDate = (Timestamp) authItem.get("validThruDate");
                if (validFromDate.compareTo(validityDate) <= 0 && validThruDate.compareTo(validityDate) >= 0) {
                    validPeriodItems.add(authItem);
                }
            } else {
                validPeriodItems.add(authItem);
            }
        }

        if (validPeriodItems.size() > 0) {

            List<Map<String, Object>> validVehicleItems = FastList.newInstance();
            List<Map<String, Object>> validVehicleItemsNoCer = FastList.newInstance();
            Map<String, HashSet<String>> categoryCer = new HashMap<String, HashSet<String>>(); //this map is useful to check details with reg. number term and without cer term

            boolean checkWaste = UtilValidate.isNotEmpty(wasteCerCodeIds); // all cer present in the same detail
            boolean checkOperation = UtilValidate.isNotEmpty(operationIds); // all operation present in the same detail
            boolean checkRegNumber = UtilValidate.isNotEmpty(registrationNumberIds); // vehicle and trailer can be present in different datail with same category
            String vehicle = null;
            String trailer = null;
            if (checkRegNumber) {
                vehicle = registrationNumberIds.get(0).toUpperCase();
                vehicle = vehicle.replaceAll(" ", "");
                if (registrationNumberIds.size() > 1) {
                    trailer = registrationNumberIds.get(1).toUpperCase();
                    trailer = trailer.replaceAll(" ", "");
                }
            }

            boolean foundCer;
            boolean foundTermCer;
            boolean foundOperation;
            boolean foundRegNumber;
            List<String> cerCodeIds;
            List<String> operationCodeIds;
            List<String> registrationNumberList;

            for (Map<String, Object> authItem : validPeriodItems) { // check vehicle (if present)
                List<Map<String, Object>> terms = termHelper.findAuthorizationAndAgreementTerms((String) authItem.get("agreementId"), (String) authItem.get("agreementItemSeqId"), false);
                foundCer = false;
                foundTermCer = false;
                foundOperation = false;
                foundRegNumber = false;
                cerCodeIds = new ArrayList<String>();
                operationCodeIds = new ArrayList<String>();
                registrationNumberList = new ArrayList<String>();
                for (Map<String, Object> term : terms) {

                    if (checkWaste && AgreementTermTypeOfbiz.GN_CNS_WASTE.name().equals(term.get("termTypeId"))) {
                        foundTermCer = true;
                        List<Map<String, Object>> wasteCerCodes = (List<Map<String, Object>>) term.get("wasteCerCodes");
                        for (Map<String, Object> wasteCerCode : wasteCerCodes) {
                            String wasteCerCodeId = (String) wasteCerCode.get("wasteCerCodeId");
                            if (wasteCerCodeId != null) cerCodeIds.add(wasteCerCodeId);
                        }
                        if (cerCodeIds.containsAll(wasteCerCodeIds))
                            foundCer = true;

                        String categoryClassEnumId = (String) authItem.get("categoryClassEnumId");
                        HashSet<String> catCerList = (categoryCer.size() == 0) ? null : categoryCer.get(categoryClassEnumId);
                        if (catCerList == null) {
                            categoryCer.put(categoryClassEnumId, new HashSet<String>(cerCodeIds));
                        } else {
                            catCerList.addAll(cerCodeIds);
                            categoryCer.put(categoryClassEnumId, catCerList);
                        }

                    } else if (checkOperation && AgreementTermTypeOfbiz.GN_CNS_OPERATION.name().equals(term.get("termTypeId"))) {
                        List<Map<String, Object>> operations = (List<Map<String, Object>>) term.get("operations");
                        for (Map<String, Object> operation : operations) {
                            String operationId = (String) operation.get("operationId");
                            if (operationId != null) operationCodeIds.add(operationId);
                        }
                        if (operationCodeIds.containsAll(operationIds))
                            foundOperation = true;

                    } else if (checkRegNumber && AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.name().equals(term.get("termTypeId"))) {
                        List<Map<String, Object>> regNumbers = (List<Map<String, Object>>) term.get("registrationNumbers");
                        for (Map<String, Object> regNumber : regNumbers) {
                            String registrationNumber = (String) regNumber.get("registrationNumber");
                            if (registrationNumber != null)
                                registrationNumberList.add(registrationNumber.toUpperCase());
                        }
                        if (registrationNumberList.contains(vehicle))
                            foundRegNumber = true;
                    }
                } //end for terms
                if ((!checkWaste || foundCer) &&
                        (!checkOperation || foundOperation) &&
                        (!checkRegNumber || foundRegNumber)
                        ) {
                    validVehicleItems.add(authItem);
                } else if ((checkWaste && !foundTermCer && checkRegNumber && foundRegNumber) &&
                        (!checkOperation || foundOperation)
                        ) {
                    validVehicleItemsNoCer.add(authItem);
                }


            } // end for validPeriodItem -> validVehicleItems, validVehicleItemsNoCer

            //GREEN-1121
            for (Map<String, Object> validRegNumberItem : validVehicleItemsNoCer) {
                String categoryClassEnumId = (String) validRegNumberItem.get("categoryClassEnumId");
                HashSet<String> enabledCer = categoryCer.get(categoryClassEnumId);
                if (enabledCer != null && enabledCer.containsAll(wasteCerCodeIds))
                    validVehicleItems.add(validRegNumberItem);
            }

            if (validVehicleItems.size() > 0) {
                List<Map<String, Object>> resultAuthorizationItems = FastList.newInstance();

                if (trailer == null) {
                    resultAuthorizationItems.addAll(validVehicleItems);
                } else { //GREEN-1121
                    List<Map<String, Object>> validTrailerItems = FastList.newInstance();
                    List<Map<String, Object>> validTrailerItemsNoCer = FastList.newInstance();

                    for (Map<String, Object> authItem : validPeriodItems) { // check trailer (same algorithm as before for vehicle)

                        List<Map<String, Object>> terms = termHelper.findAuthorizationAndAgreementTerms((String) authItem.get("agreementId"), (String) authItem.get("agreementItemSeqId"), false);
                        foundCer = false;
                        foundTermCer = false;
                        foundOperation = false;
                        foundRegNumber = false;
                        cerCodeIds = new ArrayList<String>();
                        operationCodeIds = new ArrayList<String>();
                        registrationNumberList = new ArrayList<String>();
                        for (Map<String, Object> term : terms) {

                            if (checkWaste && AgreementTermTypeOfbiz.GN_CNS_WASTE.name().equals(term.get("termTypeId"))) {
                                foundTermCer = true;
                                List<Map<String, Object>> wasteCerCodes = (List<Map<String, Object>>) term.get("wasteCerCodes");
                                for (Map<String, Object> wasteCerCode : wasteCerCodes) {
                                    String wasteCerCodeId = (String) wasteCerCode.get("wasteCerCodeId");
                                    if (wasteCerCodeId != null) cerCodeIds.add(wasteCerCodeId);
                                }
                                if (cerCodeIds.containsAll(wasteCerCodeIds))
                                    foundCer = true;

                                // it doesn't matter to re-calculate categoryCer

                            } else if (checkOperation && AgreementTermTypeOfbiz.GN_CNS_OPERATION.name().equals(term.get("termTypeId"))) {
                                List<Map<String, Object>> operations = (List<Map<String, Object>>) term.get("operations");
                                for (Map<String, Object> operation : operations) {
                                    String operationId = (String) operation.get("operationId");
                                    if (operationId != null) operationCodeIds.add(operationId);
                                }
                                if (operationCodeIds.containsAll(operationIds))
                                    foundOperation = true;

                            } else if (checkRegNumber && AgreementTermTypeOfbiz.GN_CNS_VEHICLE_REG_N.name().equals(term.get("termTypeId"))) {
                                List<Map<String, Object>> regNumbers = (List<Map<String, Object>>) term.get("registrationNumbers");
                                for (Map<String, Object> regNumber : regNumbers) {
                                    String registrationNumber = (String) regNumber.get("registrationNumber");
                                    if (registrationNumber != null)
                                        registrationNumberList.add(registrationNumber.toUpperCase());
                                }
                                if (registrationNumberList.contains(trailer))
                                    foundRegNumber = true;
                            }
                        } //end for terms
                        if ((!checkWaste || foundCer) &&
                                (!checkOperation || foundOperation) &&
                                (!checkRegNumber || foundRegNumber)
                                ) {
                            validTrailerItems.add(authItem);
                        } else if ((checkWaste && !foundTermCer && checkRegNumber && foundRegNumber) &&
                                (!checkOperation || foundOperation)
                                ) {
                            validTrailerItemsNoCer.add(authItem);
                        }


                    } // end for validPeriodItem -> validTrailerItems, validTrailerItemsNoCer

                    //GREEN-1121
                    for (Map<String, Object> validRegNumberItem : validTrailerItemsNoCer) {
                        String categoryClassEnumId = (String) validRegNumberItem.get("categoryClassEnumId");
                        HashSet<String> enabledCer = categoryCer.get(categoryClassEnumId);
                        if (enabledCer != null && enabledCer.containsAll(wasteCerCodeIds))
                            validTrailerItems.add(validRegNumberItem);
                    }

                    if (validTrailerItems.size() > 0) {
                        String idVehicleItem;
                        String vehicleCategoryClassEnumId;
                        String idTrailerItem;
                        String trailerCategoryClassEnumId;
                        Set<String> resultItemsId = new HashSet<String>();
                        for (Map<String, Object> vehicleItem : validVehicleItems) {
                            idVehicleItem = (String) vehicleItem.get("agreementItemSeqId");
                            vehicleCategoryClassEnumId = (String) vehicleItem.get("categoryClassEnumId");
                            if (!resultItemsId.contains(idVehicleItem)) {
                                for (Map<String, Object> trailerItem : validTrailerItems) {
                                    idTrailerItem = (String) trailerItem.get("agreementItemSeqId");
                                    trailerCategoryClassEnumId = (String) trailerItem.get("categoryClassEnumId");
                                    if ((vehicleCategoryClassEnumId == null && trailerCategoryClassEnumId == null) ||
                                            (vehicleCategoryClassEnumId != null && trailerCategoryClassEnumId != null
                                                    && vehicleCategoryClassEnumId.equals(trailerCategoryClassEnumId))) {
                                        if (!resultItemsId.contains(idVehicleItem)) {
                                            resultAuthorizationItems.add(vehicleItem);
                                            resultItemsId.add(idVehicleItem);
                                        }
                                        if (!resultItemsId.contains(idTrailerItem)) {
                                            resultAuthorizationItems.add(trailerItem);
                                            resultItemsId.add(idTrailerItem);
                                        }
                                    }
                                }//end for validTrailerItems
                            }
                        }//end for validVehicleItems
                    }
                }//end if else (trailer!=null)

                return resultAuthorizationItems;
            } else {
                return FastList.newInstance();
            }
        } else {
            return FastList.newInstance();
        }
    }

}