package it.memelabs.gn.services.authorization.filter;

import it.memelabs.gn.util.CollectionDiff;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.service.GenericServiceException;

import java.util.*;

/**
 * 24/05/13
 *
 * @author Andrea Fossi
 */
public abstract class CollectionMatcher {
    public abstract List<Map<String, Object>> getDomain() throws GenericServiceException;

    StringBuffer sb = new StringBuffer();

    public boolean match(Map<String, Object> authTerm, Map<String, Object> filterTerm, String collectionKey, final String entityKey) throws GenericServiceException {
        //Map<String, Object> filterTerm = getTerm(AgreementTermTypeOfbiz.GN_CNS_OPERATION.name());
        if (filterTerm == null) {
            append("[true] " + collectionKey + " not found");
            return true;
        } else {
            List<Map<String, Object>> authOperations = UtilGenerics.checkList(authTerm.get(collectionKey));
            List<Map<String, Object>> filterOperations = UtilGenerics.checkList(filterTerm.get(collectionKey));
            boolean authTermInclusive = "Y".equals(authTerm.get("inclusive"));
            boolean filterTermInclusive = "Y".equals(filterTerm.get("inclusive"));

            boolean result = false;
            if (!authTermInclusive && !filterTermInclusive) {
                //!authTermInclusive && !filterTermInclusive
                Set<Map<String, Object>> union = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String leftId = (String) o1.get(entityKey);
                        String rightId = (String) o2.get(entityKey);
                        return leftId.compareTo(rightId);
                    }
                });
                union.addAll(authOperations);
                union.addAll(filterOperations);
                // List<Map<String, Object>> domain = filterMatcherHelper.findOperations();
                List<Map<String, Object>> domain = getDomain();
                CollectionDiff<Map<String, Object>, Map<String, Object>> diff2 =
                        new CollectionDiff<Map<String, Object>, Map<String, Object>>(union, domain) {
                            @Override
                            protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                String leftId = (String) left.get(entityKey);
                                String rightId = (String) right.get(entityKey);
                                return UtilValidate.areEqual(leftId, rightId);
                            }
                        };
                diff2.compare();
                result = diff2.getBoth().size() > 0;
                append("[" + result + "] (A\\/F)/\\D != 0");
            } else if ((!authTermInclusive && filterTermInclusive)) {
                List<Map<String, Object>> domain = getDomain();
                CollectionDiff<Map<String, Object>, Map<String, Object>> diff =
                        new CollectionDiff<Map<String, Object>, Map<String, Object>>(domain, authOperations) {
                            @Override
                            protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                String leftId = (String) left.get(entityKey);
                                String rightId = (String) right.get(entityKey);
                                return UtilValidate.areEqual(leftId, rightId);
                            }
                        };
                diff.compare();
                List<Map<String, Object>> domainMinusAuthTerm = diff.getLeftOnly();
                CollectionDiff<Map<String, Object>, Map<String, Object>> diff2 =
                        new CollectionDiff<Map<String, Object>, Map<String, Object>>(domainMinusAuthTerm, filterOperations) {
                            @Override
                            protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                String leftId = (String) left.get(entityKey);
                                String rightId = (String) right.get(entityKey);
                                return UtilValidate.areEqual(leftId, rightId);
                            }
                        };
                diff2.compare();
                result = diff2.getBoth().size() > 0;
                append("[" + result + "] (D\\A)/\\F != 0");
            } else {
                CollectionDiff<Map<String, Object>, Map<String, Object>> diff =
                        new CollectionDiff<Map<String, Object>, Map<String, Object>>(authOperations, filterOperations) {
                            @Override
                            protected boolean isItemEqual(Map<String, Object> left, Map<String, Object> right) {
                                String leftId = (String) left.get(entityKey);
                                String rightId = (String) right.get(entityKey);
                                return UtilValidate.areEqual(leftId, rightId);
                            }
                        };
                diff.compare();
                if (authTermInclusive && filterTermInclusive) {
                    result = diff.getBoth().size() > 0;
                    append("[" + result + "] A/\\F != 0");
                } else if ((authTermInclusive && !filterTermInclusive)) {
                    result = diff.getBoth().size() == 0;
                    append("[" + result + "] A/\\F == 0");
                }
            }

            //List<Map<String, Object>> roles = (List<Map<String, Object>>) term.get("subjectRoles");
            //append("[" + result + "] SubjectRole match[" + roleId + "]");
            return result;
        }
    }

    private void append(String s) {
        sb.append(s);
    }

    public String getMessage() {
        return sb.toString();
    }

}

