package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.CustomTimePeriod;
import it.mapsgroup.gzoom.service.CustomTimePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomTimePeriodController {

    private final CustomTimePeriodService customTimePeriodService;

    @Autowired
    public CustomTimePeriodController(CustomTimePeriodService customTimePeriodService) {
        this.customTimePeriodService = customTimePeriodService;
    }

    @RequestMapping(value = "customtimeperiods/{periodTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<CustomTimePeriod> getCustomTimePeriods(@PathVariable(value = "periodTypeId") String periodTypeId) {
        return Exec.exec("get customTimePeriods",() -> customTimePeriodService.getCustomTimePeriods(periodTypeId));
    }

}
