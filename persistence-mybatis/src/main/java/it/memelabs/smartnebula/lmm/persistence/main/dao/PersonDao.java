package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Person;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PersonExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author sivi.
 */
@Service
public class PersonDao {
    // private static final Logger LOG = getLogger(PersonDao.class);

    private final PersonExMapper personExMapper;

    @Autowired
    public PersonDao(PersonExMapper personExMapper) {
        this.personExMapper = personExMapper;
    }

    public PersonEx findById(long id) {
        return personExMapper.selectByPrimaryKey(id);
    }

    public boolean personExists(PersonFilter filter, UserLogin user) {
        PersonExExample personExExample = new PersonExExample();
        PersonExExample.Criteria criteria = personExExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getTaxIdNumber())) {
            criteria.andTaxIdentificationNumberLikeInsensitive(filter.makeLike(filter.getFilterFiscalCode()));
        }
        if (filter.getId() != null) {
            criteria.andIdNotEqualTo(filter.getId());
        }
        return personExMapper.countByExample(personExExample) > 0;
    }

    public Tuple2<List<PersonEx>, Integer> findByFilter(PersonFilter filter, UserLogin user) {
        RowBounds rowBounds = filter.makeRowBounds();
        filter.setOwnerNodeId(user.getActiveNode().getId());

        PersonExExample personExExample = new PersonExExample();
        PersonExExample.Criteria criteria = personExExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getFilterFirstName())) {
            criteria.andFirstNameLikeInsensitive(filter.makeLike(filter.getFilterFirstName()));
        }
        if (StringUtils.isNotBlank(filter.getFilterLastName())) {
            criteria.andLastNameLikeInsensitive(filter.makeLike(filter.getFilterLastName()));
        }
        if (StringUtils.isNotBlank(filter.getFilterFiscalCode())) {
            criteria.andTaxIdentificationNumberLikeInsensitive(filter.makeLike(filter.getFilterFiscalCode()));
        }
        if (filter.getFilterCompanyId() != null) {
            personExExample.setFilterCompanyId(filter.getFilterCompanyId());
        }
        if (filter.getFilterPeStatusId() != null) {
            personExExample.setFilterPeStatusId(filter.getFilterPeStatusId());
        }
        if (filter.getFilterTimeCardNumber() != null) {
            personExExample.setFilterTimeCardNumber(filter.getFilterTimeCardNumber());
        }
        if (StringUtils.isNotBlank(filter.getFilterBadgeNumber())) {
            personExExample.setFilterBadgeNumber(filter.makeLike(filter.getFilterBadgeNumber()));
        }
        if (StringUtils.isNotBlank(filter.getFilterText())) {
            personExExample.setFilterText(filter.makeLike(filter.getFilterText()));
        }
        if (StringUtils.isNotBlank(filter.getFilterType())) {
            personExExample.setFilterType(filter.getFilterType());
        }
        if (filter.getFilterSecondments() != null) {
            personExExample.setFilterSecondments(filter.getFilterSecondments());
        }
        if (filter.getFilterCompanyStatusId() != null) {
            personExExample.setFilterCompanyStatusId(filter.getFilterCompanyStatusId());
        }
        personExExample.setOrderByClause("last_name, first_name, id");
        int count = personExMapper.countByExample(personExExample);
        List<PersonEx> list = personExMapper.selectByExampleWithRowbounds(personExExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(Person record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = personExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(Person record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = personExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = personExMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    /**
     * Return  list of {@link Person Person} with an {@link it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmployment PersonEmployment}
     * linked in selected {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLog WorkLog}
     *
     * @param workLogId
     * @param filterText text to filter persons
     * @param page
     * @param size
     * @return
     */
    public Tuple2<List<Person>, Integer> findByWorkLogId(Long workLogId, String filterText, int page, int size) {
        String text = StringUtils.isNoneBlank(filterText) ? "%" + filterText + "%" : null;
        int count = personExMapper.countByWorkLogId(workLogId, text);
        List<Person> list = personExMapper.selectByWorkLogIdWithRowbounds(workLogId, text, new RowBounds((page - 1) * size, size));
        return new Tuple2<>(list, count);
    }

}
