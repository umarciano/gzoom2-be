package it.mapsgroup.gzoom.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.Uom;
import it.mapsgroup.gzoom.querydsl.dto.UomEx;
import it.mapsgroup.gzoom.service.UomService;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UomController {

    private final UomService uomService;

    @Autowired
    public UomController(UomService uomService) {
        this.uomService = uomService;
    }

    @RequestMapping(value = "uom/value", method = RequestMethod.GET)
    @ResponseBody
    public Result<UomEx> getUoms() {
        return Exec.exec("uom/value get", () -> uomService.getUoms());
    }

    @RequestMapping(value = "uom/value", method = RequestMethod.POST)
    @ResponseBody
    public String createUom(@RequestBody Uom req) {
        return Exec.exec("uom/value post", () -> uomService.createUom(req));
    }

    @RequestMapping(value = "uom/value/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUom(@PathVariable(value = "id") String id, @RequestBody Uom req) {
        return Exec.exec("uom/value put", () -> uomService.updateUom(id, req));
    }

    @RequestMapping(value = "uom/value/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUom(@PathVariable(value = "id") String id) {
        return Exec.exec("uom/value delete", () -> uomService.deleteUom(id));
    }
}
