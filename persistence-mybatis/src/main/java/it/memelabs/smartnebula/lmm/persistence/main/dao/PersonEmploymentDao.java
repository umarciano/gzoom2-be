package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateTag;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EntityStateMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PersonEmploymentExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.commons.DateUtil.getEndOfDay;
import static it.memelabs.smartnebula.commons.DateUtil.getStartOfDay;

/**
 * @author Andrea Fossi.
 */
@Service
public class PersonEmploymentDao {
    // private static final Logger LOG = getLogger(PersonEmploymentDao.class);

    private final PersonEmploymentExMapper personEmploymentExMapper;
    private final EntityStateMapper entityStateMapper;
    private final CommonEmploymentDao commonEmploymentDao;

    @Autowired
    public PersonEmploymentDao(PersonEmploymentExMapper personEmploymentExMapper, EntityStateMapper entityStateMapper, CommonEmploymentDao commonEmploymentDao) {
        this.personEmploymentExMapper = personEmploymentExMapper;
        this.entityStateMapper = entityStateMapper;
        this.commonEmploymentDao = commonEmploymentDao;
    }

    public PersonEmploymentEx findById(long id) {
        return personEmploymentExMapper.selectByPrimaryKey(id);
    }

    public Tuple2<List<PersonEmploymentEx>, Integer> findByPersonId(int page, int size, long personId, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        PersonEmploymentExample personEmploymentExample = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria criteria = personEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andPersonIdEqualTo(personId);
        personEmploymentExample.setOrderByClause("start_date desc, end_date desc, id");

        int count = personEmploymentExMapper.countByExample(personEmploymentExample);
        List<PersonEmploymentEx> list = personEmploymentExMapper.selectExByExampleWithRowbounds(personEmploymentExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(PersonEmployment record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        record.setExternalId(commonEmploymentDao.uniqueIdentifier(CommonEmploymentDao.Type.PE, user.getActiveNode().getId()));
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        int result = personEmploymentExMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(PersonEmployment record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        if (record.getStateId() != null)
            record.setStateTag(entityStateMapper.selectByPrimaryKey(record.getStateId()).getTag());
        int result = personEmploymentExMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        PersonEmploymentExample example = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andExportedStampIsNull();
        int result = personEmploymentExMapper.deleteByExample(example);
        return result > 0;
    }

    public boolean checkDateOverlap(PersonEmploymentExExample ex) {
        if (personEmploymentExMapper.countDateOverlap(ex) > 0) {
            return true;
        }
        return false;
    }

    public Tuple2<List<PersonEmploymentEx>, Integer> findForWeeklyWorkLog(int page, int size, long companyId, Long wwlId, Long wlId, Date start, Date end, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        PersonEmploymentWwlExample example = new PersonEmploymentWwlExample();
        PersonEmploymentWwlExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andCompanyIdEqualTo(companyId);
        example.setFilterEmploymentStartDate(start);
        example.setFilterEmploymentEndDate(end);
        example.setOrderByClause("person_last_name asc, person_first_name asc, start_date desc, end_date desc, id");
        example.setWeeklyWorkLogId(wwlId);
        example.setWorkLogId(wlId);
        example.setCompanyId(companyId);
        criteria.andStateTagEqualTo(EntityStateTag.AUTHORIZED);
        int count = personEmploymentExMapper.countWeekyWorkLogByExample(example);
        List<PersonEmploymentEx> list = personEmploymentExMapper.selectWeekyWorkLogByExampleWithRowbounds(example, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Tuple2<List<PersonEmploymentEx>, Integer> findByIds(int page, int size, List<Long> ids, UserLogin user) {
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        PersonEmploymentExample personEmploymentExample = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria criteria = personEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andIdIn(ids);
        personEmploymentExample.setOrderByClause("start_date desc, end_date desc, id");

        int count = personEmploymentExMapper.countByExample(personEmploymentExample);
        List<PersonEmploymentEx> list = personEmploymentExMapper.selectExByExampleWithRowbounds(personEmploymentExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    @Deprecated
    public List<PersonEmploymentEx> findByIds(List<Long> ids, UserLogin user) {
        PersonEmploymentExample personEmploymentExample = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria criteria = personEmploymentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andIdIn(ids);
        personEmploymentExample.setOrderByClause("company_id desc, id desc");
        List<PersonEmploymentEx> list = personEmploymentExMapper.selectExByExampleWithRowbounds(personEmploymentExample, new RowBounds());
        return list;
    }

    public Tuple2<List<PersonEmploymentEx>, Integer> findByFreeText(PersonEmploymentFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        filter.setOwnerNodeId(user.getActiveNode().getId());
        List<PersonEmploymentEx> list = personEmploymentExMapper.selectByFreeTextWithRowbounds(filter, rowBounds);
        return new Tuple2<>(list, list.size());
    }

    public PersonEmploymentEx findByExternalId(String externalId, UserLogin user) {
        if (StringUtils.isNotBlank(externalId)) {
            PersonEmploymentExample example = new PersonEmploymentExample();
            PersonEmploymentExample.Criteria criteria = example.createCriteria();
            criteria.andExternalIdEqualTo(externalId);
            if (user != null) criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
            List<PersonEmploymentEx> personEmployments = personEmploymentExMapper.selectExByExampleWithRowbounds(example, new RowBounds());
            if (personEmployments.size() > 0) return personEmployments.get(0);
        }
        return null;
    }


    /**
     * Find {@link EquipmentEmployment} with endDate contained in interval
     *
     * @param endDateFrom
     * @param endDateTo
     * @param user
     * @return
     */
    public List<PersonEmploymentEx> findByEndDate(Date endDateFrom, Date endDateTo, EntityStateTag stateTag, UserLogin user) {
        PersonEmploymentExample example = new PersonEmploymentExample();
        PersonEmploymentExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andEndDateBetween(getStartOfDay(endDateFrom), getEndOfDay(endDateTo));
        if (stateTag != null)
            criteria.andStateTagEqualTo(stateTag);
        example.setOrderByClause("end_date ASC");
        return personEmploymentExMapper.selectExByExampleWithRowbounds(example, new RowBounds());
    }
}
