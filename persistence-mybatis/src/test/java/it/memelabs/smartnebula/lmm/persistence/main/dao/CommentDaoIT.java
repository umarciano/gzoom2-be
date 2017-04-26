package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.CommentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class CommentDaoIT  extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(CommentDaoIT.class);

    @Autowired
    private CommentDao commentDao;


    @Test
    public void testFindByFilter() throws Exception {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.getNodes().add(node);
        Tuple2<List<CommentEx>, Integer> ret = commentDao.findByContractId(1000, userLogin);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
    }


}
