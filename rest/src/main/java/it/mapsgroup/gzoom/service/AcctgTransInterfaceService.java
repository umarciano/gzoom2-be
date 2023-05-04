package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.AcctgTransInterfaceDao;
import it.mapsgroup.gzoom.querydsl.dto.AcctgTransInterface;
import it.mapsgroup.gzoom.querydsl.dto.AcctgTransInterfaceExt;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AcctgTransInterfaceService {
    private static final Logger LOG = getLogger(UomService.class);
    private final AcctgTransInterfaceDao acctgTransInterfaceDao;

    @Autowired
    public AcctgTransInterfaceService(AcctgTransInterfaceDao acctgTransInterfaceDao) {this.acctgTransInterfaceDao = acctgTransInterfaceDao;}

    /**
     * List of AcctgTransInterfaces
     * @return
     */
    public Result<AcctgTransInterface> getAcctgTransInterfaces () {
        List<AcctgTransInterface> list = acctgTransInterfaceDao.getAll();
        return new Result<>(list, list.size());
    }

    /**
     * Create record on AcctgTransInterface
     * @param req
     * @return
     */
    public String createAcctgTransInterface(AcctgTransInterface req) {
        Validators.assertNotNull(req, Messages.NOT_NULL);
        acctgTransInterfaceDao.create(req, principal().getUserLoginId());
        return "record "+req.getId()+" created successfully";
    }

    /**
     * Create record on AcctgTransInterfaceExt
     * @param req
     * @return
     */
    public String createAcctgTransInterfaceExt(AcctgTransInterfaceExt req) {
        Validators.assertNotNull(req, Messages.NOT_NULL);
        acctgTransInterfaceDao.createExt(req, principal().getUserLoginId());
        return "record "+req.getId()+" created successfully";
    }
}
