package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.GlFiscalType;
import it.mapsgroup.gzoom.querydsl.dto.GlFiscalType;
import it.mapsgroup.gzoom.querydsl.dto.GlFiscalTypeEx;
import it.mapsgroup.gzoom.service.GlFiscalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leonardo Minaudo.
 */
@RestController
@RequestMapping(value = "gl-fiscal-type", produces = { MediaType.APPLICATION_JSON_VALUE })
public class GlFiscalTypeController {
    private final GlFiscalTypeService glFiscalTypeService;

    @Autowired
    public GlFiscalTypeController(GlFiscalTypeService glFiscalTypeService) {
        this.glFiscalTypeService = glFiscalTypeService;
    }

    @GetMapping
    @ResponseBody
    public Result<GlFiscalType> getGlFiscalType(){
        return Exec.exec("get gl-fiscal-type", () -> this.glFiscalTypeService.getGlFiscalType() );
    }

    @GetMapping("/detection-type")
    @ResponseBody
    public Result<GlFiscalTypeEx> getDetectionType(){
        return Exec.exec("get detection-type", () -> this.glFiscalTypeService.getDetectionType() );
    }

    @PostMapping
    @ResponseBody
    public boolean createGlFiscalType(@RequestBody GlFiscalType req){
        return Exec.exec("create gl-fiscal-type", () -> this.glFiscalTypeService.createGlFiscalType(req) );
    }

    @PutMapping
    @ResponseBody
    public boolean updateGlFiscalType(@RequestBody GlFiscalType req){
        return Exec.exec("update gl-fiscal-type", () -> this.glFiscalTypeService.updateGlFiscalType(req) );
    }

    @DeleteMapping(value = "/{glFiscalTypeId}")
    @ResponseBody
    public boolean deleteGlFiscalType(@PathVariable(value = "glFiscalTypeId") String id){
        return Exec.exec("delete gl-fiscal-type list", () -> this.glFiscalTypeService.deleteGlFiscalType(id) );
    }

}
