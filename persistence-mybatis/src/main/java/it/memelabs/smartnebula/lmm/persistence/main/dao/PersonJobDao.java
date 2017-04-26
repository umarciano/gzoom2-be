package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonJob;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonJobExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonJobMapper;

/**
 * @author sivi.
 */
@Service
public class PersonJobDao {
	// private static final Logger LOG = getLogger(PersonJobDao.class);

	private final PersonJobMapper personJobMapper;

	@Autowired
	public PersonJobDao(PersonJobMapper personJobMapper) {
		this.personJobMapper = personJobMapper;
	}

	public Tuple2<List<PersonJob>, Integer> findJobs(UserLogin user) {
		PersonJobExample personJobExample = new PersonJobExample();
		PersonJobExample.Criteria criteria = personJobExample.createCriteria();
		criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
		personJobExample.setOrderByClause("description, id");
		List<PersonJob> list = personJobMapper.selectByExample(personJobExample);
		return new Tuple2<>(list, list.size());
	}
}
