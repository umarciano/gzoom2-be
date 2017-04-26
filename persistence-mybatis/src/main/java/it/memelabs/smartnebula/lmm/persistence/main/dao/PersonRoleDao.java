package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonRole;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonRoleExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonRoleMapper;

/**
 * @author sivi.
 */
@Service
public class PersonRoleDao {
	// private static final Logger LOG = getLogger(PersonRoleDao.class);

	private final PersonRoleMapper personRoleMapper;

	@Autowired
	public PersonRoleDao(PersonRoleMapper personRoleMapper) {
		this.personRoleMapper = personRoleMapper;
	}

	public Tuple2<List<PersonRole>, Integer> findRoles(UserLogin user) {
		PersonRoleExample personRoleExample = new PersonRoleExample();
		PersonRoleExample.Criteria criteria = personRoleExample.createCriteria();
		criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
		personRoleExample.setOrderByClause("description, id");
		List<PersonRole> list = personRoleMapper.selectByExample(personRoleExample);
		return new Tuple2<>(list, list.size());
	}
}
