package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteLocationMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.PostalAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConstructionSiteLocationDao {

	private final ConstructionSiteLocationMapper constructionSiteLocationMapper;
	private final PostalAddressMapper postalAddressMapper;

	@Autowired
	public ConstructionSiteLocationDao(ConstructionSiteLocationMapper constructionSiteLocationMapper, PostalAddressMapper postalAddressMapper) {
		this.constructionSiteLocationMapper = constructionSiteLocationMapper;
		this.postalAddressMapper = postalAddressMapper;
	}

	public List<ConstructionSiteLocation> findConstructionSiteLocations(long id) {
		ConstructionSiteLocationExample ex = new ConstructionSiteLocationExample();
		ex.createCriteria().andConstructionSiteIdEqualTo(id);
		return constructionSiteLocationMapper.selectByExample(ex);
	}

	public boolean create(long constructionSiteId, long addressId, UserLogin user) {
		ConstructionSiteLocation record = new ConstructionSiteLocation();
		record.setConstructionSiteId(constructionSiteId);
		record.setAddressId(addressId);
		Date createdStamp = new Date();
		record.setCreatedStamp(createdStamp);
		record.setCreatedByUserId(user.getId());
		int result = constructionSiteLocationMapper.insert(record);
		return result > 0;
	}

	public boolean delete(ConstructionSiteLocationKey key) {
		int result = constructionSiteLocationMapper.deleteByPrimaryKey(key);
		return result > 0;
	}

	public void deleteLocationByConstructionSiteId(long id) {
		ConstructionSiteLocationExample ex = new ConstructionSiteLocationExample();
		ex.createCriteria().andConstructionSiteIdEqualTo(id);
		constructionSiteLocationMapper.deleteByExample(ex);
	}


	@Deprecated
	private void checkIfLocationDeleteNeeded(ConstructionSiteEx record) {
		List<ConstructionSiteLocation> locations = findConstructionSiteLocations(record.getId());
		if (locations != null && locations.size() > 0) {
			for (ConstructionSiteLocation location : locations) {
				boolean deleteIt = true;
				for (PostalAddress pa : record.getLocations()) {
					if (pa.getId().equals(location.getAddressId())) {
						deleteIt = false;
						break;
					}
				}
				if (deleteIt) {
					constructionSiteLocationMapper.deleteByPrimaryKey(location);
					postalAddressMapper.deleteByPrimaryKey(location.getAddressId());
				}
			}
		}
	}
}
