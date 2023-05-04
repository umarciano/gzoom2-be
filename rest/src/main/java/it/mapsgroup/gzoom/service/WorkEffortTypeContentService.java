package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortTypeContentDao;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WorkEffortTypeContentService {
    private final WorkEffortTypeContentDao workEffortTypeContentDao;

    @Autowired
    public WorkEffortTypeContentService(WorkEffortTypeContentDao workEffortTypeContentDao) {
        this.workEffortTypeContentDao = workEffortTypeContentDao;
    }

    public Map<String, String> getWorkEffortTypeContentParams(String workEffortTypeId, String contentId) {
        Map<String, String> workEffortTypeContentParams = workEffortTypeContentDao.getWorkEffortTypeContentParams(workEffortTypeId, contentId);
        return workEffortTypeContentParams;
    }
}
