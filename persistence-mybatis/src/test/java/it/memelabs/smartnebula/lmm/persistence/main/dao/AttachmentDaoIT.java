package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AttachmentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AttachmentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AttachmentFilter;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andrea Fossi.
 */
public class AttachmentDaoIT  extends AbstractDaoTestIT {
    @Autowired
    private AttachmentDao attachmentDao;


    @Test
    public void testFindByCriteria() throws Exception {

        AttachmentExample attachmentExample = new AttachmentExample();

        AttachmentExample.Criteria criteria = attachmentExample.createCriteria();
        criteria.andIdEqualTo(1011l);

        AttachmentFilter attachmentFilter = new AttachmentFilter();
        attachmentFilter.setCompanyId(1000l);

        AttachmentEx attachmentEx = (AttachmentEx) attachmentDao.findExByFilter(attachmentFilter, AttachmentEntity.COMPANY, null);
        Assert.assertEquals(attachmentEx.getCompany().getId(), new Long(1000l));


    }



    /*
    @Test
    public void testSelectById() throws Exception {
        List<AttachmentEx> ret = attachmentDao.findByOwnerDocumentId(1060l);
        ret.size();
    }
    */


    /*@Test
    public void testSelectByFilter() throws Exception {
        DocumentFilter filter = new DocumentFilter();
        filter.setLoadUser(true);
        filter.setPage(1);
        filter.setSize(10);
        filter.setStatus(DocumentStatus.DRAFT);
        filter.setUuid("ww");
        filter.setDocVersion(3);
        filter.setOwnerNodeId(12L);
        UserLogin userLogin = new UserLogin();
        Node node = new Node();
        node.setId(1L);
        userLogin.getNodes().add(node);
        Tuple2<List<DocumentEx>, Integer> ret = attachmentDao.find(filter, userLogin);
        ret.first();
    }*/
}
