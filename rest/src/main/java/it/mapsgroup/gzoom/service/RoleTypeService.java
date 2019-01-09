package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.RoleTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.RoleType;

@Service
public class RoleTypeService {
	private static final Logger LOG = getLogger(RoleTypeService.class);
	
	private final RoleTypeDao roleTypeDao;

    @Autowired
    public RoleTypeService(RoleTypeDao roleTypeDao) {
        this.roleTypeDao = roleTypeDao;
    }

    public Result<RoleType> getRoleTypes() {
        List<RoleType> list = roleTypeDao.getRoleTypes();
        return new Result<>(list, list.size());
    }
}
