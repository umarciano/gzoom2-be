package it.mapsgroup.gzoom.querydsl.dao;

import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;
import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import it.mapsgroup.gzoom.querydsl.persistence.service.QueryDslPersistenceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = QueryDslPersistenceConfiguration.class)
@TestPropertySource("/gzoom.properties")
public class PermissionDaoIT {
    @Autowired
    PermissionDao permissionDao;

    @Test
    @Transactional
    public void findByUsername() throws Exception {
        List<SecurityPermission> permissions = permissionDao.getPermission("admin");
        assertNotNull(permissions);
        assertEquals(74, permissions.size());
    }
}
