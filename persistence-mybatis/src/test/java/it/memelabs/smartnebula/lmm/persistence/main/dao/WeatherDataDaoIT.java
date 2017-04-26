package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.util.Dummies;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

@Transactional
public class WeatherDataDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(WeatherDataDaoIT.class);

    @Autowired
    private WeatherDataDao weatherDataDao;
    @Autowired
    private ConstructionSiteLogDao cslDao;
    @Autowired
    private ConstructionSiteDao csDao;


    @Test
    public void testFindCatalogue() throws Exception {
        List<ConstructionSiteLogWeatherCatalog> ret = weatherDataDao.findAll(Dummies.getUserLogin());
        assertThat("Catalog is empty", ret.size(), is(greaterThan(0)));
    }

    @Test
    public void testFindById() throws Exception {
        ConstructionSiteEx cs = getConstructionSite();
        Long cslId = createCsl(cs);
        ConstructionSiteLogWeatherData record = new ConstructionSiteLogWeatherData();
        record.setId(cslId);
        record.setWeatherConditionId(101L);
        record.setMinTemperature(new BigDecimal(-5));
        record.setMaxTemperature(new BigDecimal(+15));
        record.setWindTypeId(121L);
        record.setWindSpeed(new BigDecimal(66));
        record.setRainfall(new BigDecimal(55));
        record.setActivitySuspensionId(143L);
        Long wdId = weatherDataDao.create(record, Dummies.getUserLogin());
        ConstructionSiteLogWeatherDataEx ret = weatherDataDao.findById(wdId);
        assertThat("ConstructionSiteLogWeatherData not found", ret, is(not(nullValue())));
        assertThat("ActivitySuspension not found", ret.getActivitySuspension(), is(not(nullValue())));
        assertThat("ActivitySuspension not found", ret.getActivitySuspension().getId(), is(record.getActivitySuspensionId()));
        assertThat("WindType not found", ret.getWindType(), is(not(nullValue())));
        assertThat("WindType not found", ret.getWindType().getId(), is(record.getWindTypeId()));
        assertThat("WeatherCondition not found", ret.getWeatherCondition(), is(not(nullValue())));
        assertThat("WeatherCondition not found", ret.getWeatherCondition().getId(), is(record.getWeatherConditionId()));
        assertThat("MinTemperature", ret.getMinTemperature(), is(closeTo(record.getMinTemperature(), BigDecimal.ZERO)));
        assertThat("MaxTemperature", ret.getMaxTemperature(), is(closeTo(record.getMaxTemperature(), BigDecimal.ZERO)));
        assertThat("WindSpeed", ret.getWindSpeed(), is(closeTo(record.getWindSpeed(), BigDecimal.ZERO)));
        assertThat("Rainfall", ret.getRainfall(), is(closeTo(record.getRainfall(), BigDecimal.ZERO)));
        weatherDataDao.delete(wdId);
        ret = weatherDataDao.findById(wdId);
        assertThat("ConstructionSiteLogWeatherData not deleted", ret, is(nullValue()));
        LOG.info("Test OK.");
    }

    private Long createCsl(ConstructionSiteEx cs) {
        ConstructionSiteLog record = new ConstructionSiteLog();
        record.setConstructionSiteId(cs.getId());
        return cslDao.create(record, Dummies.getUserLogin());
    }

    private ConstructionSiteEx getConstructionSite() {
        ConstructionSiteFilter filter = new ConstructionSiteFilter();
        filter.setPage(1);
        filter.setSize(10);
        return csDao.findByFilter(filter, Dummies.getUserLogin()).first().get(0);
    }


}
