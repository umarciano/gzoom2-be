package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UomDao;
import it.mapsgroup.gzoom.querydsl.dto.Uom;
import it.mapsgroup.gzoom.querydsl.dto.UomEx;
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
    /*
    private final TimesheetDao timesheetDao;

    @Autowired
    public TimesheetService(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    public Result<UomEx> getTimesheets() {
        //List<UomEx> list = uomDao.getUoms();
        //return new Result<>(list, list.size());
        return null;
    }
    
*/
}
