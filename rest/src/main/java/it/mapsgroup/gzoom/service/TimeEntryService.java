package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Timesheet;
import it.mapsgroup.gzoom.querydsl.dao.TimeEntryDao;
import it.mapsgroup.gzoom.querydsl.dao.TimesheetDao;
import it.mapsgroup.gzoom.querydsl.dto.TimesheetEx;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 *
 */
@Service
public class TimeEntryService {
    private static final Logger LOG = getLogger(TimeEntryService.class);

    private final TimesheetDao timesheetDao;
    private final TimeEntryDao timeEntryDao;
    private final DtoMapper dtoMapper;

    @Autowired
    public TimeEntryService(TimesheetDao timesheetDao, TimeEntryDao timeEntryDao,
                            DtoMapper dtoMapper) {
        this.timesheetDao = timesheetDao;
        this.timeEntryDao = timeEntryDao;
        this.dtoMapper = dtoMapper;
    }

    public Result<TimeEntry> getTimeEntries(String id) {
        List<it.mapsgroup.gzoom.querydsl.dto.TimeEntry> list = timeEntryDao.getTimeEntries(id);
        List<TimeEntry> ret = list.stream().map(p -> dtoMapper.copy(p, new TimeEntry())).collect(Collectors.toList());
        return new Result<>(ret, ret.size());
    }

    public Result<TimeEntry> getWorkEfforts() {
        List<it.mapsgroup.gzoom.querydsl.dto.TimeEntry> list = timeEntryDao.getWorkEfforts();
        List<TimeEntry> ret = list.stream().map(p -> dtoMapper.copy(p, new TimeEntry())).collect(Collectors.toList());
        return new Result<>(ret, ret.size());
    }

    /*public String createTimesheet(Timesheet req) {
        Validators.assertNotNull(req, Messages.TIMESHEET_REQUIRED);
        Validators.assertNotBlank(req.getPartyId(), Messages.PARTY_ID_REQUIRED);
        it.mapsgroup.gzoom.querydsl.dto.Timesheet timesheet = new it.mapsgroup.gzoom.querydsl.dto.Timesheet();
        //timesheet.setTimesheetId(req.getTimesheetId());
        timesheet.setPartyId(req.getPartyId());
        timesheet.setFromDate(req.getFromDate().atStartOfDay());
        timesheet.setThruDate(req.getThruDate().atStartOfDay());
        timesheet.setActualHours(req.getActualHours());
        timesheet.setContractHours(req.getContractHours());
        timesheetDao.create(timesheet, principal().getUserLoginId());
        return req.getTimesheetId();
    }

    public String updateTimesheet(String id, Timesheet req) {
        Validators.assertNotNull(req, Messages.TIMESHEET_REQUIRED);
        Validators.assertNotBlank(req.getPartyId(), Messages.PARTY_ID_REQUIRED);

        it.mapsgroup.gzoom.querydsl.dto.Timesheet record = timesheetDao.getTimesheet(id);
        Validators.assertNotNull(record, Messages.INVALID_TIMESHEET);
        copy(req, record);
        timesheetDao.update(id, record, principal().getUserLoginId());
        return req.getTimesheetId();
    }

    public String deleteTimesheet(String id) {
        Validators.assertNotBlank(id, Messages.TIMESHEET_ID_REQUIRED);
        it.mapsgroup.gzoom.querydsl.dto.Timesheet record = timesheetDao.getTimesheet(id);
        Validators.assertNotNull(record, Messages.INVALID_TIMESHEET);
        timesheetDao.delete(id);
        return id;
    }

    public void copy( Timesheet from, it.mapsgroup.gzoom.querydsl.dto.Timesheet to) {
        to.setFromDate(from.getFromDate().atStartOfDay());
        to.setThruDate(from.getThruDate().atStartOfDay());
        to.setPartyId(from.getPartyId());
        to.setTimesheetId(from.getTimesheetId());
        to.setActualHours(from.getActualHours());
        to.setContractHours(from.getContractHours());
    }*/

}
