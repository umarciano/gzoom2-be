package it.memelabs.gn.doc;

import java.io.*;

/**
 * 12/02/13
 *
 * @author Andrea Fossi
 */
public class FileUtils {
    public static void copyFile(String source, String dest) throws IOException {
        copyFile(new File(source), new File(dest));
    }

    public static void copyFile(File source, File dest) throws IOException {
        if (!dest.exists()) {
            dest.createNewFile();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
