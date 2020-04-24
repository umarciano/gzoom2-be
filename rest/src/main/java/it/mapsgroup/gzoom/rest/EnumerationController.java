package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.Enumeration;
import it.mapsgroup.gzoom.service.EnumerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EnumerationController {

    private final EnumerationService enumerationService;

    @Autowired
    public EnumerationController(EnumerationService enumerationService) {
        this.enumerationService = enumerationService;
    }

    @RequestMapping(value = "enumeration/{enumTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Enumeration> getEnumeration(@PathVariable(value = "enumTypeId") String enumTypeId) {
        return Exec.exec("get enumeration",() -> enumerationService.getEnumerations(enumTypeId));
    }

}
