package it.mapsgroup.gzoom.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class LocalDateTest {
    private static final Logger LOG = getLogger(LocalDateTest.class);

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        objectMapper.setDateFormat(new ISO8601DateFormat());
    }

    @Test
    public void name() throws Exception {

        //LocalDate d = LocalDate.parse("2018-02-28", DateTimeFormatter.ISO_DATE);
        //DateTimeFormatter.ISO_DATE.format(value);


        SimpleBean bean = new SimpleBean();
        bean.setFromDate(LocalDate.parse("2018-02-01", DateTimeFormatter.ISO_DATE));
        bean.setToDate(LocalDate.parse("2018-02-28", DateTimeFormatter.ISO_DATE));

        String json = objectMapper.writeValueAsString(bean);
        Map map = objectMapper.readValue(json, Map.class);
        assertEquals(map.get("fromDate"), "2018-02-01");
        assertEquals(map.get("toDate"), "2018-02-28");

        SimpleBean bean2 = objectMapper.readValue(json, SimpleBean.class);
        assertTrue(bean2.getFromDate().equals(bean.getFromDate()));
        assertTrue(bean2.getToDate().equals(bean.getToDate()));


        LOG.debug(json);

    }
}
