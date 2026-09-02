package it.mapsgroup.gzoom.querydsl.dao;

import it.mapsgroup.gzoom.querydsl.dto.ConsuntivazioneAlberoRow;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * DAO dell'albero di consuntivazione (Portale Referente, CTX_BS).
 * <p>
 * Esegue la query nativa validata su Postgres (vedi {@code consuntivazione_albero.sql})
 * e restituisce righe PIATTE (indicatore x UO x parametro). Lo scoping e' interamente
 * nella query: si filtra per l'utente loggato via il bind {@code :userLoginId}.
 * <p>
 * Si usa {@link NamedParameterJdbcTemplate} (costruito sul {@code mainDataSource}) invece
 * di QueryDSL perche' le tabelle {@code gl_account_role} e {@code gl_account_input_calc}
 * non hanno QEntity generate; l'accesso JDBC diretto e' gia' il pattern adottato altrove
 * (es. {@code UserInfoService}, {@code QueryExecutorService}, {@code ImportSchedeRepository})
 * e partecipa alla transazione Spring corrente tramite {@code DataSourceUtils}.
 */
@Service
public class ConsuntivazioneAlberoDao extends AbstractDao {

    private static final Logger LOG = getLogger(ConsuntivazioneAlberoDao.class);

    // Query validata (Postgres). Vedi workspace/gzoom2-be/consuntivazione_albero.sql.
    // I parametri esistono solo per tipo A/B*100 (LEFT JOIN -> null per SI_NO / diretto).
    // Le S* senza work_effort_measure (non assegnate) sono escluse dal JOIN.
    private static final String SQL =
            "WITH me AS (SELECT party_id FROM user_login WHERE user_login_id = :userLoginId), "
          // ADMIN (AORNADMIN) bypassa lo scoping per UOC: vede e puo' consuntivare TUTTI gli indicatori
          // agganciati a schede CTX_BS, ANCHE quelli SENZA referente (WEM_IND_IN_CHARGE) - es. gli
          // indicatori condivisi come ST13. Vedi myind sotto e doc 11 (indicatori condivisi/senza referente).
          + "is_admin AS ( "
          + "  SELECT EXISTS ( "
          + "    SELECT 1 FROM user_login_security_group ulsg "
          + "    WHERE ulsg.user_login_id = :userLoginId "
          + "      AND ulsg.group_id = 'AORNADMIN' "
          + "      AND (ulsg.thru_date IS NULL OR ulsg.thru_date > now()) "
          + "  ) AS admin "
          + "), "
          + "myind AS ( "
          // ADMIN: TUTTI gli indicatori agganciati a schede CTX_BS, ANCHE senza referente
          // (WEM_IND_IN_CHARGE): l'admin deve poterli vedere e consuntivare comunque.
          + "  SELECT DISTINCT wem2.gl_account_id "
          + "  FROM work_effort_measure wem2 "
          + "  JOIN work_effort we2 ON we2.work_effort_id = wem2.work_effort_id AND we2.work_effort_type_id = 'CTX_BS' "
          + "  WHERE (SELECT admin FROM is_admin) "
          + "    AND (wem2.thru_date IS NULL OR wem2.thru_date > now()) "
          + "  UNION "
          // REFERENTE (persona): gli indicatori di cui la PERSONA loggata e' referente diretto
          // (WEM_IND_IN_CHARGE con party_id = persona). Modello persona 2026-09-02: niente piu' salto UOC/ORG_RESPONSIBLE.
          + "  SELECT DISTINCT gar.gl_account_id "
          + "  FROM gl_account_role gar "
          + "  WHERE gar.role_type_id = 'WEM_IND_IN_CHARGE' "
          + "    AND (gar.thru_date IS NULL OR gar.thru_date > now()) "
          + "    AND gar.party_id = (SELECT party_id FROM me) "
          + ") "
          + "SELECT "
          + "  ga.gl_account_id, ga.account_code, ga.account_name, "
          + "  ga.calc_custom_method_id AS tipo, ga.source AS fonte, "
          + "  grt.description AS area, ga.description AS descrizione, "
          + "  EXTRACT(YEAR FROM we.estimated_completion_date)::int AS anno, "
          + "  we.work_effort_id, we.org_unit_id, pg.group_name AS uo, "
          + "  wem.kpi_score_weight AS peso, wem.period_type_id, we.current_status_id AS stato_scheda, "
          + "  gaic.input_sequence_num AS seq, gaic.factor_calculator AS ruolo, "
          + "  gft.gl_fiscal_type_id AS par_id, gft.description AS etichetta, "
          + "  (SELECT ate.amount FROM acctg_trans att JOIN acctg_trans_entry ate ON ate.acctg_trans_id=att.acctg_trans_id "
          + "     WHERE att.acctg_trans_type_id='CTX_BS' AND att.gl_fiscal_type_id='ACTUAL' AND att.party_id=we.org_unit_id "
          + "       AND ate.gl_account_id=ga.gl_account_id AND ate.organization_party_id=we.organization_id "
          + "       AND att.transaction_date>=wem.from_date AND att.transaction_date<=wem.thru_date LIMIT 1) AS valore_actual, "
          + "  (SELECT ate.amount FROM acctg_trans att JOIN acctg_trans_entry ate ON ate.acctg_trans_id=att.acctg_trans_id "
          + "     WHERE att.acctg_trans_type_id='CTX_BS' AND att.gl_fiscal_type_id=gaic.gl_fiscal_type_id AND att.party_id=we.org_unit_id "
          + "       AND ate.gl_account_id=ga.gl_account_id AND ate.organization_party_id=we.organization_id "
          + "       AND att.transaction_date>=wem.from_date AND att.transaction_date<=wem.thru_date LIMIT 1) AS valore_par "
          + "FROM myind "
          + "JOIN gl_account ga ON ga.gl_account_id = myind.gl_account_id "
          + "LEFT JOIN gl_resource_type grt ON grt.gl_resource_type_id = ga.gl_resource_type_id "
          + "JOIN work_effort_measure wem ON wem.gl_account_id = myind.gl_account_id "
          + "   AND (wem.thru_date IS NULL OR wem.thru_date > now()) "
          + "JOIN work_effort we ON we.work_effort_id = wem.work_effort_id AND we.work_effort_type_id = 'CTX_BS' "
          // Il REFERENTE vede/consuntiva solo le schede in TOACCOUNT (finestra di consuntivazione aperta
          // dall'admin); prima non e' ancora aperta, dopo (ACCOUNTED+) e' chiusa. L'admin non e' ristretto.
          + "   AND ((SELECT admin FROM is_admin) OR we.current_status_id = 'WEORCARD_TOACCOUNT') "
          + "LEFT JOIN party_group pg ON pg.party_id = we.org_unit_id "
          + "LEFT JOIN gl_account_input_calc gaic ON gaic.gl_account_id = ga.gl_account_id "
          + "LEFT JOIN gl_fiscal_type gft ON gft.gl_fiscal_type_id = gaic.gl_fiscal_type_id "
          + "ORDER BY ga.account_code, uo, seq";

    private static final RowMapper<ConsuntivazioneAlberoRow> ROW_MAPPER = new RowMapper<ConsuntivazioneAlberoRow>() {
        @Override
        public ConsuntivazioneAlberoRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConsuntivazioneAlberoRow row = new ConsuntivazioneAlberoRow();
            row.setGlAccountId(rs.getString("gl_account_id"));
            row.setAccountCode(rs.getString("account_code"));
            row.setAccountName(rs.getString("account_name"));
            row.setTipo(rs.getString("tipo"));
            row.setFonte(rs.getString("fonte"));
            row.setArea(rs.getString("area"));
            row.setDescrizione(rs.getString("descrizione"));
            int anno = rs.getInt("anno");
            row.setAnno(rs.wasNull() ? null : anno);
            row.setWorkEffortId(rs.getString("work_effort_id"));
            row.setOrgUnitId(rs.getString("org_unit_id"));
            row.setUo(rs.getString("uo"));
            row.setPeso(rs.getBigDecimal("peso"));
            row.setPeriodTypeId(rs.getString("period_type_id"));
            row.setStatoScheda(rs.getString("stato_scheda"));
            // input_sequence_num e' VARCHAR in DB: leggo come stringa e parso (getBigDecimal su varchar puo' lanciare).
            String seqStr = rs.getString("seq");
            row.setSeq((seqStr == null || seqStr.trim().isEmpty()) ? null : Integer.valueOf(seqStr.trim()));
            row.setRuolo(rs.getString("ruolo"));
            row.setParId(rs.getString("par_id"));
            row.setEtichetta(rs.getString("etichetta"));
            row.setValoreActual(rs.getBigDecimal("valore_actual"));
            row.setValorePar(rs.getBigDecimal("valore_par"));
            return row;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbc;

    public ConsuntivazioneAlberoDao(@Qualifier("mainDataSource") DataSource dataSource) {
        this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Righe piatte (indicatore x UO x parametro) di cui l'utente e' referente.
     *
     * @param userLoginId login dell'utente (scoping server-side: referente = proprie UOC;
     *                    admin AORNADMIN = tutti gli indicatori con referente).
     * @return righe piatte, gia' ordinate per account_code, uo, seq.
     */
    @Transactional(readOnly = true)
    public List<ConsuntivazioneAlberoRow> getAlbero(String userLoginId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userLoginId", userLoginId);
        List<ConsuntivazioneAlberoRow> rows = namedJdbc.query(SQL, params, ROW_MAPPER);
        LOG.info("consuntivazione/albero [userLoginId={}] righe piatte = {}", userLoginId, rows.size());
        return rows;
    }
}
