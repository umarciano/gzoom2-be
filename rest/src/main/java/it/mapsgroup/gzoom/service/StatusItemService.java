package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.StatusItemDao;
import it.mapsgroup.gzoom.querydsl.dto.StatusItemExt;

@Service
public class StatusItemService {
	private static final Logger LOG = getLogger(StatusItemService.class);
	
	private final StatusItemDao statusItemDao;

    @Autowired
    public StatusItemService(StatusItemDao statusItemDao) {
        this.statusItemDao = statusItemDao;
    }

    public Result<StatusItemExt> getStatusItems(String parentTypeId) {
        List<StatusItemExt> list = statusItemDao.getStatusItems(parentTypeId);
        return new Result<>(list, list.size());
    }
}
