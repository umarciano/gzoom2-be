package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.WorkEffortAssocTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAssocType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortAssocTypeExt;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Leonardo Minaudo.
 */
@Service
public class WorkEffortAssocTypeService {
    private static final Logger LOG = getLogger(WorkEffortAssocTypeService.class);

    private final WorkEffortAssocTypeDao workEffortAssocTypeDao;

    @Autowired
    public WorkEffortAssocTypeService(WorkEffortAssocTypeDao workEffortAssocTypeDao) {
        this.workEffortAssocTypeDao = workEffortAssocTypeDao;
    }

    public Result<WorkEffortAssocTypeExt> getWorkEffortAssocType() {
        List<WorkEffortAssocTypeExt> list = this.workEffortAssocTypeDao.getWorkEffortAssocTypeList();
        return new Result<>(list, list.size());
    }

    public boolean createWorkEffortAssocType(WorkEffortAssocType req) {
        Validators.assertNotNull(req, Messages.WORK_EFFORT_ASSOC_TYPE_REQUIRED);
        Validators.assertNotBlank(req.getDescription(), Messages.WORK_EFFORT_ASSOC_TYPE_DESCRIPTION_REQUIRED);
        return workEffortAssocTypeDao.create(req, principal().getUserLoginId());
    }

    public boolean updateWorkEffortAssocType(WorkEffortAssocType list) {
        Validators.assertNotBlank(list.getWorkEffortAssocTypeId(), Messages.WORK_EFFORT_ASSOC_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(list.getDescription(), Messages.WORK_EFFORT_ASSOC_TYPE_DESCRIPTION_REQUIRED);
        return workEffortAssocTypeDao.update(list, principal().getUserLoginId());
    }

    public boolean deleteWorkEffortAssocType(String[] id) {
        List<WorkEffortAssocType> listRecord = new ArrayList<WorkEffortAssocType>();

        for (String i : id) {
            Validators.assertNotBlank(i, Messages.WORK_EFFORT_ASSOC_TYPE_ID_REQUIRED);
            WorkEffortAssocType record = workEffortAssocTypeDao.get(i);
            Validators.assertNotNull(record, Messages.INVALID_WORK_EFFORT_ASSOC_TYPE);

            listRecord.add(record);
        }

        for (WorkEffortAssocType element : listRecord) {

            if (element.getParentTypeId() != null && isChild(element.getWorkEffortAssocTypeId(), listRecord)) {
                workEffortAssocTypeDao.delete(element.getWorkEffortAssocTypeId());

                listRecord.forEach((item) -> {
                    if (item.getParentTypeId() == element.getWorkEffortAssocTypeId()) {
                        item.setParentTypeId(null);
                    }
                });
            }
        }

        for (WorkEffortAssocType element : listRecord) {
            workEffortAssocTypeDao.delete(element.getWorkEffortAssocTypeId());
        }


        return true;
    }

    private boolean isChild(String id, List<WorkEffortAssocType> record) {
        for (WorkEffortAssocType element : record) {
            if (id.equals(element.getParentTypeId())) return false;
        }

        return true;
    }

}
