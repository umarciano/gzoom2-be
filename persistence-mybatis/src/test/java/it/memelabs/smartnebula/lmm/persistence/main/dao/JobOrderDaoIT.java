package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Andrea Fossi.
 */

public class JobOrderDaoIT extends AbstractDaoTestIT {
	@Autowired
	private JobOrderDao jobOrderDao;

	@Test
	public void testFindById() throws Exception {
		JobOrderEx ret = jobOrderDao.findById(1001l);
		ret.getId();
	}
	@Test
	public void testFindByFilter() throws Exception {
		Node node = new Node();
		node.setId(1L);
		UserLogin userLogin = new UserLogin();
		userLogin.getNodes().add(node);
		JobOrderFilter filter = new JobOrderFilter();
		Tuple2<List<JobOrderEx>, Integer> ret = jobOrderDao.findByFilter(filter, userLogin);
		ret.first().size();
	}

}
