package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dao.GlAccountDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class GlAccountService {
    private static final Logger LOG = getLogger(GlAccountService.class);

    private final GlAccountDao glAccountDao;

    @Autowired
    public GlAccountService(GlAccountDao glAccountDao) {
        this.glAccountDao = glAccountDao;
    }

    public BigInteger getDecimalPrecision() {
        return glAccountDao.getDecimalPrecision();
    }
}
