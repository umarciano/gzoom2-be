package it.mapsgroup.gzoom.jackson;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import it.memelabs.smartnebula.commons.DateUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final Logger LOG = getLogger(LocalDateDeserializer.class);

    @Override
    public LocalDate deserialize(JsonParser jsonparser,
                                 DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            LOG.error("Cannot parse date: {}", date);
            throw new JsonParseException("Cannot parse input date", JsonLocation.NA, e);
        }

    }

}