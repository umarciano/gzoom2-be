package com.mapsengineering.base.birt.util;

public class UtilStrings {

    /**
     * Replaces all occurrences of oldString in mainString with newString
     * @param mainString The original string
     * @param oldString The string to replace
     * @param newString The string to insert in place of the old
     * @return mainString with all occurrences of oldString replaced by newString
     */
    public static String replaceString(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        if ((oldString == null) || (oldString.length() == 0)) {
            return mainString;
        }
        if (newString == null) {
            newString = "";
        }

        int i = mainString.lastIndexOf(oldString);

        if (i < 0) return mainString;

        StringBuilder mainSb = new StringBuilder(mainString);

        while (i >= 0) {
            mainSb.replace(i, i + oldString.length(), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }

    /**
     * Ritorna la posizione dell'ultima occorrenza della stringa occ
     * @param str la stringa in cui cercare l'occorrenza
     * @param occ la stringa di cui cercare l'ultima posizione
     * @return l'ultima occorrenza della stringa occ
     */
    public static Integer lastIndexOf(String str, String occ) {
        if ((str == null) || (str.length() == 0) || (occ == null) || (occ.length() == 0)) {
            return null;
        }
        return  str.lastIndexOf(occ);
    }

    /**
     * Ritorna la sottostringa di str dalla posizione begin alla posizione end
     * @param str la stringa da cui ricavare la sottostringa
     * @param begin l'indice di inizio sottostringa
     * @param end l'indice di fine sottostringa
     * @return la sottostringa
     */
    public static String substring(String str, int begin, int end) {
        if ((str == null) || (str.length() == 0)) {
            return null;
        }
        return str.substring(begin,end);
    }


}
