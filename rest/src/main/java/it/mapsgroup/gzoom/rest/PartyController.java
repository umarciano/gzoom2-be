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
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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

    @RequestMapping(value = "party/partiesExposed", method = RequestMethod.GET)
    @ResponseBody
    public Result<it.mapsgroup.gzoom.model.Person> getPartiesExposed() {
        return Exec.exec("party get", () -> partyService.getPartiesExposed());
    }

    @RequestMapping(value = "party/{parentTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Party> getPartys(@PathVariable(value = "parentTypeId") String parentTypeId) {
        return Exec.exec("party get", () -> partyService.getPartys(principal().getUserLoginId(), parentTypeId));
    }
    
    @RequestMapping(value = "orgUnits/{parentTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<PartyEx> getOrgUnits(@PathVariable(value = "parentTypeId") String parentTypeId,
    @RequestParam Map<String,String> requestParams) {

        String roleTypeId = requestParams.get("roleTypeId");
        String workEffortTypeId = requestParams.get("workEffortTypeId");
        return Exec.exec("orgUnit get", () -> partyService.getOrgUnits(principal().getUserLoginId(), parentTypeId, roleTypeId, workEffortTypeId));
    }
    
    @RequestMapping(value = "party/roleType/{roleTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Party> getRoleTypePartys(@PathVariable(value = "roleTypeId") String roleTypeId,
    @RequestParam Map<String,String> requestParams) {
        String roleTypeIdFrom = requestParams.get("roleTypeIdFrom");
        String workEffortTypeId = requestParams.get("workEffortTypeId");
        return Exec.exec("party get", () -> partyService.getRoleTypePartys(roleTypeId,roleTypeIdFrom,workEffortTypeId));
    }

    @GetMapping("party/roleType/between/{roleTypeId}")
    @ResponseBody
    public Result<Party> getRoleTypePartysBetween(@PathVariable(value = "roleTypeId") String roleTypeId) {
        return Exec.exec("party get between", () -> partyService.getRoleTypePartysBetween(roleTypeId));
    }

}
