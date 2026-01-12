package it.mapsgroup.gzoom.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ImportSchedeRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ImportSchedeRepository.class);
    private final NamedParameterJdbcTemplate namedJdbc;

    @Autowired
    public ImportSchedeRepository(JdbcTemplate jdbcTemplate) {
        this.namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public int insertRows(List<Map<String,Object>> rows) {
        int count = 0;
        LOG.info("Inizio inserimento di {} righe nel database", rows.size());
        for (Map<String,Object> r : rows) {
            LOG.info("Inserimento riga: {}", r);
            
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("CONTESTO", toString(r.get("Contesto")));
            params.addValue("CODICE_SCHEDA", toString(r.get("Codice Scheda")));
            params.addValue("NOME_SCHEDA", toString(r.get("Nome Scheda")));
            params.addValue("MATRICOLA_VALUTATO", toString(r.get("Matricola Valutato")));
            params.addValue("MATRICOLA_VALUTATORE", toString(r.get("Matricola Valutatore")));
            params.addValue("CODICE_UOC", toString(r.get("Codice UOC")));
            // Prova entrambi i nomi di colonna
            String templateCode = toString(r.get("templateCode"));
            if (templateCode == null) {
                templateCode = toString(r.get("Codice Template"));
            }
            params.addValue("TEMPLATE_CODE", templateCode);
            params.addValue("DATA_INIZIO", toDate(r.get("Data Inizio")));
            params.addValue("DATA_FINE", toDate(r.get("Data Fine")));
            params.addValue("STATO", toString(r.get("Stato")));
            params.addValue("DESCRIZIONE", toString(r.get("Descrizione")));
            params.addValue("NOME_VALUTATO", toString(r.get("NomeValutato")));
            params.addValue("COGNOME_VALUTATO", toString(r.get("CognomeValutato")));
            params.addValue("NOME_VALUTATORE", toString(r.get("NomeValutatore")));
            params.addValue("COGNOME_VALUTATORE", toString(r.get("CognomeValutatore")));

            LOG.info("Parametri SQL: CONTESTO={}, CODICE_SCHEDA={}, NOME_SCHEDA={}, MATRICOLA_VALUTATO={}, MATRICOLA_VALUTATORE={}, TEMPLATE_CODE={}", 
                params.getValue("CONTESTO"), params.getValue("CODICE_SCHEDA"), params.getValue("NOME_SCHEDA"), 
                params.getValue("MATRICOLA_VALUTATO"), params.getValue("MATRICOLA_VALUTATORE"), params.getValue("TEMPLATE_CODE"));

            String sql = "INSERT INTO IMPORT_SCHEDE (CONTESTO, CODICE_SCHEDA, NOME_SCHEDA, MATRICOLA_VALUTATO, MATRICOLA_VALUTATORE, CODICE_UOC, TEMPLATE_CODE, DATA_INIZIO, DATA_FINE, STATO, DESCRIZIONE, NOME_VALUTATO, COGNOME_VALUTATO, NOME_VALUTATORE, COGNOME_VALUTATORE, CREATED_AT, UPDATED_AT) "
                    + "VALUES (:CONTESTO, :CODICE_SCHEDA, :NOME_SCHEDA, :MATRICOLA_VALUTATO, :MATRICOLA_VALUTATORE, :CODICE_UOC, :TEMPLATE_CODE, :DATA_INIZIO, :DATA_FINE, :STATO, :DESCRIZIONE, :NOME_VALUTATO, :COGNOME_VALUTATO, :NOME_VALUTATORE, :COGNOME_VALUTATORE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

            int updated = namedJdbc.update(sql, params);
            LOG.info("Righe inserite: {}", updated);
            count += updated;
        }
        LOG.info("Totale righe inserite: {}", count);
        return count;
    }

    private String toString(Object o) {
        if (o == null) return null;
        
        // Se è un numero con decimali .0, rimuovili
        if (o instanceof Number) {
            Number num = (Number) o;
            double d = num.doubleValue();
            // Se è un numero intero (es. 111201.0), rimuovi il .0
            if (d == Math.floor(d)) {
                return String.valueOf((long) d);
            }
            return String.valueOf(d);
        }
        
        return o.toString();
    }

    private Date toDate(Object o) {
        if (o == null) return null;
        if (o instanceof Date) return (Date)o;
        
        // Se è una stringa, prova a parsarla
        if (o instanceof String) {
            String dateStr = (String) o;
            try {
                // Prova formato dd/MM/yyyy
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.parse(dateStr);
            } catch (Exception e) {
                LOG.warn("Impossibile parsare la data: {}", dateStr, e);
            }
        }
        
        return null;
    }
}
