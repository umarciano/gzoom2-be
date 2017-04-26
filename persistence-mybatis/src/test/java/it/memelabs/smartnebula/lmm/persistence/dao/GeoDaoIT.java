package it.memelabs.smartnebula.lmm.persistence.dao;

import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dao.GeoDao;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Geo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * @author Andrea Fossi.
 */
@Transactional
public class GeoDaoIT extends AbstractDaoTestIT{
    @Autowired
    private GeoDao geoDao;

    @Test
    public void testFindCountries() throws Exception {
        List<Geo> ret = geoDao.findCountries(30);
        assertTrue(ret.size() > 0);

    }

    @Test
    public void testFindMunicipalities() throws Exception {
        List<Geo> ret = geoDao.findMunicipalities(30,"");
        assertTrue(ret.size() > 0);
    }

    @Test
    public void testFindPostalCode() throws Exception {
        List<Geo> ret = geoDao.findPostalCodes(30,"IT-GN-100005");
        assertTrue(ret.size() > 0);
    }
}
