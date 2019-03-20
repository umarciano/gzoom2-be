package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.UomRangeValues;
import it.mapsgroup.gzoom.service.UomRangeValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UomRangeValuesController {

    private final UomRangeValuesService uomRangeValuesService;

    @Autowired
    public UomRangeValuesController(UomRangeValuesService uomRangeValuesService) {
        this.uomRangeValuesService = uomRangeValuesService;
    }

    @RequestMapping(value = "uom-range-values/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<UomRangeValues> getUomRangeValues(@PathVariable(value = "id") String id) {
        return Exec.exec("uom-range-values/ get", () -> uomRangeValuesService.getUomRangeValues(id));
    }
}
