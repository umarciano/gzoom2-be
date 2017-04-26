package it.memelabs.smartnebula.lmm.persistence.job.util;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrea Fossi.
 */
public class JobUtil {

    public static String toXml(Object o) {
        XStream xstream = new XStream();
        return xstream.toXML(o);
    }

    public static <T> T fromXml(String xml) {
        if (StringUtils.isBlank(xml)) return null;
        else {
            XStream xstream = new XStream();
            T ret = (T) xstream.fromXML(xml);
            return ret;
        }
    }

  /*  public static Object fromXml(String xml) {
        XStream xstream = new XStream();
        return xstream.fromXML(xml);

    }*/
}
