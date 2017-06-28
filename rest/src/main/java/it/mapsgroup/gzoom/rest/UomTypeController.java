package it.mapsgroup.gzoom.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.UomType;
import it.mapsgroup.gzoom.service.UomTypeService;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UomTypeController {

    private final UomTypeService uomTypeService;

    @Autowired
    public UomTypeController(UomTypeService uomTypeService) {
        this.uomTypeService = uomTypeService;
    }

    @RequestMapping(value = "uom/types", method = RequestMethod.GET)
    @ResponseBody
    public Result<UomType> getUomTypes() {
        return Exec.exec("uom/types", () -> uomTypeService.getUomTypes());
    }

    @RequestMapping(value = "uom/types", method = RequestMethod.POST)
    @ResponseBody
    public String createUomType(@RequestBody UomType req) {
        return Exec.exec("uom/types", () -> uomTypeService.createUomType(req));
    }

    @RequestMapping(value = "uom/types/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUomType(@PathVariable(value = "id") String id, @RequestBody UomType req) {
        return Exec.exec("uom/types", () -> uomTypeService.updateUomType(id, req));
    }

    @RequestMapping(value = "uom/types/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUomType(@PathVariable(value = "id") String id) {
        return Exec.exec("uom/types", () -> uomTypeService.deleteUomType(id));
    }
}
