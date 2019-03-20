package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UomRangeValuesDao;
import it.mapsgroup.gzoom.querydsl.dto.UomRangeValues;
import it.mapsgroup.gzoom.rest.UomRangeValuesController;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UomRangeValuesService {

    private static final Logger LOG = getLogger(UomRangeValuesService.class);

    private final UomRangeValuesDao uomRangeValuesDao;

    @Autowired
    public UomRangeValuesService(UomRangeValuesDao uomRangeValuesDao) {
        this.uomRangeValuesDao = uomRangeValuesDao;
    }

    public Result<UomRangeValues> getUomRangeValues(String uomRangeId) {
        List<UomRangeValues> list = uomRangeValuesDao.getUomRangeValues(uomRangeId);
        return new Result<>(list, list.size());
    }
}
