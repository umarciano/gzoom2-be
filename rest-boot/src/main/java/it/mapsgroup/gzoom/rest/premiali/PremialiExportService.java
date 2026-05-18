package it.mapsgroup.gzoom.rest.premiali;

import it.mapsgroup.gzoom.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

/**
 * Service per l'export verso il sistema esterno "Premiali".
 *
 * Espone una API sincrona paginata sui dati di valutazione (CTX_EP) di un anno.
 * I punteggi sono letti dalle colonne pre-calcolate di {@code work_effort}
 * popolate dallo script {@code backfill_work_effort_scores.sql}.
 */
@Service
public class PremialiExportService {

    private static final Logger LOG = LoggerFactory.getLogger(PremialiExportService.class);

    /** Massima dimensione di pagina consentita (per evitare OOM su payload enormi). */
    public static final int MAX_PAGE_SIZE = 2000;
    /** Dimensione di pagina di default. */
    public static final int DEFAULT_PAGE_SIZE = 500;

    private final PremialiExportRepository repository;

    @Autowired
    public PremialiExportService(PremialiExportRepository repository) {
        this.repository = repository;
    }

    /**
     * Estrae le valutazioni dell'anno richiesto, paginate.
     *
     * @param annoValutazione anno di valutazione (es. 2025)
     * @param page            indice di pagina, 0-based
     * @param size            dimensione di pagina (1..MAX_PAGE_SIZE)
     * @return {@link Result} contenente la pagina di righe e il totale dei record disponibili
     */
    public Result<PremialeExportRow> exportByAnno(int annoValutazione, int page, int size) {
        int normalizedYear = validateYear(annoValutazione);
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = clampSize(size);
        int offset = normalizedPage * normalizedSize;

        int total = repository.countByAnno(normalizedYear);
        List<PremialeExportRow> rows;
        if (offset >= total) {
            rows = List.of();
        } else {
            rows = repository.findByAnno(normalizedYear, offset, normalizedSize);
        }
        LOG.info("Premiali export: anno={}, page={}, size={}, returned={}, total={}",
                normalizedYear, normalizedPage, normalizedSize, rows.size(), total);
        return new Result<>(rows, total);
    }

    private int validateYear(int annoValutazione) {
        int currentYear = Year.now().getValue();
        if (annoValutazione < 2000 || annoValutazione > currentYear + 1) {
            throw new IllegalArgumentException("annoValutazione fuori range consentito (2000.." + (currentYear + 1) + "): " + annoValutazione);
        }
        return annoValutazione;
    }

    private int clampSize(int size) {
        if (size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(size, MAX_PAGE_SIZE);
    }
}
