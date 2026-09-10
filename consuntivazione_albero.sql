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
)
-- Model B (doc 10): il referente e' per (scheda, indicatore) su work_effort_measure.party_id,
-- non piu' sul catalogo gl_account_role. Lo scoping e' nella WHERE finale (su wem.party_id).
SELECT
  ga.gl_account_id, ga.account_code, ga.account_name,
  ga.calc_custom_method_id AS tipo, ga.source AS fonte,
  grt.description AS area, ga.description AS descrizione,
  EXTRACT(YEAR FROM we.estimated_completion_date)::int AS anno,
  we.work_effort_id, we.org_unit_id, pg.group_name AS uo,
  wem.kpi_score_weight AS peso, wem.period_type_id, we.current_status_id AS stato_scheda,
  gaic.input_sequence_num AS seq, gaic.factor_calculator AS ruolo,
  gft.gl_fiscal_type_id AS par_id, gft.description AS etichetta,
  wem.comments AS commento
FROM work_effort_measure wem
JOIN gl_account ga ON ga.gl_account_id = wem.gl_account_id
LEFT JOIN gl_resource_type grt ON grt.gl_resource_type_id = ga.gl_resource_type_id
JOIN work_effort we ON we.work_effort_id = wem.work_effort_id AND we.work_effort_type_id = 'CTX_BS'
   -- Il referente vede/consuntiva le schede aperte:
   --   WEORCARD_TOACC_INT  -> ciclo intermedio (solo indicatori con flag Y)
   --   WEORCARD_TOACCOUNT  -> ciclo finale (tutti gli indicatori)
   -- L'admin vede tutto.
   AND ((SELECT admin FROM is_admin) OR we.current_status_id IN ('WEORCARD_TOACC_INT','WEORCARD_TOACCOUNT'))
LEFT JOIN party_group pg ON pg.party_id = we.org_unit_id
LEFT JOIN gl_account_input_calc gaic ON gaic.gl_account_id = ga.gl_account_id
LEFT JOIN gl_fiscal_type gft ON gft.gl_fiscal_type_id = gaic.gl_fiscal_type_id
-- Model B: admin vede tutto; il referente vede SOLO le misure (scheda,indicatore) assegnate
-- a lui su wem.party_id. Richiede la migrazione che popola wem.party_id.
WHERE (wem.thru_date IS NULL OR wem.thru_date > now())
  AND ((SELECT admin FROM is_admin)
       OR (wem.party_id = (SELECT party_id FROM me) AND wem.role_type_id = 'WEM_IND_IN_CHARGE'))
ORDER BY ga.account_code, uo, seq;
