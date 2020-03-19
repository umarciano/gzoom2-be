package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Timesheet;

import it.mapsgroup.gzoom.querydsl.dao.UserPreferenceDao;
import it.mapsgroup.gzoom.querydsl.dto.UserPreference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.validation.Validator;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserPreferenceService {

    private static final Logger LOG = getLogger(TimesheetService.class);

    private final UserPreferenceDao userPreferenceDao;

    @Autowired
    public UserPreferenceService(UserPreferenceDao userPreferenceDao) {
        this.userPreferenceDao = userPreferenceDao;
    }

    public String updateUserPreference(UserPreference req) {
        Validators.assertNotNull(req, Messages.USER_PREFERENCE_REQUIRED);
        Validators.assertNotNull(req.getUserLoginId(), Messages.USER_LOGIN_ID_REQUIRED);
        Validators.assertNotNull(req.getUserPrefTypeId(), Messages.USER_PREFERENCE_TYPE_ID_REQUIRED);

        userPreferenceDao.update(principal().getUserLoginId(), req);
        return req.getUserLoginId();
    }

}
