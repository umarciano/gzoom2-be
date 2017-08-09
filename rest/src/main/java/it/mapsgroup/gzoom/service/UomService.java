package it.mapsgroup.gzoom.service;

import static org.slf4j.LoggerFactory.getLogger;
import static it.mapsgroup.gzoom.security.Principals.principal;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UomDao;
import it.mapsgroup.gzoom.querydsl.dto.Uom;
import it.mapsgroup.gzoom.querydsl.dto.UomEx;
import it.mapsgroup.gzoom.rest.ValidationException;

/**
 * Profile service.
 *
 */
@Service
public class UomService {
    private static final Logger LOG = getLogger(UomService.class);

    private final UomDao uomDao;

    @Autowired
    public UomService(UomDao uomDao) {
        this.uomDao = uomDao;
    }

    public Result<UomEx> getUoms() {
        List<UomEx> list = uomDao.getUoms();
        return new Result<>(list, list.size());
    }
    

    public UomEx getUom(String id) {
        UomEx uom = uomDao.getUom(id);
        return uom;
    }

    /**
     * Validation and Create
     * TODO Manage db Exception, like integrity Violetion, Duplicate Key or create a error message with detail
     * @param req
     * @return
     */
    public String createUom(Uom req) {
        Validators.assertNotNull(req, Messages.UOM_REQUIRED);
        Validators.assertNotBlank(req.getUomId(), Messages.UOM_ID_REQUIRED);
        Validators.assertNotBlank(req.getUomTypeId(), Messages.UOM_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(req.getDescription(), Messages.UOM_DESCRIPTION_REQUIRED);
        Validators.assertNotBlank(req.getAbbreviation(), Messages.UOM_ABBREVIATION_REQUIRED);
        if(req.getMinValue() != null && req.getMaxValue() != null ) {
            if(req.getMinValue().compareTo(req.getMaxValue()) > 0) {
                throw new ValidationException(Messages.UOM_MIN_VALUE_GREATER_THAN_MAX_VALUE);
            }
        }
        if ((req.getMinValue() == null && req.getMaxValue() != null )
                || (req.getMinValue() != null && req.getMaxValue() == null)) {
            throw new ValidationException(Messages.UOM_MIN_VALUE_AND_MAX_VALUE);
        }
        uomDao.create(req, principal().getUserLoginId());
        return req.getUomId();
    }

    public String updateUom(String id, Uom req) {
        Validators.assertNotNull(req, Messages.UOM_REQUIRED);
        Validators.assertNotBlank(req.getUomId(), Messages.UOM_ID_REQUIRED);
        Validators.assertNotBlank(req.getUomTypeId(), Messages.UOM_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(req.getDescription(), Messages.UOM_DESCRIPTION_REQUIRED);
        Validators.assertNotBlank(req.getAbbreviation(), Messages.UOM_ABBREVIATION_REQUIRED);
        Uom record = uomDao.getUom(id);
        Validators.assertNotNull(record, Messages.INVALID_UOM);
        if(req.getMinValue() != null && req.getMaxValue() != null ) {
            if(req.getMinValue().compareTo(req.getMaxValue()) > 0) {
                throw new ValidationException(Messages.UOM_MIN_VALUE_GREATER_THAN_MAX_VALUE);
            }
        }
        if ((req.getMinValue() == null && req.getMaxValue() != null )
                || (req.getMinValue() != null && req.getMaxValue() == null)) {
            throw new ValidationException(Messages.UOM_MIN_VALUE_AND_MAX_VALUE);
        }
        uomDao.update(id, req, principal().getUserLoginId());
        return req.getUomId();
    }

    public String deleteUom(String id) {
        Validators.assertNotBlank(id, Messages.UOM_TYPE_ID_REQUIRED);
        Uom record = uomDao.getUom(id);
        Validators.assertNotNull(record, Messages.INVALID_UOM_TYPE);
        uomDao.delete(id);
        return id;
    }

}
