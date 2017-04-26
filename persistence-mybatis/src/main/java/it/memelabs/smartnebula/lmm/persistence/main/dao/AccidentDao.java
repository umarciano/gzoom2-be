package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Accident;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AccidentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AccidentExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AccidentExMapper;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author sivi.
 */
@Service
public class AccidentDao {
    private static final Logger LOG = getLogger(AccidentDao.class);

    private final AccidentExMapper accidentMapper;

    @Autowired
    public AccidentDao(AccidentExMapper accidentMapper) {
        this.accidentMapper = accidentMapper;
    }

    public AccidentEx findById(long id) {
        return accidentMapper.selectExByPrimaryKey(id);
    }

    public Long create(Accident record, UserLogin user) {
        Date createdStamp = new Date();
        record.setUntil(getUntil(record.getSince(), record.getDuration()));
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = accidentMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }


    public boolean update(Accident record, UserLogin user) {
        record.setUntil(getUntil(record.getSince(), record.getDuration()));
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = accidentMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = accidentMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Date getUntil(Date since, BigDecimal duration) {
        int i = duration.toBigInteger().intValue();
        BigDecimal fractional = duration.remainder(BigDecimal.ONE);
        if (!BigDecimal.ZERO.equals(fractional)) i += 1;
        return DateUtil.addDays(since, i);
    }

    public List<AccidentEx> findOverlap(AccidentFilter filter, UserLogin user) {
        filter.setOwnerNodeId(user.getActiveNode().getId());
        return accidentMapper.searchOverlap(filter);
    }

    public Tuple2<List<AccidentEx>, Integer> findByFilter(AccidentFilter filter, UserLogin user) {
        RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
        AccidentExample wbsExample = new AccidentExample();
        AccidentExample.Criteria criteria = wbsExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (filter.getFrom() != null) {
            criteria.andSinceGreaterThanOrEqualTo(filter.getFrom());
        }
        if (filter.getTo() != null) {
            criteria.andSinceLessThanOrEqualTo(filter.getTo());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }

        wbsExample.setOrderByClause("since desc, cs_description asc, pe_person_last_name asc, pe_person_first_name asc");
        int count = accidentMapper.countByExample(wbsExample);
        List<AccidentEx> list = accidentMapper.selectExByExampleWithRowbounds(wbsExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public int countByFilter(AccidentFilter filter, UserLogin user) {
        AccidentExample wbsExample = new AccidentExample();
        AccidentExample.Criteria criteria = wbsExample.createCriteria();
        filter.setOwnerNodeId(user.getActiveNode().getId());
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (filter.getFrom() != null) {
            criteria.andSinceGreaterThanOrEqualTo(filter.getFrom());
        }
        if (filter.getTo() != null) {
            criteria.andSinceLessThanOrEqualTo(filter.getTo());
        }
        if (filter.getConstructionSiteId() != null) {
            criteria.andConstructionSiteIdEqualTo(filter.getConstructionSiteId());
        }
        int count = accidentMapper.countByExample(wbsExample);
        return count;
    }

    /**
     * {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEvent WorkLogPersonEvent}
     * is not valid if accident is occurred before today and expire today or after
     *
     * @param eventDate
     * @param personEmploymentId
     * @return
     */
    public boolean countInjuredPersonForTimesheet(Date eventDate, Long personEmploymentId) {
        Date startOfDay = DateUtil.getStartOfDay(eventDate);

        AccidentExample wbsExample = new AccidentExample();
        AccidentExample.Criteria criteria = wbsExample.createCriteria();

        criteria.andSinceLessThanOrEqualTo(DateUtil.addDays(startOfDay, -1));
        criteria.andUntilGreaterThanOrEqualTo(startOfDay);
        criteria.andPersonEmploymentIdEqualTo(personEmploymentId);
        int count = accidentMapper.countByExample(wbsExample);

        LOG.info("Found {} valid accidents for {} and personEmployment {}", count, eventDate, personEmploymentId);
        return count > 0;
    }

    /**
     * {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLogPersonEvent WorkLogPersonEvent}
     * is not valid if accident is occurred before today and expire today or after
     *
     * @param eventIds
     * @return
     */
    public boolean countInjurerPersonForTimesheetEvents(List<Long> eventIds) {
        int count = accidentMapper.countByWorkLogPersonEvents(eventIds);
        LOG.info("Found {} valid accidents for WorkLogPersonEvents", count);
        return count > 0;
    }
}
