-- Query VALIDATA (Postgres) per l'endpoint GET consuntivazione/albero.
-- Scoping server-side: :userLoginId = utente loggato (Principals.principal()).
-- Restituisce righe piatte (indicatore x UO x parametro); il service le assembla in
-- albero Indicatore > UO > parametri. I parametri esistono solo per tipo A/B*100
-- (LEFT JOIN -> null per SI_NO / DIRETTO). S* senza work_effort_measure = non assegnati -> esclusi.
--
-- ADMIN (AORNADMIN): vede e puo' consuntivare TUTTI gli indicatori agganciati a schede CTX_BS,
-- ANCHE quelli SENZA referente WEM_IND_IN_CHARGE (es. gli indicatori condivisi come ST13). Il
-- referente "normale" resta scopato alle proprie UOC (ORG_RESPONSIBLE). Vedi doc 11 (condivisi).
WITH me AS (SELECT party_id FROM user_login WHERE user_login_id = :userLoginId),
is_admin AS (
  SELECT EXISTS (
    SELECT 1 FROM user_login_security_group ulsg
    WHERE ulsg.user_login_id = :userLoginId
      AND ulsg.group_id = 'AORNADMIN'
      AND (ulsg.thru_date IS NULL OR ulsg.thru_date > now())
  ) AS admin
),
myuoc AS (
  SELECT DISTINCT pr.party_id_from AS uoc
  FROM party_relationship pr JOIN me ON true
  WHERE pr.party_id_to = me.party_id
    AND pr.party_relationship_type_id = 'ORG_RESPONSIBLE'
    AND (pr.thru_date IS NULL OR pr.thru_date > now())
),
myind AS (
  -- ADMIN: TUTTI gli indicatori agganciati a schede CTX_BS, anche SENZA referente.
  SELECT DISTINCT wem2.gl_account_id
  FROM work_effort_measure wem2
  JOIN work_effort we2 ON we2.work_effort_id = wem2.work_effort_id AND we2.work_effort_type_id = 'CTX_BS'
  WHERE (SELECT admin FROM is_admin)
    AND (wem2.thru_date IS NULL OR wem2.thru_date > now())
  UNION
  -- REFERENTE: solo gli indicatori di cui e' responsabile la sua UOC.
  SELECT DISTINCT gar.gl_account_id
  FROM gl_account_role gar
  WHERE gar.role_type_id = 'WEM_IND_IN_CHARGE'
    AND (gar.thru_date IS NULL OR gar.thru_date > now())
    AND gar.party_id IN (SELECT uoc FROM myuoc)
)
SELECT
  ga.gl_account_id, ga.account_code, ga.account_name,
  ga.calc_custom_method_id AS tipo, ga.source AS fonte,
  grt.description AS area, ga.description AS descrizione,
  EXTRACT(YEAR FROM we.estimated_completion_date)::int AS anno,
  we.work_effort_id, we.org_unit_id, pg.group_name AS uo,
  wem.kpi_score_weight AS peso, wem.period_type_id, we.current_status_id AS stato_scheda,
  gaic.input_sequence_num AS seq, gaic.factor_calculator AS ruolo,
  gft.gl_fiscal_type_id AS par_id, gft.description AS etichetta
FROM myind
JOIN gl_account ga ON ga.gl_account_id = myind.gl_account_id
LEFT JOIN gl_resource_type grt ON grt.gl_resource_type_id = ga.gl_resource_type_id
JOIN work_effort_measure wem ON wem.gl_account_id = myind.gl_account_id
   AND (wem.thru_date IS NULL OR wem.thru_date > now())
JOIN work_effort we ON we.work_effort_id = wem.work_effort_id AND we.work_effort_type_id = 'CTX_BS'
LEFT JOIN party_group pg ON pg.party_id = we.org_unit_id
LEFT JOIN gl_account_input_calc gaic ON gaic.gl_account_id = ga.gl_account_id
LEFT JOIN gl_fiscal_type gft ON gft.gl_fiscal_type_id = gaic.gl_fiscal_type_id
ORDER BY ga.account_code, uo, seq;
