package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.PartyDao;
import it.mapsgroup.gzoom.querydsl.dao.PersonDao;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Profile service.
 *
 */
@Service
public class PartyService {
    private static final Logger LOG = getLogger(PartyService.class);

    private final PartyDao partyDao;
    private final PersonDao personDao;

    @Autowired
    public PartyService(PartyDao partyDao, PersonDao personDao) {
        this.partyDao = partyDao;
        this.personDao = personDao;
    }

    public Result<Person> getPersons() {
        List<Person> list = personDao.getPersons();
        return new Result<>(list, list.size());
    }

}
