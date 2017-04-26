package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteLogWeatherCatalogMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ConstructionSiteLogWeatherDataExMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class WeatherDataDao {
    private final ConstructionSiteLogWeatherDataExMapper weatherDataMapper;
    private final ConstructionSiteLogWeatherCatalogMapper weatherCatalogueMapper;

    @Autowired
    public WeatherDataDao(ConstructionSiteLogWeatherDataExMapper weatherDataMapper,
                          ConstructionSiteLogWeatherCatalogMapper weatherCatalogueMapper) {
        this.weatherDataMapper = weatherDataMapper;
        this.weatherCatalogueMapper = weatherCatalogueMapper;
    }

    public List<ConstructionSiteLogWeatherCatalog> findAll(UserLogin user) {
        ConstructionSiteLogWeatherCatalogExample example = new ConstructionSiteLogWeatherCatalogExample();
        ConstructionSiteLogWeatherCatalogExample.Criteria criteria = example.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        example.setOrderByClause("type ASC, ordinal ASC");
        return weatherCatalogueMapper.selectByExample(example);
    }

    public Long create(ConstructionSiteLogWeatherData record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = weatherDataMapper.insert(record);
        if (result > 0)
            return record.getId();
        else return null;
    }

    public boolean update(ConstructionSiteLogWeatherData record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = weatherDataMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = weatherDataMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    /**
     * Id is the same of {@link ConstructionSiteLog}
     *
     * @param id
     * @return
     */
    public ConstructionSiteLogWeatherDataEx findById(long id) {
        return weatherDataMapper.selectExByPrimaryKey(id);
    }


}
