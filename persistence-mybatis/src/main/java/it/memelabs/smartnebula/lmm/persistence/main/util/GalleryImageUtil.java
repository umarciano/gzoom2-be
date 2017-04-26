package it.memelabs.smartnebula.lmm.persistence.main.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class GalleryImageUtil {
    private static final String SEPARATOR = "/";

    public static final String getItemKey(long nodeId, String entity, Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder sb = new StringBuilder();
        sb.append("node-").append(String.format("%08d", nodeId)).append(SEPARATOR);
        sb.append(entity.toString()).append(SEPARATOR);
        sb.append(df.format(date)).append(SEPARATOR).append("images");
        return sb.toString();
    }

    public static final String getFilename(long attachmentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("image-").append(String.format("%08d", attachmentId)).append(".dat");
        return sb.toString();
    }

    public static final String getFilename(long attachmentId, String suffix, String extension) {
        StringBuilder sb = new StringBuilder();
        if (suffix == null) suffix = "";
        sb.append("image-").append(String.format("%08d", attachmentId)).append("_").append(suffix).append(".").append(extension);
        return sb.toString();
    }


}
