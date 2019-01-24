package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortDao;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;

@Service
public class WorkEffortService {
	private static final Logger LOG = getLogger(WorkEffortService.class);
	
	private final WorkEffortDao workEffortDao;
	
	@Autowired
    public WorkEffortService(WorkEffortDao workEffortDao) {
        this.workEffortDao = workEffortDao;
    }

    public Result<WorkEffort> getWorkEfforts(String userLoginId, String parentTypeId, String workEffortTypeId, boolean useFilter) {
        List<WorkEffort> list = workEffortDao.getWorkEfforts(userLoginId, parentTypeId, workEffortTypeId, useFilter);
        return new Result<>(list, list.size());
    }
    
    public Result<WorkEffort> getWorkEfforts() {
        List<WorkEffort> list = workEffortDao.getWorkEfforts();
        return new Result<>(list, list.size());
    }
    
    public Result<WorkEffort> getWorkEffortParents(String workEffortParentId) {
        List<WorkEffort> list = workEffortDao.getWorkEffortParents(workEffortParentId);
        return new Result<>(list, list.size());
    }
    
}
