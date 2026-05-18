package it.mapsgroup.gzoom.rest.premiali;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository per l'export verso il sistema "Premiali".
 *
 * Estrae da {@code work_effort} le schede CTX_EP filtrate per anno
 * (intersezione di {@code estimated_start_date} ed {@code estimated_completion_date}
 * con l'intervallo [YYYY-01-01, YYYY-12-31]) e arricchisce ogni riga con:
 * <ul>
 *   <li>Dati anagrafici del valutato (party_party_assignment con role_type_id =
 *       WEM_EVAL_IN_CHARGE) → person + party.</li>
 *   <li>Matricola del dipendente (party_parent_role con role_type_id = EMPLOYEE
 *       → parent_role_code).</li>
 *   <li>Codice e descrizione dell'unità organizzativa (party_parent_role
 *       con role_type_id = ORGANIZATION_UNIT su work_effort.org_unit_id).</li>
 *   <li>Punteggi pre-calcolati: score_ep, score_bs, adjusted_score_ep,
 *       adjusted_score_bs, overall_ep_bs_score (vedi backfill_work_effort_scores.sql).</li>
 * </ul>
 *
 * NOTA: la matricola può comparire più volte in {@code party_parent_role} se il
 * dipendente ha più ruoli oltre a EMPLOYEE; viene applicato {@code DISTINCT ON}
 * per restituire una sola matricola per persona.
 */
@Repository
public class PremialiExportRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PremialiExportRepository.class);

    private static final String BASE_FROM_WHERE =
            "FROM work_effort we " +
            "JOIN work_effort_party_assignment wpa " +
            "  ON wpa.work_effort_id = we.work_effort_id " +
            " AND wpa.role_type_id   = 'WEM_EVAL_IN_CHARGE' " +
            "JOIN party p " +
            "  ON p.party_id = wpa.party_id " +
            "LEFT JOIN person per " +
            "  ON per.party_id = p.party_id " +
            "LEFT JOIN LATERAL ( " +
            "  SELECT ppr.parent_role_code " +
            "  FROM   party_parent_role ppr " +
            "  WHERE  ppr.party_id     = p.party_id " +
            "  AND    ppr.role_type_id = 'EMPLOYEE' " +
            "  AND    ppr.parent_role_code IS NOT NULL " +
            "  ORDER  BY ppr.parent_role_code " +
            "  LIMIT  1 " +
            ") emp ON TRUE " +
            "LEFT JOIN party org " +
            "  ON org.party_id = we.org_unit_id " +
            "LEFT JOIN LATERAL ( " +
            "  SELECT ppr.parent_role_code " +
            "  FROM   party_parent_role ppr " +
            "  WHERE  ppr.party_id     = we.org_unit_id " +
            "  AND    ppr.role_type_id = 'ORGANIZATION_UNIT' " +
            "  AND    ppr.parent_role_code IS NOT NULL " +
            "  LIMIT  1 " +
            ") uo ON TRUE " +
            "WHERE we.work_effort_type_id = 'CTX_EP' " +
            "  AND we.work_effort_revision_id IS NULL " +
            "  AND we.estimated_start_date      >= :dataInizio " +
            "  AND we.estimated_completion_date <= :dataFine ";

    private static final String SELECT_SQL =
            "SELECT we.work_effort_id, " +
            "       we.work_effort_name, " +
            "       we.work_effort_type_id, " +
            "       we.etch                       AS tipologia_scheda, " +
            "       we.org_unit_id                AS org_unit_id, " +
            "       org.party_name                AS org_party_name, " +
            "       uo.parent_role_code           AS codice_uo, " +
            "       p.party_id, " +
            "       emp.parent_role_code          AS matricola, " +
            "       p.fiscal_code                 AS codice_fiscale, " +
            "       per.first_name                AS nome, " +
            "       per.last_name                 AS cognome, " +
            "       we.score_ep, " +
            "       we.score_bs, " +
            "       we.adjusted_score_ep, " +
            "       we.adjusted_score_bs, " +
            "       we.overall_ep_bs_score " +
            BASE_FROM_WHERE +
            "ORDER BY emp.parent_role_code::bigint NULLS LAST, p.party_id::bigint, we.work_effort_id " +
            "LIMIT :limit OFFSET :offset";

    private static final String COUNT_SQL =
            "SELECT COUNT(*) " + BASE_FROM_WHERE;

    private final NamedParameterJdbcTemplate namedJdbc;

    @Autowired
    public PremialiExportRepository(JdbcTemplate jdbcTemplate) {
        this.namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public int countByAnno(int annoValutazione) {
        MapSqlParameterSource params = buildYearParams(annoValutazione, 0, 0);
        Integer total = namedJdbc.queryForObject(COUNT_SQL, params, Integer.class);
        return total == null ? 0 : total;
    }

    public List<PremialeExportRow> findByAnno(int annoValutazione, int offset, int limit) {
        MapSqlParameterSource params = buildYearParams(annoValutazione, offset, limit);
        LOG.info("Premiali export query: anno={}, offset={}, limit={}", annoValutazione, offset, limit);
        return namedJdbc.query(SELECT_SQL, params, rowMapper(annoValutazione));
    }

    private MapSqlParameterSource buildYearParams(int annoValutazione, int offset, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dataInizio", Date.valueOf(annoValutazione + "-01-01"));
        params.addValue("dataFine",   Date.valueOf(annoValutazione + "-12-31"));
        params.addValue("offset",     offset);
        params.addValue("limit",      limit);
        return params;
    }

    private RowMapper<PremialeExportRow> rowMapper(int annoValutazione) {
        return (rs, i) -> {
            PremialeExportRow row = new PremialeExportRow();
            row.setWorkEffortId(rs.getString("work_effort_id"));
            row.setWorkEffortName(rs.getString("work_effort_name"));
            row.setWorkEffortTypeId(rs.getString("work_effort_type_id"));
            row.setTipologiaScheda(rs.getString("tipologia_scheda"));
            row.setAnnoValutazione(annoValutazione);

            row.setOrgUnitId(rs.getString("org_unit_id"));
            row.setCodiceUnitaOrganizzativa(rs.getString("codice_uo"));
            row.setDescrizioneUnitaOrganizzativa(rs.getString("org_party_name"));

            row.setPartyId(rs.getString("party_id"));
            row.setMatricola(rs.getString("matricola"));
            row.setCodiceFiscale(rs.getString("codice_fiscale"));
            row.setNome(rs.getString("nome"));
            row.setCognome(rs.getString("cognome"));

            row.setScoreEp(rs.getBigDecimal("score_ep"));
            row.setScoreBs(rs.getBigDecimal("score_bs"));
            row.setAdjustedScoreEp(rs.getBigDecimal("adjusted_score_ep"));
            row.setAdjustedScoreBs(rs.getBigDecimal("adjusted_score_bs"));
            row.setOverallEpBsScore(rs.getBigDecimal("overall_ep_bs_score"));
            return row;
        };
    }
}
