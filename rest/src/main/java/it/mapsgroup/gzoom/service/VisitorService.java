package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.Visitor;
import it.mapsgroup.gzoom.querydsl.dao.VisitorDao;
import it.mapsgroup.gzoom.querydsl.dto.VisitorEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Profile service.
 *
 */
@Service
public class VisitorService {

    private DtoMapper dtoMapper;
    private VisitorDao visitorDao;

    @Autowired
    public VisitorService(DtoMapper dtoMapper, VisitorDao visitorDao) {
        this.dtoMapper = dtoMapper;
        this.visitorDao = visitorDao;
    }

    public Result<Visitor> getVisitors() {
        List<VisitorEx> visits = visitorDao.getVisitors();
        List<it.mapsgroup.gzoom.model.Visitor> ret = visits.stream().map(v -> dtoMapper.copy(v,  new it.mapsgroup.gzoom.model.Visitor())).collect(Collectors.toList());
        return new Result<>(ret, ret.size());
    }


}
