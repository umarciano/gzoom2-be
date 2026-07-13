package it.mapsgroup.gzoom.rest;

import it.mapsgroup.gzoom.common.Exec;
import it.mapsgroup.gzoom.model.EmailLogEntry;
import it.mapsgroup.gzoom.model.EmailRule;
import it.mapsgroup.gzoom.model.EmailRuleCustom;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.service.EmailSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EmailSystemController {

    private final EmailSystemService emailSystemService;

    @Autowired
    public EmailSystemController(EmailSystemService emailSystemService) {
        this.emailSystemService = emailSystemService;
    }

    @RequestMapping(value = "email/isAdmin", method = RequestMethod.GET)
    @ResponseBody
    public boolean isAdmin() {
        return Exec.exec("email/isAdmin get", () -> emailSystemService.isAdmin());
    }

    @RequestMapping(value = "email/rules", method = RequestMethod.GET)
    @ResponseBody
    public Result<EmailRule> getRules() {
        return Exec.exec("email/rules get", () -> emailSystemService.getRules());
    }

    @RequestMapping(value = "email/rules/{ruleId}/toggle", method = RequestMethod.POST)
    @ResponseBody
    public String toggleRule(@PathVariable("ruleId") String ruleId, @RequestBody Map<String, Boolean> body) {
        return Exec.exec("email/rules toggle", () -> {
            boolean enabled = Boolean.TRUE.equals(body.get("enabled"));
            emailSystemService.toggleRule(ruleId, enabled);
            return "OK";
        });
    }

    @RequestMapping(value = "email/log", method = RequestMethod.GET)
    @ResponseBody
    public Result<EmailLogEntry> getLog(@RequestParam(value = "limit", defaultValue = "100") int limit) {
        return Exec.exec("email/log get", () -> emailSystemService.getLog(limit));
    }

    @RequestMapping(value = "email/rules/config/tipologie", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getTipologie() {
        return Exec.exec("email/rules/config/tipologie", () -> emailSystemService.getTipologie());
    }

    @RequestMapping(value = "email/rules/config/stati", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getStati(@RequestParam("tipologia") String tipologia) {
        return Exec.exec("email/rules/config/stati", () -> emailSystemService.getStati(tipologia));
    }

    @RequestMapping(value = "email/rules/config/uo", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getUo() {
        return Exec.exec("email/rules/config/uo", () -> emailSystemService.getUo());
    }

    @RequestMapping(value = "email/rules/custom", method = RequestMethod.GET)
    @ResponseBody
    public Result<EmailRuleCustom> getCustomRules() {
        return Exec.exec("email/rules/custom get", () -> emailSystemService.getCustomRules());
    }

    @RequestMapping(value = "email/rules/custom", method = RequestMethod.POST)
    @ResponseBody
    public EmailRuleCustom createCustomRule(@RequestBody EmailRuleCustom rule) {
        return Exec.exec("email/rules/custom create", () -> emailSystemService.createCustomRule(rule));
    }

    @RequestMapping(value = "email/rules/custom/{ruleId}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCustomRule(@PathVariable("ruleId") String ruleId, @RequestBody EmailRuleCustom rule) {
        return Exec.exec("email/rules/custom update", () -> {
            emailSystemService.updateCustomRule(ruleId, rule);
            return "OK";
        });
    }

    @RequestMapping(value = "email/rules/custom/{ruleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCustomRule(@PathVariable("ruleId") String ruleId) {
        return Exec.exec("email/rules/custom delete", () -> {
            emailSystemService.deleteCustomRule(ruleId);
            return "OK";
        });
    }
}
