package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImageEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import it.memelabs.smartnebula.lmm.persistence.main.util.Dummies;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class GalleryImageDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(GalleryImageDaoIT.class);

    @Autowired
    private GalleryImageDao imageDao;


    @Test
    public void testFindByFilter() throws Exception {
        UserLogin userLogin = Dummies.getUserLogin();
        GalleryImageFilter filter = new GalleryImageFilter();
        filter.setPage(1);
        filter.setSize(1000);
        Tuple2<List<GalleryImageEx>, Integer> ret = imageDao.findByFilter(filter, userLogin);
        System.out.println("size=" + (ret != null ? ret.first().size() : null));
        Long id = ret.first().get(0).getId();
        GalleryImageEx record = imageDao.findById(id);
        System.out.print(record.getId());

    }


}
