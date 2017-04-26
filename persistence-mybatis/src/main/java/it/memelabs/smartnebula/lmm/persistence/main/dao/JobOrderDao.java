package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrderExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.JobOrderExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobOrderDao {
	// private static final Logger LOG = getLogger(JobOrderDao.class);

	private final JobOrderExMapper jobOrderExMapper;

	@Autowired
	public JobOrderDao(JobOrderExMapper jobOrderMapper) {
		this.jobOrderExMapper = jobOrderMapper;
	}

	public JobOrderEx findById(long id) {
		return jobOrderExMapper.selectByPrimaryKeyEx(id);
	}

	public Tuple2<List<JobOrderEx>, Integer> findByFilter(JobOrderFilter filter, UserLogin user) {
		RowBounds rowBounds = new RowBounds((filter.getPage() - 1) * filter.getSize(), filter.getSize());
		filter.setOwnerNodeId(user.getActiveNode().getId());

		JobOrderExExample jobOrderExample = new JobOrderExExample();
		JobOrderExExample.Criteria criteria = jobOrderExample.createCriteria();
		criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
		if (StringUtils.isNotBlank(filter.getCode())) {
			criteria.andCodeLikeInsensitive(filter.getCode());
		}
		jobOrderExample.setOrderByClause("code, description, id");
		int count = jobOrderExMapper.countByExampleEx(jobOrderExample);
		List<JobOrderEx> list = jobOrderExMapper.selectByExampleWithRowboundsEx(jobOrderExample, rowBounds);
		return new Tuple2<>(list, count);
	}

	public Long create(JobOrder record, UserLogin user) {
		Date createdStamp = new Date();
		record.setModifiedStamp(createdStamp);
		record.setModifiedByUserId(user.getId());
		record.setCreatedStamp(createdStamp);
		record.setCreatedByUserId(user.getId());
		record.setOwnerNodeId(user.getActiveNode().getId());
		int result = jobOrderExMapper.insert(record);
		return (result > 0) ? record.getId() : null;
	}

	public boolean update(JobOrder record, UserLogin user) {
		record.setModifiedStamp(new Date());
		record.setModifiedByUserId(user.getId());
		record.setOwnerNodeId(user.getActiveNode().getId());
		int result = jobOrderExMapper.updateByPrimaryKey(record);
		return result > 0;
	}

	public boolean delete(long id) {
		int result = jobOrderExMapper.deleteByPrimaryKey(id);
		return result > 0;
	}

	public Tuple2<List<JobOrderEx>, Integer> findJobOrderByText(JobOrderFilter filter, UserLogin user) {
		RowBounds rowBounds = new RowBounds(RowBounds.NO_ROW_OFFSET, filter.getSize());
		filter.setOwnerNodeId(user.getActiveNode().getId());

		JobOrderExExample jobOrderExample = new JobOrderExExample();
		JobOrderExExample.Criteria criteria = jobOrderExample.createCriteria();
		criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
		if (StringUtils.isNotBlank(filter.getFreeText())) {
			jobOrderExample.setFilterText(filter.getFreeText());
		}
		jobOrderExample.setOrderByClause("code, description, id");
		int count = jobOrderExMapper.countByExampleEx(jobOrderExample);
		List<JobOrderEx> list = jobOrderExMapper.selectByExampleWithRowboundsEx(jobOrderExample, rowBounds);
		return new Tuple2<>(list, count);
	}
}
