package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.EnumerationDao;
import it.mapsgroup.gzoom.querydsl.dao.QueryConfigDao;
import it.mapsgroup.gzoom.querydsl.dto.Enumeration;
import it.mapsgroup.gzoom.querydsl.dto.QueryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryConfigService {

    private final QueryConfigDao queryConfigDao;

    @Autowired
    public QueryConfigService(QueryConfigDao queryConfigDao) {
        this.queryConfigDao = queryConfigDao;
    }

    public Result<QueryConfig> getAllQueryConfig(String parentTypeId,String queryType, String userLoginId) {
        List<QueryConfig> list = queryConfigDao.getAllQueryConfig(parentTypeId,queryType, userLoginId);
        return new Result<>(list, list.size());
    }

    public QueryConfig getQueryConfig(String id) {
        QueryConfig query = queryConfigDao.getQueryConfig(id);
        //return new Result<>(query, query.size());
        return query;
    }
}
