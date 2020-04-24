package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.EnumerationDao;
import it.mapsgroup.gzoom.querydsl.dto.Enumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnumerationService {

    private final EnumerationDao enumerationDao;

    @Autowired
    public EnumerationService(EnumerationDao enumerationDao) {
        this.enumerationDao = enumerationDao;
    }

    public Result<Enumeration> getEnumerations(String enumTypeId) {
        List<Enumeration> list = enumerationDao.getEnumerations(enumTypeId);
        return new Result<>(list, list.size());
    }
}
