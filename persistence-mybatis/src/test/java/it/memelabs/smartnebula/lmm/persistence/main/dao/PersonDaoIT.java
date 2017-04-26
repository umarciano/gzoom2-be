package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEx;
import it.memelabs.smartnebula.lmm.persistence.main.util.Dummies;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class PersonDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(PersonDaoIT.class);

    @Autowired
    private PersonDao personDao;

    @Test
    public void findEx() throws Exception {
        PersonFilter filter = new PersonFilter();
        filter.setFilterCompanyStatusId(2102L);
        filter.setFilterType("persons");
        Tuple2<List<PersonEx>, Integer> ret = personDao.findByFilter(filter, Dummies.getUserLogin(2L));
        ret.first();
    }
}
