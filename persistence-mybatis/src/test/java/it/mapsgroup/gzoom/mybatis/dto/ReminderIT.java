package it.mapsgroup.gzoom.mybatis.dto;

import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.mapsgroup.gzoom.mybatis.dao.ReminderDao;

/**
 * @author Assuntina Magnante.
 */

public class ReminderIT extends AbstractMyBatisTest {
	private static final Logger LOG = getLogger(ReminderIT.class);
	
	@Autowired
	private ReminderDao dao;
	
	@Test
	@Transactional
	public void reminderPeriod() throws Exception {
		LOG.info("start reminderPeriod: ");
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		List<Reminder> ret = dao.selectReminderPeriod("admin", "CORPERF", "15B0BIL", new Timestamp(now.getTime()));
		LOG.info("list: " + ret.size());
		assertNotNull(ret);
		// assertEquals("admin", ret.getUserLoginId());
	}
	
	@Test
	@Transactional
	public void reminderWorkEffortExpiry() throws Exception {
		LOG.info("start reminderWorkEffortExpiry: ");
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		List<Reminder> ret = dao.selectReminderWorkEffortExpiry("admin", "CORPERF", "15B0BIL", new Timestamp(now.getTime()));
		LOG.info("list: " + ret.size());
		assertNotNull(ret);
		// assertEquals("admin", ret.getUserLoginId());
	}
}
