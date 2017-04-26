package it.memelabs.smartnebula.lmm.persistence.main.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeSequenceGeneratorParam;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.IdentityMapper;

@Service
public class IdentityDao {

	private final IdentityMapper identityMapper;

	@Autowired
	public IdentityDao(IdentityMapper identityMapper) {
		this.identityMapper = identityMapper;
	}

	public long nextValue(String tableName, String columnName, UserLogin user) {
		Long value = identityMapper.selectMaxByNode(new NodeSequenceGeneratorParam(tableName, columnName, user.getActiveNode().getId()));
		return value != null ? value.longValue() + 1L : 1L;
	}
}
