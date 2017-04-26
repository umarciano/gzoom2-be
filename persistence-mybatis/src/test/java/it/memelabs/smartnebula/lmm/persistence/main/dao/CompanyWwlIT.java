package it.memelabs.smartnebula.lmm.persistence.main.dao;

import ch.qos.logback.classic.LoggerContext;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Company;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.CompanyExMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */

public class CompanyWwlIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(CompanyWwlIT.class);

    public CompanyWwlIT() {
        //reduced log output
        //TODO
        // LogManager.getLogManager()..setLevel(Level.INFO);
//        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("abc.xyz")) .setLevel(Level.INFO);
        //((ch.qos.logback.classic.Logger) LogBacF).setLevel(Level.INFO);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        //lc.
    }

    @Autowired
    private CompanyExMapper companyMapper;

    @Test
    public void firstTest() throws Exception {
        CompanyWwlFilter filter = new CompanyWwlFilter();
        filter.setConstructionSiteId(1000L);
        List<Company> ret = companyMapper.validForWeeklyWorkLog(filter);
        LOG.info(ret.stream().map(c -> c.getId() + "-" + c.getBusinessName()).collect(Collectors.joining(",")));
    }
}
