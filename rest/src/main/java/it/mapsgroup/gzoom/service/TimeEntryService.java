package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.model.Activity;
import it.mapsgroup.gzoom.querydsl.dao.TimeEntryDao;
import it.mapsgroup.gzoom.querydsl.dao.TimesheetDao;
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

    public Result<Activity> getWorkEfforts(String id) {
        List<it.mapsgroup.gzoom.querydsl.dto.Activity> list = timeEntryDao.getWorkEfforts(id);
        List<Activity> ret = list.stream().map(p -> dtoMapper.copy(p, new Activity())).collect(Collectors.toList());
        return new Result<>(ret, ret.size());
    }

    public String createOrUpdateTimeEntry(List<TimeEntry> reqList) {
        Validators.assertFalse(reqList.isEmpty(), Messages.INVALID_TIME_ENTRY);
        for (TimeEntry req : reqList) {
            Validators.assertNotNull(req.getTimesheetId(), Messages.INVALID_TIMESHEET);
            it.mapsgroup.gzoom.querydsl.dto.TimeEntry timeEntry = new it.mapsgroup.gzoom.querydsl.dto.TimeEntry();
            timeEntry.setWorkEffortId(req.getWorkEffortId());
            timeEntry.setPercentage(req.getPercentage());
            timeEntry.setFromDate(req.getFromDate());
            timeEntry.setThruDate(req.getThruDate());

            if (req.getTimeEntryId() != null) {
                updateTimeEntry(req.getTimeEntryId(), req);
            } else {
                timeEntry.setTimeEntryId(req.getTimeEntryId());
                timeEntry.setTimesheetId(req.getTimesheetId());
                timeEntryDao.create(timeEntry, principal().getUserLoginId());
            }

        }
        return reqList.get(0).getTimeEntryId();
    }

    public String updateTimeEntry(String id, TimeEntry req) {
        Validators.assertNotNull(req.getTimeEntryId(), Messages.TIME_ENTRY_ID_REQUIRED);
        it.mapsgroup.gzoom.querydsl.dto.TimeEntry record = timeEntryDao.getTimeEntry(id);
        Validators.assertNotNull(record, Messages.INVALID_TIME_ENTRY);
        copy(req, record);
        timeEntryDao.update(id, record, principal().getUserLoginId());
        return req.getTimeEntryId();
    }

    public String deleteTimeEntry(String id) {
        Validators.assertNotBlank(id, Messages.TIMESHEET_ID_REQUIRED);
        it.mapsgroup.gzoom.querydsl.dto.Timesheet record = timesheetDao.getTimesheet(id);
        Validators.assertNotNull(record, Messages.INVALID_TIMESHEET);
        timesheetDao.delete(id);
        return id;
    }

    public void copy( TimeEntry from, it.mapsgroup.gzoom.querydsl.dto.TimeEntry to) {
        to.setFromDate(from.getFromDate());
        to.setThruDate(from.getThruDate());
        to.setPercentage(from.getPercentage());
        to.setWorkEffortId(from.getWorkEffortId());
    }

}
