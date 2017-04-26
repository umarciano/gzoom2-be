package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PostalAddressExMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddressEx;

import java.util.List;

/**
 * @author Andrea Fossi.
 */

public class PostalAddressDaoIT extends AbstractDaoTestIT {
	@Autowired
	private PostalAddressDao postalAddressDao;


	@Autowired
	private PostalAddressExMapper mapper;

	@Test
	public void testFindById() throws Exception {
		PostalAddress ret = postalAddressDao.findById(1000l);
		System.out.println("id=" + (ret != null ? ret.getId() : null));
	}

	@Test
	public void testFindExById() throws Exception {
		PostalAddressEx ret = postalAddressDao.findExById(1941L);
		System.out.println("id=" + (ret != null ? ret.getId() : null));
	}

	@Test
	public void testFindExLotId() throws Exception {
		PostalAddressEx ret = mapper.selectByLotId(1000l);
		System.out.println("id=" + (ret != null ? ret.getId() : null));
	}
	@Test
	public void testSelectByConstructionSiteId() throws Exception {
		List<PostalAddressEx> ret = mapper.selectByConstructionSiteId(1000l);
		System.out.println("id=" + (ret != null ? ret.size() : null));
	}
}
