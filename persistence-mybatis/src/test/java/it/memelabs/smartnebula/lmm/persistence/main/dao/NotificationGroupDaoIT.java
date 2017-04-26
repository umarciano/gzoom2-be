package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationGroupEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class NotificationGroupDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(NotificationGroupDaoIT.class);

    @Autowired
    private NotificationGroupDao dao;

    @Test
    public void testFindExById() throws Exception {
        NotificationGroupEx ret = dao.findExById(101l);
        System.out.println("id=" + (ret != null ? ret.getId() : null));
    }
    @Test
    public void testFindExFilter() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);

        NotificationGroupFilter filter = new NotificationGroupFilter();
        filter.setFilterText("aaa");
        filter.setPage(1);
        filter.setSize(10);
        List<NotificationGroupEx> ret = dao.findByFilter(filter,userLogin).first();
        System.out.println("id=" + (ret != null ? ret.size() : null));
    }

}
