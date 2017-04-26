package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.LotLocationMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PostalAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LotLocationDao {
	// private static final Logger LOG = getLogger(LotLocationDao.class);

	private final LotLocationMapper lotLocationMapper;
	private final PostalAddressMapper postalAddressMapper;

	@Autowired
	public LotLocationDao(LotLocationMapper lotLocationMapper, PostalAddressMapper postalAddressMapper) {
		this.lotLocationMapper = lotLocationMapper;
		this.postalAddressMapper = postalAddressMapper;
	}

	public List<LotLocation> findLotLocations(long id) {
		LotLocationExample ex = new LotLocationExample();
		ex.createCriteria().andLotIdEqualTo(id);
		return lotLocationMapper.selectByExample(ex);
	}

	public boolean create(long lotId, long addressId, UserLogin user) {
		LotLocation record = new LotLocation();
		record.setLotId(lotId);
		record.setAddressId(addressId);
		Date createdStamp = new Date();
		record.setCreatedStamp(createdStamp);
		record.setCreatedByUserId(user.getId());
		int result = lotLocationMapper.insert(record);
		return result > 0;
	}

	public boolean delete(LotLocationKey key) {
		int result = lotLocationMapper.deleteByPrimaryKey(key);
		return result > 0;
	}

	public int deleteLocationsByLotId(long id) {
		LotLocationExample ex = new LotLocationExample();
		ex.createCriteria().andLotIdEqualTo(id);
		return lotLocationMapper.deleteByExample(ex);
	}

	@Deprecated
	private void saveOrUpdateLotLocation(LotEx record, UserLogin user, Date createdStamp, long id) {
		if (record.getLocations() != null && record.getLocations().size() > 0) {
			checkIfLocationDeleteNeeded(record);
			for (it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress location : record.getLocations()) {
				LotLocation lotLocation = new LotLocation();
				lotLocation.setAddressId(location.getId());
				lotLocation.setLotId(id);
				LotLocation exists = lotLocationMapper.selectByPrimaryKey(lotLocation);
				if (exists != null) {
					lotLocationMapper.updateByPrimaryKey(lotLocation);
				} else {
					lotLocation.setCreatedByUserId(user.getId());
					lotLocation.setCreatedStamp(createdStamp);
					lotLocationMapper.insert(lotLocation);
				}
			}
		}
	}

	@Deprecated
	private void checkIfLocationDeleteNeeded(LotEx record) {
		List<LotLocation> locations = findLotLocations(record.getId());
		if (locations != null && locations.size() > 0) {
			for (LotLocation location : locations) {
				boolean deleteIt = true;
				for (PostalAddressEx pa : record.getLocations()) {
					if (pa.getId().equals(location.getAddressId())) {
						deleteIt = false;
						break;
					}
				}
				if (deleteIt) {
					lotLocationMapper.deleteByPrimaryKey(location);
					postalAddressMapper.deleteByPrimaryKey(location.getAddressId());
				}
			}
		}
	}
}
