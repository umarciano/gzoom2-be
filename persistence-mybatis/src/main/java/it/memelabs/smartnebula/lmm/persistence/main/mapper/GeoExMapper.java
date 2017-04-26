package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import java.util.List;

import it.memelabs.smartnebula.lmm.persistence.main.dto.GeoExample;
import org.apache.ibatis.session.RowBounds;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Geo;

public interface GeoExMapper extends GeoMapper {

	List<Geo> selectPostalCodes(String municipalityId, RowBounds rowBounds);

	List<Geo> selectExByExample(GeoExample example);

}
