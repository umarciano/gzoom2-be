//ErrorEvent.java, created on 08/nov/2013
package it.memelabs.gn.webapp.event;

import it.memelabs.gn.services.OfbizErrors;

import java.util.EnumMap;

/**
*@author Aldo Figlioli
*/
public class ErrorEvent {
    private static final ThreadLocal<EnumMap<OfbizErrors, String>> ERRORS = new ThreadLocal<EnumMap<OfbizErrors,String>>() {
        @Override
        protected EnumMap<OfbizErrors, String> initialValue() {
            return new EnumMap<OfbizErrors, String>(OfbizErrors.class);
        }
    };
    
    public static void clean() {
        ERRORS.remove();
    }
    
    public static void addError(OfbizErrors code, String param) {
        EnumMap<OfbizErrors, String> old = ERRORS.get();
        old.put(code, param);
        ERRORS.set(old);
    }
    
    public static boolean contains(OfbizErrors code) {
        return ERRORS.get().containsKey(code);
    }
    
    public static String getParameter(OfbizErrors code) {
        return ERRORS.get().get(code);
    } 
}
