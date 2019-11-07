package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.mybatis.dao.VisitorDao;
import it.mapsgroup.gzoom.mybatis.dto.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;



/**
 * Profile service.
 *
 */
@Service
public class VisitorService {

    private VisitorDao visitorDao;

    @Autowired
    public VisitorService(VisitorDao visitorDao) {
        this.visitorDao = visitorDao;
    }

    public Result<Visitor> getVisitors() throws Exception {
        Method setNameMethod = VisitorDao.class.getMethod("selectVisit");
        List<Visitor> list = (List<Visitor>)setNameMethod.invoke(visitorDao);
        return new Result<>(list, list.size());
    }
}
