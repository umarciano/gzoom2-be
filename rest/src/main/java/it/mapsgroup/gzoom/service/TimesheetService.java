package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.TimesheetDao;
import it.mapsgroup.gzoom.querydsl.dto.Timesheet;
import it.mapsgroup.gzoom.rest.ValidationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 *
 */
@Service
public class TimesheetService {
    private static final Logger LOG = getLogger(TimesheetService.class);

    private final TimesheetDao timesheetDao;

    @Autowired
    public TimesheetService(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    public Result<Timesheet> getTimesheets() {
        List<Timesheet> list = timesheetDao.getTimesheets();
        return new Result<>(list, list.size());
    }

    public String createTimesheet(Timesheet req) {
        Validators.assertNotNull(req, Messages.TIMESHEET_REQUIRED);
        Validators.assertNotBlank(req.getPartyId(), Messages.PARTY_ID_REQUIRED);
        timesheetDao.create(req, principal().getUserLoginId());
        return req.getTimesheetId();
    }

    public String updateTimesheet(String id, Timesheet req) {
        Validators.assertNotNull(req, Messages.TIMESHEET_REQUIRED);
        Validators.assertNotBlank(req.getPartyId(), Messages.PARTY_ID_REQUIRED);
        Timesheet record = timesheetDao.getTimesheet(id);
        Validators.assertNotNull(record, Messages.INVALID_TIMESHEET);
        timesheetDao.update(id, req, principal().getUserLoginId());
        return req.getTimesheetId();
    }

    public String deleteTimesheet(String id) {
        Validators.assertNotBlank(id, Messages.TIMESHEET_ID_REQUIRED);
        Timesheet record = timesheetDao.getTimesheet(id);
        Validators.assertNotNull(record, Messages.INVALID_TIMESHEET);
        timesheetDao.delete(id);
        return id;
    }
}
