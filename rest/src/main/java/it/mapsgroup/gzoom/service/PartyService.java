package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.PartyDao;
import it.mapsgroup.gzoom.querydsl.dao.PersonDao;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.PartyEx;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.querydsl.dto.PersonEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Profile service.
 *
 */
@Service
public class PartyService {

    private final PartyDao partyDao;
    private final PersonDao personDao;
    private final DtoMapper dtoMapper;

    @Autowired
    public PartyService(PartyDao partyDao, PersonDao personDao, DtoMapper dtoMapper) {
        this.partyDao = partyDao;
        this.personDao = personDao;
        this.dtoMapper = dtoMapper;
    }

    public Result<Person> getPersons() {
        List<Person> list = personDao.getPersons();
        return new Result<>(list, list.size());
    }
    
    public Result<Party> getPartys(String userLoginId, String parentTypeId) {
        List<Party> list = partyDao.getParties(userLoginId, parentTypeId);
        return new Result<>(list, list.size());
    }
    
    public Result<PartyEx> getOrgUnits(String userLoginId, String parentTypeId, String roleTypeId, String workEffortTypeId) {
        List<PartyEx> list = partyDao.getOrgUnits(userLoginId, parentTypeId, roleTypeId, workEffortTypeId);
        return new Result<>(list, list.size());
    }
    
    public Result<Party> getRoleTypePartys(String roleTypeId, String roleTypeIdFrom, String workEffortTypeId) {
        List<Party> list = partyDao.getRoleTypePartys(roleTypeId,roleTypeIdFrom,workEffortTypeId);
        return new Result<>(list, list.size());
    }

    public Result<Party> getRoleTypePartysBetween(String roleTypeId) {
        List<Party> list = partyDao.getRoleTypePartysBetween(roleTypeId);
        return new Result<>(list, list.size());
    }

    public Result<it.mapsgroup.gzoom.model.Person> getPartiesExposed() {
        List<PersonEx> list = partyDao.getPartiesExposed();
        List<it.mapsgroup.gzoom.model.Person> ret = list.stream().map(p -> dtoMapper.copy(p, new it.mapsgroup.gzoom.model.Person())).collect(Collectors.toList());
        return new Result<>(ret, ret.size());
    }
}
