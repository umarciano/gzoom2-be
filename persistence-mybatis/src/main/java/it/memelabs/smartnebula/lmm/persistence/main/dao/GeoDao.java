package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Geo;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GeoExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GeoExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.GeoExMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoDao {
    // private static final org.slf4j.Logger LOG = getLogger(GeoDao.class);

    private final GeoExMapper geoMapper;

    @Autowired
    public GeoDao(GeoExMapper geoMapper) {
        this.geoMapper = geoMapper;
    }

    public Geo findById(String id) {
        return geoMapper.selectByPrimaryKey(id);
    }

    public List<Geo> findCountries(Integer size) {
        GeoExample example = new GeoExample();
        example.createCriteria().andGeoTypeIdEqualTo("COUNTRY");
        example.setOrderByClause("geo_name, geo_id");
        return geoMapper.selectByExampleWithRowbounds(example, getRowBounds(size, null));
    }

    public List<Geo> findMunicipalities(Integer size, String filterText) {
        GeoExample example = new GeoExample();
        GeoExample.Criteria criteria = example.createCriteria();
        criteria.andGeoTypeIdEqualTo("MUNICIPALITY");
        criteria.andGeoIdLike("IT-GN-%");
        if (StringUtils.isNotBlank(filterText)) {
            criteria.andGeoNameLikeInsensitive(filterText + "%");
        }
        example.setOrderByClause("geo_name, geo_id");
        return geoMapper.selectByExampleWithRowbounds(example, getRowBounds(size, 30));
    }

    public Geo findMunicipality(String geo_id) {
        GeoExample example = new GeoExample();
        GeoExample.Criteria criteria = example.createCriteria();
        criteria.andGeoTypeIdEqualTo("MUNICIPALITY");
        criteria.andGeoIdLike("IT-GN-%");
        if (StringUtils.isNoneBlank(geo_id)) {
            criteria.andGeoIdEqualTo(geo_id);
        } else {
            return null;
        }
        example.setOrderByClause("geo_name, geo_id");
        List<Geo> results = geoMapper.selectByExample(example);
        return (results.size() > 0) ? results.get(0) : null;
    }

    public List<Geo> findPostalCodes(Integer size, String municipalityId) {
        return geoMapper.selectPostalCodes(municipalityId, getRowBounds(size, null));
    }

    private RowBounds getRowBounds(Integer size, Integer defaultValue) {
        return new RowBounds(0, size != null ? size : (defaultValue != null ? defaultValue : Integer.MAX_VALUE));
    }

    public Geo findMunicipalityProvince(String municipalityGeoId) {
        GeoExExample example = new GeoExExample();
        GeoExExample.Criteria criteria = example.createCriteria();
        criteria.andGeoTypeIdEqualTo("PROVINCE");
        criteria.andGeoIdLike("IT-GN-%");
        if (municipalityGeoId != null) {
            example.andGeoAssGeoIdToEqualTo(criteria, municipalityGeoId);
        } else {
            return null;
        }
        List<Geo> results = geoMapper.selectExByExample(example);
        return (results.size() > 0) ? results.get(0) : null;
    }

    public Geo findProvinceRegion(String provinceGeoId) {
        GeoExExample example = new GeoExExample();
        GeoExExample.Criteria criteria = example.createCriteria();
        criteria.andGeoTypeIdEqualTo("REGION");
        criteria.andGeoIdLike("IT-GN-%");
        if (provinceGeoId != null) {
            example.andGeoAssGeoIdToEqualTo(criteria, provinceGeoId);
        } else {
            return null;
        }
        List<Geo> results = geoMapper.selectExByExample(example);
        return (results.size() > 0) ? results.get(0) : null;
    }
}
