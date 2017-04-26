package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhase;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseType;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseTypeExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AntimafiaProcessPhaseExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AntimafiaProcessPhaseTypeMapper;

@Service
public class AntimafiaProcessPhaseDao {

	private final AntimafiaProcessPhaseExMapper antimafiaProcessPhaseExMapper;
	private final AntimafiaProcessPhaseTypeMapper antimafiaProcessPhaseTypeMapper;

	@Autowired
	public AntimafiaProcessPhaseDao(AntimafiaProcessPhaseExMapper antimafiaProcessPhaseExMapper, AntimafiaProcessPhaseTypeMapper antimafiaProcessPhaseTypeMapper) {
		this.antimafiaProcessPhaseExMapper = antimafiaProcessPhaseExMapper;
		this.antimafiaProcessPhaseTypeMapper = antimafiaProcessPhaseTypeMapper;
	}

	public AntimafiaProcessPhase findById(long id) {
		return antimafiaProcessPhaseExMapper.selectByPrimaryKey(id);
	}

	public AntimafiaProcessPhaseEx findExById(long id) {
		return antimafiaProcessPhaseExMapper.selectExByPrimaryKey(id);
	}
	
	public List<AntimafiaProcessPhaseEx> findExByAntimafiaProcessId(long antimafiaProcessId) {
		AntimafiaProcessPhaseExample antimafiaProcessPhaseExample = new AntimafiaProcessPhaseExample();
		AntimafiaProcessPhaseExample.Criteria criteria = antimafiaProcessPhaseExample.createCriteria();
		criteria.andAntimafiaProcessIdEqualTo(antimafiaProcessId);
		antimafiaProcessPhaseExample.setOrderByClause("type_ordinal, id");
		return antimafiaProcessPhaseExMapper.selectExByExample(antimafiaProcessPhaseExample);
	}

	public Long create(AntimafiaProcessPhase record, UserLogin user) {
		Date createdStamp = new Date();
		record.setModifiedStamp(createdStamp);
		record.setModifiedByUserId(user.getId());
		record.setCreatedStamp(createdStamp);
		record.setCreatedByUserId(user.getId());
		int result = antimafiaProcessPhaseExMapper.insert(record);
		return (result > 0) ? record.getId() : null;
	}

	public boolean update(AntimafiaProcessPhase record, UserLogin user) {
		record.setModifiedStamp(new Date());
		record.setModifiedByUserId(user.getId());
		int result = antimafiaProcessPhaseExMapper.updateByPrimaryKey(record);
		return result > 0;
	}

	public boolean delete(long id) {
		int result = antimafiaProcessPhaseExMapper.deleteByPrimaryKey(id);
		return result > 0;
	}

	public boolean deleteByAntimafiaProcessId(long antimafiaProcessId) {
		AntimafiaProcessPhaseExample example = new AntimafiaProcessPhaseExample();
		AntimafiaProcessPhaseExample.Criteria criteria = example.createCriteria();
		criteria.andAntimafiaProcessIdEqualTo(antimafiaProcessId);
		int result = antimafiaProcessPhaseExMapper.deleteByExample(example);
		return result > 0;
	}

	public List<AntimafiaProcessPhaseType> findAntimafiaProcessPhaseTypes(UserLogin user) {
		AntimafiaProcessPhaseTypeExample example = new AntimafiaProcessPhaseTypeExample();
		AntimafiaProcessPhaseTypeExample.Criteria criteria = example.createCriteria();
		criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
		example.setOrderByClause("ordinal, id");
		return antimafiaProcessPhaseTypeMapper.selectByExample(example);
	}
	
	public int antimafiaProcessPhaseTypeCount(long antimafiaProcessId, long phaseTypeId) {
        return antimafiaProcessPhaseExMapper.selectCountByUniquePhaseType(antimafiaProcessId, phaseTypeId);
    }
}
