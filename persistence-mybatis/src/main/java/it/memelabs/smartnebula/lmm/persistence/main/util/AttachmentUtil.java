package it.memelabs.smartnebula.lmm.persistence.main.util;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class AttachmentUtil {
    private static final String SEPARATOR = "/";

    public static final String getItemKey(long nodeId, AttachmentEntity entity, Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder sb = new StringBuilder();
        sb.append("node-").append(String.format("%08d",nodeId)).append(SEPARATOR);
        sb.append(entity.toString()).append(SEPARATOR);
        sb.append(df.format(date)).append(SEPARATOR).append("attachments");
        return sb.toString();
    }

    public static final String getFilename(long attachmentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("att-").append(String.format("%08d", attachmentId)).append(".dat");
        return sb.toString();
    }


}
