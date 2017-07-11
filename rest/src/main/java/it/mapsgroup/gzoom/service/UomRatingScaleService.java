package it.mapsgroup.gzoom.service;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.UomRatingScaleDao;
import it.mapsgroup.gzoom.querydsl.dto.UomRatingScale;
import it.mapsgroup.gzoom.querydsl.dto.UomRatingScaleEx;

/**
 * Profile service.
 *
 */
@Service
public class UomRatingScaleService {
    private static final Logger LOG = getLogger(UomRatingScaleService.class);

    private final UomRatingScaleDao uomRatingScaleDao;

    @Autowired
    public UomRatingScaleService(UomRatingScaleDao uomRatingScaleDao) {
        this.uomRatingScaleDao = uomRatingScaleDao;
    }

    public Result<UomRatingScaleEx> getUomRatingScales(String uomId) {
        List<UomRatingScaleEx> list = uomRatingScaleDao.getUomRatingScales(uomId);
        return new Result<>(list, list.size());
    }
    
    public UomRatingScaleEx getUomRatingScale(String uomId, BigDecimal value) {
        UomRatingScaleEx record = uomRatingScaleDao.getUomRatingScale(uomId, value);
        return record;
    }

    public String createUomRatingScale(UomRatingScale req) {
        Validators.assertNotNull(req, Messages.UOM_RATING_SCALE_REQUIRED);
        Validators.assertNotBlank(req.getUomId(), Messages.UOM_ID_REQUIRED);
        Validators.assertNotNull(req.getUomRatingValue(), Messages.UOM_RATING_VALUE_REQUIRED);
        Validators.assertNotBlank(req.getDescription(), Messages.UOM_RATING_SCALE_DESCRIPTION_REQUIRED);
        uomRatingScaleDao.create(req, principal().getUserLoginId());
        return req.getUomId();
    }

    public String updateUomRatingScale(String id, BigDecimal value, UomRatingScale req) {
        Validators.assertNotNull(req, Messages.UOM_RATING_SCALE_REQUIRED);
        Validators.assertNotBlank(req.getUomId(), Messages.UOM_ID_REQUIRED);
        Validators.assertNotNull(value, Messages.UOM_RATING_VALUE_REQUIRED);
        Validators.assertNotBlank(req.getDescription(), Messages.UOM_DESCRIPTION_REQUIRED);
        UomRatingScale record = uomRatingScaleDao.getUomRatingScale(id, value);
        Validators.assertNotNull(record, Messages.INVALID_UOM_RATING_SCALE);
        uomRatingScaleDao.update(id, value, req, principal().getUserLoginId());
        return req.getUomId();
    }

    public String deleteUomRatingScale(String id, BigDecimal value) {
        Validators.assertNotBlank(id, Messages.UOM_ID_REQUIRED);
        Validators.assertNotNull(value, Messages.UOM_RATING_VALUE_REQUIRED);
        UomRatingScale record = uomRatingScaleDao.getUomRatingScale(id, value);
        Validators.assertNotNull(record, Messages.INVALID_UOM_RATING_SCALE);
        uomRatingScaleDao.delete(id, value);
        return id;
    }
}
