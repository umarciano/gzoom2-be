package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcess;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;

public class AntimafiaProcessDaoIT  extends AbstractDaoTestIT {
	@Autowired
	private AntimafiaProcessDao antimafiaProcessDao;

	@Test
	public void testFindById() throws Exception {
		AntimafiaProcess ret = antimafiaProcessDao.findById(101l);
		System.out.println("id=" + (ret != null ? ret.getId() : null));
	}

	@Test
	public void testFindExById() throws Exception {
		AntimafiaProcessEx ret = antimafiaProcessDao.findExById(101l);
		System.out.println("id=" + (ret != null ? ret.getId() : null));
	}

	@Test
	public void testFindByFilter() throws Exception {
		Node node = new Node();
		node.setId(1L);
		UserLogin user = new UserLogin();
		user.getNodes().add(node);
		AntimafiaProcessFilter filter = new AntimafiaProcessFilter();
		filter.setPage(1);
		filter.setSize(10);
		Tuple2<List<AntimafiaProcess>, Integer> ret = antimafiaProcessDao.findByFilter(filter, user);
		System.out.println("size=" + (ret != null ? ret.first().size() : null));
	}

	@Test
	public void testFindExByFilter() throws Exception {
		Node node = new Node();
		node.setId(1L);
		UserLogin user = new UserLogin();
		user.getNodes().add(node);
		AntimafiaProcessFilter filter = new AntimafiaProcessFilter();
		filter.setPage(1);
		filter.setSize(10);
		Tuple2<List<AntimafiaProcessEx>, Integer> ret = antimafiaProcessDao.findExByFilter(filter, user);
		System.out.println("size=" + (ret != null ? ret.first().size() : null));
	}
}
