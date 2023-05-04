package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.CustomTimePeriodDao;
import it.mapsgroup.gzoom.querydsl.dto.CustomTimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomTimePeriodService {

    private final CustomTimePeriodDao customTimePeriodDao;

    @Autowired
    public CustomTimePeriodService(CustomTimePeriodDao customTimePeriodDao) {
        this.customTimePeriodDao = customTimePeriodDao;
    }

    public Result<CustomTimePeriod> getCustomTimePeriods(String periodTypeId) {
        List<CustomTimePeriod> list = customTimePeriodDao.getCustomTimePeriods(periodTypeId);
        return new Result<>(list, list.size());
    }
}
