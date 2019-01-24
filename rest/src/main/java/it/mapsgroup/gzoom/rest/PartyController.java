package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import it.mapsgroup.gzoom.querydsl.dto.PartyEx;
import it.mapsgroup.gzoom.querydsl.dto.Person;
import it.mapsgroup.gzoom.service.PartyService;

import static it.mapsgroup.gzoom.security.Principals.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PartyController {

    private final PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @RequestMapping(value = "party/person", method = RequestMethod.GET)
    @ResponseBody
    public Result<Person> getPersons() {
        return Exec.exec("party/person get", () -> partyService.getPersons());
    }
    
    @RequestMapping(value = "party", method = RequestMethod.GET)
    @ResponseBody
    public Result<Party> getPartys() {
        return Exec.exec("party get", () -> partyService.getPartys());
    }
    
    @RequestMapping(value = "orgUnits/{parentTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<PartyEx> getOrgUnits(@PathVariable(value = "parentTypeId") String parentTypeId) {
        return Exec.exec("orgUnit get", () -> partyService.getOrgUnits(principal().getUserLoginId(), parentTypeId));
    }
    
    @RequestMapping(value = "party/roleType/{roleTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Party> getRoleTypePartys(@PathVariable(value = "roleTypeId") String roleTypeId) {
        return Exec.exec("party get", () -> partyService.getRoleTypePartys(roleTypeId));
    }

}
