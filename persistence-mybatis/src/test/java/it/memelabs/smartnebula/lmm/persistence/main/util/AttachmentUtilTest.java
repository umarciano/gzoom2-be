package it.memelabs.smartnebula.lmm.persistence.main.util;

import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import org.junit.Test;

import java.util.Date;

/**
 * @author Andrea Fossi.
 */
public class AttachmentUtilTest {
    @Test
    public void testDocumentPath() throws Exception {
        String ret = AttachmentUtil.getItemKey(2, AttachmentEntity.COMPANY, new Date());
        System.out.println(ret);
        ret=AttachmentUtil.getFilename(33);
        System.out.println(ret);
    }
}
