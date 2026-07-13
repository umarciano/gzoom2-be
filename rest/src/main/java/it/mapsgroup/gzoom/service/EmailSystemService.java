package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.EmailLogEntry;
import it.mapsgroup.gzoom.model.EmailRule;
import it.mapsgroup.gzoom.model.EmailRuleCustom;
import it.mapsgroup.gzoom.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static it.mapsgroup.gzoom.security.Principals.principal;

@Service
public class EmailSystemService {

    private final JdbcTemplate jdbc;
    private final PermissionRestService permissionService;

    private static final String FULLADMIN = "AORNADMIN";

    @Autowired
    public EmailSystemService(JdbcTemplate jdbc, PermissionRestService permissionService) {
        this.jdbc = jdbc;
        this.permissionService = permissionService;
    }

    public boolean isAdmin() {
        String userId = principal().getUserLoginId();
        return permissionService.hasSecurityGroup(userId, FULLADMIN);
    }

    public Result<EmailRule> getRules() {
        List<EmailRule> rules = jdbc.query(
            "SELECT rule_id, description, enabled, last_updated FROM gzoom_email_rule ORDER BY rule_id",
            new EmailRuleRowMapper()
        );
        return new Result<>(rules, rules.size());
    }

    public void toggleRule(String ruleId, boolean enabled) {
        jdbc.update(
            "UPDATE gzoom_email_rule SET enabled = ?, last_updated = NOW() WHERE rule_id = ?",
            enabled, ruleId
        );
    }

    public Result<EmailLogEntry> getLog(int limit) {
        int safeLimit = Math.min(limit, 500);
        List<EmailLogEntry> entries = jdbc.query(
            "SELECT log_id, rule_id, work_effort_id, recipient_email, subject, sent_at, status, error_message " +
            "FROM gzoom_email_log ORDER BY sent_at DESC LIMIT ?",
            new EmailLogRowMapper(),
            safeLimit
        );
        int total = jdbc.queryForObject("SELECT COUNT(*) FROM gzoom_email_log", Integer.class);
        return new Result<>(entries, total);
    }

    // --- Config endpoints ---

    public List<Map<String, String>> getTipologie() {
        return jdbc.query(
            "SELECT work_effort_type_id AS id, description FROM work_effort_type " +
            "WHERE work_effort_type_id IN ('CTX_EP','CTX_OR','CTX_BS') ORDER BY description",
            (rs, i) -> {
                Map<String, String> m = new HashMap<>();
                m.put("id", rs.getString("id"));
                m.put("description", rs.getString("description"));
                return m;
            }
        );
    }

    public List<Map<String, String>> getStati(String tipologia) {
        String statusTypeId;
        switch (tipologia) {
            case "CTX_EP": statusTypeId = "WE_STATUS_PERFORMANC"; break;
            case "CTX_OR": statusTypeId = "WE_STATUS_ORGANIZAT";  break;
            case "CTX_BS": statusTypeId = "WE_STATUS_STRATEGIC";  break;
            default: throw new IllegalArgumentException("Tipologia non valida: " + tipologia);
        }
        return jdbc.query(
            "SELECT DISTINCT ON (status_id) status_id AS id, description FROM status_item WHERE status_type_id = ? ORDER BY status_id, sequence_id",
            (rs, i) -> {
                Map<String, String> m = new HashMap<>();
                m.put("id", rs.getString("id"));
                m.put("description", rs.getString("description"));
                return m;
            },
            statusTypeId
        );
    }

    public List<Map<String, String>> getUo() {
        return jdbc.query(
            "SELECT DISTINCT pg.party_id AS id, pg.group_name AS name " +
            "FROM party_group pg JOIN party_role pr ON pg.party_id = pr.party_id " +
            "WHERE pr.role_type_id = 'ORGANIZATION_UNIT' ORDER BY pg.group_name",
            (rs, i) -> {
                Map<String, String> m = new HashMap<>();
                m.put("id", rs.getString("id"));
                m.put("name", rs.getString("name"));
                return m;
            }
        );
    }

    // --- CRUD regole custom ---

    public Result<EmailRuleCustom> getCustomRules() {
        List<EmailRuleCustom> rules = jdbc.query(
            "SELECT rule_id, name, work_effort_type_id, status_id, recipient_role_type_id, " +
            "uo_list, subject, body_template, enabled, created_at, last_updated " +
            "FROM gzoom_email_rule_custom ORDER BY created_at DESC",
            new EmailRuleCustomRowMapper()
        );
        return new Result<>(rules, rules.size());
    }

    public EmailRuleCustom createCustomRule(EmailRuleCustom rule) {
        String id = UUID.randomUUID().toString();
        jdbc.update(
            "INSERT INTO gzoom_email_rule_custom " +
            "(rule_id, name, work_effort_type_id, status_id, recipient_role_type_id, uo_list, subject, body_template, enabled) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            id, rule.getName(), rule.getWorkEffortTypeId(), rule.getStatusId(),
            rule.getRecipientRoleTypeId(), rule.getUoList(), rule.getSubject(),
            rule.getBodyTemplate(), rule.isEnabled()
        );
        rule.setRuleId(id);
        return rule;
    }

    public void updateCustomRule(String ruleId, EmailRuleCustom rule) {
        jdbc.update(
            "UPDATE gzoom_email_rule_custom SET name=?, work_effort_type_id=?, status_id=?, " +
            "recipient_role_type_id=?, uo_list=?, subject=?, body_template=?, enabled=?, last_updated=NOW() " +
            "WHERE rule_id=?",
            rule.getName(), rule.getWorkEffortTypeId(), rule.getStatusId(),
            rule.getRecipientRoleTypeId(), rule.getUoList(), rule.getSubject(),
            rule.getBodyTemplate(), rule.isEnabled(), ruleId
        );
    }

    public void deleteCustomRule(String ruleId) {
        jdbc.update("DELETE FROM gzoom_email_rule_custom WHERE rule_id=?", ruleId);
    }

    private static class EmailRuleCustomRowMapper implements RowMapper<EmailRuleCustom> {
        @Override
        public EmailRuleCustom mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmailRuleCustom r = new EmailRuleCustom();
            r.setRuleId(rs.getString("rule_id"));
            r.setName(rs.getString("name"));
            r.setWorkEffortTypeId(rs.getString("work_effort_type_id"));
            r.setStatusId(rs.getString("status_id"));
            r.setRecipientRoleTypeId(rs.getString("recipient_role_type_id"));
            r.setUoList(rs.getString("uo_list"));
            r.setSubject(rs.getString("subject"));
            r.setBodyTemplate(rs.getString("body_template"));
            r.setEnabled(rs.getBoolean("enabled"));
            if (rs.getTimestamp("created_at") != null)
                r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("last_updated") != null)
                r.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
            return r;
        }
    }

    private static class EmailRuleRowMapper implements RowMapper<EmailRule> {
        @Override
        public EmailRule mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmailRule r = new EmailRule();
            r.setRuleId(rs.getString("rule_id"));
            r.setDescription(rs.getString("description"));
            r.setEnabled(rs.getBoolean("enabled"));
            if (rs.getTimestamp("last_updated") != null)
                r.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
            return r;
        }
    }

    private static class EmailLogRowMapper implements RowMapper<EmailLogEntry> {
        @Override
        public EmailLogEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmailLogEntry e = new EmailLogEntry();
            e.setLogId(rs.getString("log_id"));
            e.setRuleId(rs.getString("rule_id"));
            e.setWorkEffortId(rs.getString("work_effort_id"));
            e.setRecipientEmail(rs.getString("recipient_email"));
            e.setSubject(rs.getString("subject"));
            if (rs.getTimestamp("sent_at") != null)
                e.setSentAt(rs.getTimestamp("sent_at").toLocalDateTime());
            e.setStatus(rs.getString("status"));
            e.setErrorMessage(rs.getString("error_message"));
            return e;
        }
    }
}
