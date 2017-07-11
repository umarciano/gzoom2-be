package it.mapsgroup.gzoom.rest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.UomRatingScale;
import it.mapsgroup.gzoom.querydsl.dto.UomRatingScaleEx;
import it.mapsgroup.gzoom.service.UomRatingScaleService;

/**
 */
@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UomRatingScaleController {

    private final UomRatingScaleService uomRatingScaleService;

    @Autowired
    public UomRatingScaleController(UomRatingScaleService uomRatingScaleService) {
        this.uomRatingScaleService = uomRatingScaleService;
    }

    @RequestMapping(value = "uom/scale/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<UomRatingScaleEx> getUomRatingScales(@PathVariable(value = "id") String id) {
        return Exec.exec("uom/scale get", () -> uomRatingScaleService.getUomRatingScales(id));
    }
    
    @RequestMapping(value = "uom/scale/{id}/{value}", method = RequestMethod.GET)
    @ResponseBody
    public UomRatingScaleEx getUomRatingScale(@PathVariable(value = "id") String id, @PathVariable(value = "value") BigDecimal value) {
        return Exec.exec("uom/scale get", () -> uomRatingScaleService.getUomRatingScale(id, value));
    }

    @RequestMapping(value = "uom/scale", method = RequestMethod.POST)
    @ResponseBody
    public String createUomRatingScale(@RequestBody UomRatingScale req) {
        return Exec.exec("uom/scale post", () -> uomRatingScaleService.createUomRatingScale(req));
    }

    @RequestMapping(value = "uom/scale/{id}/{value}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUomRatingScale(@PathVariable(value = "id") String id, @PathVariable(value = "value") BigDecimal value, @RequestBody UomRatingScale req) {
        return Exec.exec("uom/scale put", () -> uomRatingScaleService.updateUomRatingScale(id, value, req));
    }

    @RequestMapping(value = "uom/scale/{id}/{value}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUomRatingScale(@PathVariable(value = "id") String id, @PathVariable(value = "value") BigDecimal value) {
        return Exec.exec("uom/scale delete", () -> uomRatingScaleService.deleteUomRatingScale(id, value));
    }
}
