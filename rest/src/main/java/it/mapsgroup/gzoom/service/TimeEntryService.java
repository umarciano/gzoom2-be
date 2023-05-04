package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.model.TimeEntry;
import it.mapsgroup.gzoom.querydsl.dao.TimeEntryDao;
import it.mapsgroup.gzoom.querydsl.dao.TimesheetDao;
import it.mapsgroup.gzoom.querydsl.dto.TimeEntryEx;
import it.mapsgroup.gzoom.querydsl.dto.TimesheetEx;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffort;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static it.mapsgroup.gzoom.security.Principals.username;
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

    public Result<TimesheetEx> getTimesheet(String id, String userLoginId) throws SQLException {
        List<TimesheetEx> list = timeEntryDao.getTimesheet(id,userLoginId );
        return new Result<>(list, list.size());
    }

    public Result<TimeEntryEx> getTimeEntries(String id) {
        List<TimeEntryEx> list = timeEntryDao.getTimeEntries(id);
        return new Result<>(list, list.size());
    }

    public Result<WorkEffort> getWorkEfforts(String id) {
        List<WorkEffort> list = timeEntryDao.getWorkEfforts(id);
        return new Result<>(list, list.size());
    }

    public Boolean updateTimeEntry(List<TimeEntry> array) {

        final BigDecimal[] sumActualHours = {new BigDecimal(0)};
        final BigDecimal[] sumPlanHours = {new BigDecimal(0)};
        array.forEach((item) -> {
            BigDecimal sumActualHourstemp = sumActualHours[0];
            BigDecimal sumPlanHourstemp = sumPlanHours[0];
            sumActualHours[0] = sumActualHourstemp.add(item.getHours());
            sumPlanHours[0] = sumPlanHourstemp.add(item.getPlanHours());

            if (item.getTimeEntryId() != null && !item.getTimeEntryId().substring(0,3).equals("new") ) {
                Validators.assertNotNull(item.getTimeEntryId(), Messages.TIME_ENTRY_ID_REQUIRED);
                it.mapsgroup.gzoom.querydsl.dto.TimeEntry record = timeEntryDao.getTimeEntry(item.getTimeEntryId());
                Validators.assertNotNull(record, Messages.INVALID_TIME_ENTRY);
                copy(item, record);
                timeEntryDao.update(item.getTimeEntryId(), record, principal().getUserLoginId());
            } else if(item.getTimeEntryId().substring(0,3).equals("new")) {
                it.mapsgroup.gzoom.querydsl.dto.TimeEntry timeEntry = new it.mapsgroup.gzoom.querydsl.dto.TimeEntry();
                timeEntry.setWorkEffortId(item.getWorkEffortId());
                timeEntry.setPercentage(item.getPercentage());
                timeEntry.setFromDate(item.getFromDate());
                timeEntry.setThruDate(item.getThruDate());
                timeEntry.setTimeEntryId(item.getTimeEntryId());
                timeEntry.setTimesheetId(item.getTimesheetId());
                timeEntry.setPlanHours(item.getPlanHours());
                timeEntry.setHours(item.getHours());
                timeEntry.setComments(item.getComments());
                timeEntry.setEffortUomId(item.getEffortUomId());
                timeEntry.setRateTypeId(item.getRateTypeId());
                timeEntry.setPartyId(item.getPartyId());
                timeEntryDao.create(timeEntry, principal().getUserLoginId());
            }


        });
        String id = array.get(0).getTimesheetId();

        List<TimesheetEx>  timesheetex = null;
        try {
            timesheetex = timeEntryDao.getTimesheet(id,username());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        it.mapsgroup.gzoom.querydsl.dto.Timesheet timesheet = timesheetex.get(0);

        timesheet.setActualHours(sumActualHours[0]);
        timesheet.setContractHours(sumPlanHours[0]);

        timesheetDao.update(id,timesheet);
        return true;
    }

    public Boolean deleteTimeEntry(String[] data) {
            for(String id : data ) {
                if (id != null && !id.substring(0,3).equals("new")) {
                    Validators.assertNotBlank(id, Messages.TIME_ENTRY_ID_REQUIRED);
                    it.mapsgroup.gzoom.querydsl.dto.TimeEntry record = timeEntryDao.getTimeEntry(id);
                    Validators.assertNotNull(record, Messages.INVALID_TIME_ENTRY);
                    timeEntryDao.delete(id);
                }
            }
        return true;
    }

    public void copy( TimeEntry from, it.mapsgroup.gzoom.querydsl.dto.TimeEntry to) {
        to.setFromDate(from.getFromDate());
        to.setThruDate(from.getThruDate());
        to.setPercentage(from.getPercentage());
        to.setWorkEffortId(from.getWorkEffortId());
        to.setComments(from.getComments());
        to.setHours(from.getHours());
        to.setPlanHours(from.getPlanHours());
    }
}
