package it.mapsgroup.gzoom.querydsl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.mapsgroup.gzoom.querydsl.dto.SecurityPermission;

/**
 * @author Andrea Fossi.
 */

public class PermissionDaoIT extends AbstractDaoTest {
    @Autowired
    PermissionDao permissionDao;

    @Test
    @Transactional
    public void findByUsername() throws Exception {
        List<SecurityPermission> permissions2 = permissionDao.getPermission("admin");
        assertNotNull(permissions2);
        assertEquals(2, permissions2.size());
    }
}
