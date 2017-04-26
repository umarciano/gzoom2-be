package it.memelabs.smartnebula.lmm.persistence.main.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress;
import it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddressEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PostalAddressExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PostalAddressMapper;

@Service
public class PostalAddressDao {

	private final PostalAddressExMapper postalAddressExMapper;

	@Autowired
	public PostalAddressDao(PostalAddressExMapper postalAddressExMapper) {
		this.postalAddressExMapper = postalAddressExMapper;
	}

	public PostalAddress findById(long id) {
		return ((PostalAddressMapper) postalAddressExMapper).selectByPrimaryKey(id);
	}

	public PostalAddressEx findExById(long id) {
		return postalAddressExMapper.selectByPrimaryKey(id);
	}

	public Long create(PostalAddress record, UserLogin user) {
		Date createdStamp = new Date();
		record.setModifiedStamp(createdStamp);
		record.setModifiedByUserId(user.getId());
		record.setCreatedStamp(createdStamp);
		record.setCreatedByUserId(user.getId());
		int result = postalAddressExMapper.insert(record);
		return (result > 0) ? record.getId() : null;
	}

	public boolean update(PostalAddress record, UserLogin user) {
		record.setModifiedStamp(new Date());
		record.setModifiedByUserId(user.getId());
		int result = postalAddressExMapper.updateByPrimaryKey(record);
		return result > 0;
	}

	public boolean delete(long id) {
		int result = postalAddressExMapper.deleteByPrimaryKey(id);
		return result > 0;
	}
}
