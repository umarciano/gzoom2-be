# API Premiali Export

API REST sincrona paginata per esportare i punteggi di valutazione (CTX_EP)
verso il sistema esterno **Premiali**.

## Endpoint

```
GET /rest/api/premiali/export
```

### Parametri (query string)

| Nome              | Tipo | Obbligatorio | Default | Note                                     |
|-------------------|------|--------------|---------|------------------------------------------|
| `annoValutazione` | int  | sì           | —       | Anno della valutazione, es. `2025`.      |
| `page`            | int  | no           | `0`     | Indice di pagina, 0-based.               |
| `size`            | int  | no           | `500`   | Dimensione di pagina (max **2000**).     |

### Risposta

```json
{
  "results": [
    {
      "workEffortId": "WE_12345",
      "workEffortName": "Scheda EP 2025 - Mario Rossi",
      "workEffortTypeId": "CTX_EP",
      "annoValutazione": 2025,

      "partyId": "P00001",
      "matricola": "12345",
      "codiceFiscale": "RSSMRA80A01H501Z",
      "nome": "Mario",
      "cognome": "Rossi",

      "orgUnitId": "UO_001",
      "codiceUnitaOrganizzativa": "UO001",
      "descrizioneUnitaOrganizzativa": "Direzione Sanitaria",

      "scoreEp": 92.50,
      "scoreBs": 85.00,
      "adjustedScoreEp": 37.00,
      "adjustedScoreBs": 34.00,
      "overallEpBsScore": 71.00
    }
  ],
  "total": 4161
}
```

- `results` — pagina di righe.
- `total` — numero totale di record disponibili per quell'anno (utile per il
  client per sapere quando smettere di paginare).

### Iterazione lato client

Ripetere la chiamata incrementando `page` fino a quando
`(page + 1) * size >= total`.

Esempio in pseudo-codice:

```
size = 500
page = 0
totalFetched = 0
do {
  resp = GET /api/premiali/export?annoValutazione=2025&page=${page}&size=${size}
  process(resp.results)
  totalFetched += resp.results.size
  page++
} while (totalFetched < resp.total)
```

## Autenticazione

L'endpoint richiede un **JWT** ottenuto tramite il flusso di login standard di GZOOM.

### 1. Ottenere il token

```bash
curl -X POST http://HOST:8081/rest/api/getToken \
  -H "Content-Type: application/json" \
  -d '{"username":"premiali_export","password":"********"}'
```

Risposta:

```json
{ "token": "eyJraWQ...." }
```

### 2. Chiamare l'export

```bash
curl -X GET "http://HOST:8081/rest/api/premiali/export?annoValutazione=2025&page=0&size=500" \
  -H "Authorization: Bearer eyJraWQ...."
```

## Setup utenza dedicata

Si raccomanda di creare un'utenza tecnica dedicata `premiali_export`
(o nome equivalente) con password robusta, da utilizzare esclusivamente per
le chiamate da parte del sistema Premiali.

L'utenza deve avere accesso al backend GZOOM (UserLogin abilitato in OFBiz).
La verifica del permesso applicativo specifico (es. `PREMIALI_EXPORT_VIEW`)
non è attualmente implementata sull'endpoint: l'unico requisito è
l'autenticazione valida (JWT). Se richiesto in futuro, può essere aggiunta nel
service `PremialiExportService` con una chiamata a `PermissionService`.

## Sorgente dati

I punteggi sono letti dalle colonne **pre-calcolate** della tabella
`work_effort`:

- `score_ep`, `score_bs`
- `adjusted_score_ep`, `adjusted_score_bs`
- `overall_ep_bs_score`

Tali colonne vengono popolate dallo script
[backfill_work_effort_scores.sql](../../../../../../../../../../backfill_work_effort_scores.sql),
che usa come sorgente la view ufficiale GZOOM `work_effort_trans_all_view`
(la stessa usata dai report BIRT).

> **Importante**: dopo modifiche ai punteggi (es. ricalcolo periodico) ricordare
> di rieseguire lo script di backfill, oppure agganciarlo a un job Quartz.

## Filtro per anno

Il filtro `annoValutazione` viene applicato come:

```sql
work_effort.estimated_start_date      >= 'YYYY-01-01'
AND work_effort.estimated_completion_date <= 'YYYY-12-31'
```

Solo le schede `work_effort_type_id = 'CTX_EP'` non storicizzate
(`work_effort_revision_id IS NULL`) vengono restituite.

## Mappatura campi

| Campo DTO                       | Sorgente                                                                 |
|---------------------------------|--------------------------------------------------------------------------|
| `workEffortId` / `workEffortName` / `workEffortTypeId` | `work_effort`                                          |
| `partyId` / `codiceFiscale`     | `work_effort_party_assignment` (role `WEM_EVAL_IN_CHARGE`) → `party`     |
| `nome` / `cognome`              | `person`                                                                 |
| `matricola`                     | `party_parent_role` (role `EMPLOYEE`) → `parent_role_code`               |
| `orgUnitId`                     | `work_effort.organization_id`                                            |
| `descrizioneUnitaOrganizzativa` | `party.party_name` su `organization_id`                                  |
| `codiceUnitaOrganizzativa`      | `party_parent_role` (role `ORGANIZATION_UNIT`) → `parent_role_code`      |
| `scoreEp` / `scoreBs` / `adjusted*` / `overallEpBsScore` | colonne dirette di `work_effort` (vedi backfill) |

## Limiti e note operative

- `size` massimo: **2000**. Valori maggiori vengono troncati silenziosamente.
- `annoValutazione` accettato nel range `2000 .. annoCorrente + 1`. Fuori range
  → HTTP 400.
- Nessun caching server-side: il `total` è ricalcolato ad ogni chiamata.
- L'ordinamento garantito è `(matricola, partyId, workEffortId)`. Le
  matricole `NULL` vengono restituite in coda.
