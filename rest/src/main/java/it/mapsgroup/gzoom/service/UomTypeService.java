package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UomTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.UomType;

/**
 * Profile service.
 *
 */
@Service
public class UomTypeService {
    private static final Logger LOG = getLogger(UomTypeService.class);

    private final UomTypeDao uomTypeDao;

    @Autowired
    public UomTypeService(UomTypeDao uomTypeDao) {
        this.uomTypeDao = uomTypeDao;
    }

    public Result<UomType> getUomTypes() {
        List<UomType> list = uomTypeDao.getUomTypes();
        return new Result<>(list, list.size()); // TODO gestire count con paginazione
    }

    public String createUomType(UomType req) {
        uomTypeDao.create(req);
        return req.getUomTypeId();
    }

    public String updateUomType(String id, UomType req) {
        uomTypeDao.update(req);
        return id;
    }

    public String deleteUomType(String id) {
        UomType record = uomTypeDao.getUomType(id);
        uomTypeDao.delete(record);
        return id;
    }
}
