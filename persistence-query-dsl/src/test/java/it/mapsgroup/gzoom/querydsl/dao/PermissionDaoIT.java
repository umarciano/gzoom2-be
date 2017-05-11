package it.mapsgroup.gzoom.querydsl.dao;

import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

/**
 * @author Andrea Fossi.
 */

public class PermissionDaoIT extends AbstractDaoTest {
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
