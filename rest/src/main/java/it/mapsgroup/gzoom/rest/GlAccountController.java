package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.service.GlAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class GlAccountController {

    private final GlAccountService glAccountService;

    @Autowired
    public GlAccountController(GlAccountService glAccountService) {
        this.glAccountService = glAccountService;
    }

    @RequestMapping(value = "glaccount-precision", method = RequestMethod.GET)
    @ResponseBody
    public BigInteger getDecimalPrecision() {
        return Exec.exec("get glaccount-precision", () -> glAccountService.getDecimalPrecision());
    }
}
