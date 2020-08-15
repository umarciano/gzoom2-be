package it.mapsgroup.gzoom.rest;


import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dto.QueryConfig;
import it.mapsgroup.gzoom.service.QueryConfigService;
import it.mapsgroup.gzoom.service.QueryExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class QueryConfigController {

    private final QueryConfigService queryConfigService;
    private final QueryExecutorService queryExecutorService;

    @Autowired
    public QueryConfigController(QueryConfigService queryConfigService, QueryExecutorService queryExecutorService) {
        this.queryConfigService = queryConfigService;
        this.queryExecutorService = queryExecutorService;
    }

    @RequestMapping(value = "query-config/all", method = RequestMethod.GET)
    @ResponseBody
    public Result<QueryConfig> getAllQueryConfig() {
        return Exec.exec("get query-config",() -> queryConfigService.getAllQueryConfig(null,null));
    }

    @RequestMapping(value = "query-config/all/{parentTypeId}/{queryType}", method = RequestMethod.GET)
    @ResponseBody
    public Result<QueryConfig> getAllQueryConfigWithParent(@PathVariable(value = "parentTypeId") String parentTypeId,
                                                           @PathVariable(value = "queryType") String queryType) {
        return Exec.exec("get query-config",() -> queryConfigService.getAllQueryConfig(parentTypeId,queryType));
    }

    @RequestMapping(value = "query-config/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public QueryConfig getQueryConfig(@PathVariable(value = "id") String id) {
        return Exec.exec("get query-config with id",() -> queryConfigService.getQueryConfig(id));
    }

    @RequestMapping(value = "query-config/exec", method = RequestMethod.POST)
    @ResponseBody
    public String executeQuery(@RequestBody QueryConfig query, HttpServletRequest req, HttpServletResponse response) {
        return Exec.exec("execute query-config with id",() -> queryExecutorService.execQuery(query, req, response));
    }

}
