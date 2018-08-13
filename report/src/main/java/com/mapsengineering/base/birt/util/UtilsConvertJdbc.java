package com.mapsengineering.base.birt.util;

import java.util.Date;

public class UtilsConvertJdbc {
    
    public static final String DEFAULT_GROUP_NAME = "org.ofbiz";
    public static final String DATE_FORMAT_ORACLE = "MM/DD/YYYY";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";
    
    
    public static String getConvertDateToDateJdbc(Date date, Object delegator) {
      
      String dateString = "'" + UtilDateTime.toDateString(date, DATE_TIME_FORMAT) + "'";
      return dateString;
    }
    public static String getConvertDateToDateJdbc(Date date) {
        
        String dateString = "'" + UtilDateTime.toDateString(date, DATE_TIME_FORMAT) + "'";
        return dateString;
    }
    
    
//    
//    /**
//     * Utilizzato per convertire la data nel formato corretto in base al tipo di DB utilizzato
//     * @param date
//     * @param delegator
//     * @return
//     */
//    public static String getConvertDateToDateJdbc(Date date, Delegator delegator) {
//        
//        String dateString = "'" + org.ofbiz.base.util.UtilDateTime.toSqlDateString(date) + "'";
//        
//        DatasourceInfo datasourceInfo = getDatasourceInfo(DEFAULT_GROUP_NAME, delegator);
//        String fieldTypeName = datasourceInfo.fieldTypeName;
//        if (isOracle(fieldTypeName)) {
//            dateString = getConvertDateToDateOracle(date);
//        }
//        /*TODO non funzionano cosi le date controllare anche la parte sotto 
//         * if (isMsSql(fieldTypeName)) {
//            dateString = getConvertDateToDateMsSql(dateString);
//        } else 
//        }         
//        */
//        return dateString;
//    }
//    
//    private static String getConvertDateToDateMsSql(String dateString) {
//        return "{ts " + dateString + "}";
//    }
//    
//    private static String getConvertDateToDateOracle(Date date) {
//        String dateString = org.ofbiz.base.util.UtilDateTime.toDateString(date);
//        return "TO_DATE('" + dateString + "','" + DATE_FORMAT_ORACLE +"')";
//    }
//    
//    private static DatasourceInfo getDatasourceInfo(String groupName, Delegator delegator) {
//        String helperName = delegator.getGroupHelperName(groupName);
//        if (helperName != null) {
//            DatasourceInfo datasourceInfo = EntityConfigUtil.getDatasourceInfo(helperName);
//            if (datasourceInfo != null && UtilValidate.isNotEmpty(datasourceInfo)) {
//                return datasourceInfo;
//            }
//        }
//        return null;
//    }
//    
//    private static boolean isMsSql(String type) {
//        if ("mssql".equals(type)) {
//            return true;
//        }
//        return false;
//    }
//    
//    private static boolean isPostgres(String type) {
//        if ("postgres".equals(type)) {
//            return true;
//        }
//        return false;
//    }
//    
//    private static boolean isOracle(String type) {
//        if ("oracle".equals(type)) {
//            return true;
//        }
//        return false;
//    }
//    
//    /**
//     * Utilizzato per convertire la funziona di adddate
//     * @param interval = day per aggiungere giorni alla data
//     * @param number = campo che contiene il numero di giorni che devono essere aggiunti
//     * @param date = campo da dove prendere la data
//     * @param delegator
//     * @return
//     */
//    public static String getConvertAddDateToJdbc(String interval, String number, String date, Delegator delegator) {
//        
//        String addDateString = "";
//        
//        DatasourceInfo datasourceInfo = getDatasourceInfo(DEFAULT_GROUP_NAME, delegator);
//        String fieldTypeName = datasourceInfo.fieldTypeName;
//        if (isMsSql(fieldTypeName)) {
//            addDateString = getConvertAddDateToDateMsSql(interval, number, date);
//        } else if (isOracle(fieldTypeName)) {
//            addDateString = getConvertAddDateToDateOracle(number, date);
//        } else if (isPostgres(fieldTypeName)) {
//            addDateString = getConvertAddDateToDatePostgres(interval, number, date);
//        } else {
//            addDateString = getConvertAddDateToDateMySql(interval, number, date);
//        }
//        
//        return addDateString;
//    }
//    
//    /**
//     * 
//     * @param interval = day per aggiungere giorni alla data
//     * @param number = campo che contiene il numero di giorni che devono essere aggiunti
//     * @param date 
//     * @param delegator
//     * @return
//     */
//    public static String getConvertAddDateToJdbc(String interval, String number, Date date, Delegator delegator) {
//        
//        String addDateString = "";
//        DatasourceInfo datasourceInfo = getDatasourceInfo(DEFAULT_GROUP_NAME, delegator);
//        String fieldTypeName = datasourceInfo.fieldTypeName;
//        if (isMsSql(fieldTypeName)) {
//            addDateString = getConvertAddDateToDateMsSql(interval, number, date);
//        } else if (isOracle(fieldTypeName)) {
//            addDateString = getConvertAddDateToDateOracle(number, date);
//        } else if (isPostgres(fieldTypeName)) {
//            addDateString = getConvertAddDateToDatePostgres(interval, number, date);
//        } else{
//            addDateString = getConvertAddDateToDateMySql(interval, number, date);
//        }
//        
//        return addDateString;
//    }
//    
//
//    
//    /**
//     * 
//     * DATEADD (datepart, number, date) 
//     * @param interval
//     * @param number
//     * @param date
//     * @return
//     */
//    private static String getConvertAddDateToDateMsSql(String interval, String number, String date) {
//        return "DATEADD(" + interval + ", " + number + ", " + date +")";
//    }
//    
//    private static String getConvertAddDateToDateMsSql(String interval, String number, Date date) {
//        String dateString = "'" + org.ofbiz.base.util.UtilDateTime.toSqlDateString(date) + "'";
//        //return "DATEADD(" + interval + ", " + number + ", " + getConvertDateToDateMsSql(dateString) +")";
//        return "DATEADD(" + interval + ", " + number + ", " + dateString +")";
//    }
//    
//    
//    /**
//     * ADDDATE(date, INTERVAL expr unit)
//     * @param interval
//     * @param number
//     * @param date
//     * @return
//     */
//    private static String getConvertAddDateToDateMySql(String interval, String number, String date) {
//        return "ADDDATE(" + date + ", INTERVAL " + number + " " + interval +")";
//    }
//    private static String getConvertAddDateToDateMySql(String interval, String number, Date date) {
//        String dateString = "'" + org.ofbiz.base.util.UtilDateTime.toSqlDateString(date) + "'";
//        return "ADDDATE(" + dateString + ", INTERVAL " + number + " " + interval +")";
//    }
//    
//    /**
//     * date + 'number day'
//     * @param interval
//     * @param number
//     * @param date
//     * @return
//     */
//    private static String getConvertAddDateToDatePostgres(String interval, String number, String date) {
//        return date + " + '" + number + " " + interval + "'";
//    }
//    private static String getConvertAddDateToDatePostgres(String interval, String number, Date date) {
//        String dateString = "'" + org.ofbiz.base.util.UtilDateTime.toSqlDateString(date) + "'";
//        return dateString + " + '" + number + " " + interval + "'";
//    }
//    
//    /**
//     * date + 1  
//     * @param interval
//     * @param number
//     * @param date
//     * @return
//     */
//    private static String getConvertAddDateToDateOracle(String number, String date) {
//        return date + " + " + number ;
//    }
//    private static String getConvertAddDateToDateOracle(String number, Date date) {
//        return getConvertDateToDateOracle(date) + " + " + number ;
//    }
    
}
