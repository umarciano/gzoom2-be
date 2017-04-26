
package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLogWeatherDataEx;

public interface ConstructionSiteLogWeatherDataExMapper extends ConstructionSiteLogWeatherDataMapper {

    ConstructionSiteLogWeatherDataEx selectExByPrimaryKey(Long id);

}