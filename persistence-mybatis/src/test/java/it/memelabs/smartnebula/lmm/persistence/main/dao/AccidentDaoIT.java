package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.EntityStateReference;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static it.memelabs.smartnebula.lmm.persistence.main.util.Dummies.getUserLogin;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Transactional
public class AccidentDaoIT extends AbstractDaoTestIT {
    private static final Logger LOG = getLogger(AccidentDaoIT.class);


    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private ConstructionSiteDao csDao;
    @Autowired
    private EntityStateDao entityStateDao;
    @Autowired
    private PersonEmploymentDao personEmploymentDao;
    @Autowired
    private AccidentDao accidentDao;

    @Test
    public void testFindById() throws Exception {
        ConstructionSiteEx cs = getConstructionSite();
        Company company = getCompany();
        Person person = getPerson();
        PersonEmploymentEx employment = getPersonEmployment(person, company);
        Accident record = new Accident();
        record.setSince(DateUtil.parse("02/10/2016", "dd/MM/yyyy"));
        record.setDuration(new BigDecimal("10.2"));
        record.setConstructionSiteId(cs.getId());
        record.setPersonEmploymentId(employment.getId());
        record.setDescription("Description");
        record.setNote("Note");
        Long id = accidentDao.create(record, getUserLogin());
        assertThat(id, is(notNullValue()));
        AccidentEx ret = accidentDao.findById(id);
        assertThat("AccidentEx not found", ret, is(not(nullValue())));
        assertThat("PersonEmployment not found", ret.getPersonEmployment(), is(not(nullValue())));
        assertThat("PersonEmployment not found", ret.getPersonEmployment().getId(), is(record.getPersonEmploymentId()));
        assertThat("Company not found", ret.getPersonEmployment().getCompany(), is(not(nullValue())));
        assertThat("Company.BusinessName not found", ret.getPersonEmployment().getCompany().getBusinessName(), is(company.getBusinessName()));
        assertThat("Company.VatNumber not found", ret.getPersonEmployment().getCompany().getVatNumber(), is(company.getVatNumber()));
        assertThat("Company.TaxIdentificationNumber not found", ret.getPersonEmployment().getCompany().getTaxIdentificationNumber(), is(company.getTaxIdentificationNumber()));

        assertThat("Person not found", ret.getPersonEmployment().getPerson(), is(not(nullValue())));
        assertThat("Person.FirstName not found", ret.getPersonEmployment().getPerson().getFirstName(), is(person.getFirstName()));
        assertThat("Person.LastName not found", ret.getPersonEmployment().getPerson().getLastName(), is(person.getLastName()));
        assertThat("Person.TaxIdentificationNumber not found", ret.getPersonEmployment().getPerson().getTaxIdentificationNumber(), is(person.getTaxIdentificationNumber()));

        assertThat("ConstructionSite not found", ret.getConstructionSite(), is(not(nullValue())));
        assertThat("ConstructionSite not found", ret.getConstructionSite().getId(), is(record.getConstructionSiteId()));
        assertThat("Since", ret.getSince(), is(record.getSince()));
        assertThat("Until", ret.getUntil(), is(DateUtil.parse("13/10/2016", "dd/MM/yyyy")));
        assertThat("Note", ret.getNote(), is(record.getNote()));
        assertThat("Description", ret.getDescription(), is(record.getDescription()));
        assertThat("Duration", ret.getDuration(), is(closeTo(record.getDuration(), BigDecimal.ZERO)));

        //findOverlap
        AccidentFilter filter = new AccidentFilter();
        filter.setPersonId(person.getId());
        filter.setSince(DateUtil.parse("02/09/2016", "dd/MM/yyyy"));
        filter.setUntil(DateUtil.parse("31/09/2016", "dd/MM/yyyy"));
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(0));
        filter.setSince(DateUtil.parse("02/11/2016", "dd/MM/yyyy"));
        filter.setUntil(DateUtil.parse("03/11/2016", "dd/MM/yyyy"));
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(0));
        filter.setSince(DateUtil.parse("02/10/2016", "dd/MM/yyyy"));
        filter.setUntil(DateUtil.parse("10/10/2016", "dd/MM/yyyy"));
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(1));
        filter.setSince(DateUtil.parse("05/10/2016", "dd/MM/yyyy"));
        filter.setUntil(DateUtil.parse("20/10/2016", "dd/MM/yyyy"));
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(1));
        filter.setSince(DateUtil.parse("02/09/2016", "dd/MM/yyyy"));
        filter.setUntil(DateUtil.parse("05/10/2016", "dd/MM/yyyy"));
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(1));
        filter.setId(ret.getId());
        assertThat("Inclusive", accidentDao.findOverlap(filter, getUserLogin()).size(), is(0));

        //findPersonByFilter
        filter = new AccidentFilter();
        filter.setFrom(DateUtil.parse("29/09/2016", "dd/MM/yyyy"));
        filter.setTo(DateUtil.parse("05/10/2016", "dd/MM/yyyy"));
        filter.setConstructionSiteId(cs.getId());
        filter.setPage(1);
        filter.setSize(10);
        Tuple2<List<AccidentEx>, Integer> result = accidentDao.findByFilter(filter, getUserLogin());
        assertThat("Inclusive", result.second(), is(1));
    }


    private ConstructionSiteEx getConstructionSite() {
        ConstructionSiteFilter filter = new ConstructionSiteFilter();
        filter.setPage(1);
        filter.setSize(10);
        return csDao.findByFilter(filter, getUserLogin()).first().get(0);
    }

    private Company getCompany() {
        Company record = new Company();
        record.setBusinessName("Unit test - " + System.currentTimeMillis());
        record.setTaxIdentificationNumber("123456789");
        record.setVatNumber("123456789");
        record.setWhiteListMember(true);
        Long id = companyDao.create(record, getUserLogin());
        assertThat(id, is(notNullValue()));
        return companyDao.findById(id);
    }

    private PersonEx getPerson() {
        Person record = new Person();
        record.setTaxIdentificationNumber("123456789");
        record.setFirstName("FirstName - " + System.currentTimeMillis());
        record.setLastName("LastName - " + System.currentTimeMillis());
        record.setBirthDate(new Date());
        record.setGender("M");
        record.setResidencyPermit(true);
        Long id = personDao.create(record, getUserLogin());
        assertThat(id, is(notNullValue()));
        return personDao.findById(id);
    }

    private PersonEmploymentEx getPersonEmployment(Person person, Company company) {
        PersonEmployment record = new PersonEmployment();
        record.setCompanyId(company.getId());
        record.setPersonId(person.getId());
        record.setCardNumber(System.currentTimeMillis());
        record.setStartDate(DateUtil.parse("01/10/2016", "dd/MM/yyyy"));
        EntityState state = entityStateDao.getFirst(
                EntityStateReference.PERSON_EMPLOYMENT.name(), null, null, getUserLogin());
        record.setStateId(state.getId());
        Long id = personEmploymentDao.create(record, getUserLogin());
        assertThat(id, is(notNullValue()));
        return personEmploymentDao.findById(id);
    }
}
