package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.GlFiscalTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.GlFiscalType;
import it.mapsgroup.gzoom.querydsl.dto.GlFiscalTypeEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.List;

import static it.mapsgroup.gzoom.security.Principals.principal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Leonardo Minaudo.
 */
@Service
public class GlFiscalTypeService {
    private static final Logger LOG = getLogger(GlFiscalTypeService.class);
    private final GlFiscalTypeDao glFiscalTypeDao;

    @Autowired
    public GlFiscalTypeService(GlFiscalTypeDao glFiscalTypeDao) {
        this.glFiscalTypeDao = glFiscalTypeDao;
    }

    public Result<GlFiscalType> getGlFiscalType() {
        List<GlFiscalType> list = this.glFiscalTypeDao.getGlFiscalTypeList();
        return new Result<>(list, list.size());
    }

    public Result<GlFiscalTypeEx> getDetectionType() {
        List<GlFiscalTypeEx> list = this.glFiscalTypeDao.getDetectionType();
        return new Result<>(list, list.size());
    }

    public boolean createGlFiscalType(GlFiscalType req) {
        Validators.assertNotNull(req, Messages.GL_FISCAL_TYPE_REQUIRED);
        Validators.assertNotBlank(req.getGlFiscalTypeId(), Messages.GL_FISCAL_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(req.getGlFiscalTypeEnumId(), Messages.GL_FISCAL_TYPE_ENUM_ID_REQUIRED);
        return glFiscalTypeDao.create(req, principal().getUserLoginId());
    }

    public boolean updateGlFiscalType(GlFiscalType req) {
        Validators.assertNotBlank(req.getGlFiscalTypeId(), Messages.GL_FISCAL_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(req.getGlFiscalTypeEnumId(), Messages.GL_FISCAL_TYPE_ENUM_ID_REQUIRED);
        GlFiscalType record = glFiscalTypeDao.get(req.getGlFiscalTypeId());
        Validators.assertNotNull(record, Messages.INVALID_GL_FISCAL_TYPE);
        return glFiscalTypeDao.update(req, principal().getUserLoginId());
    }

    public boolean deleteGlFiscalType(String id) {
        GlFiscalType record = glFiscalTypeDao.get(id);
        Validators.assertNotNull(record, Messages.INVALID_GL_FISCAL_TYPE);
        return glFiscalTypeDao.delete(id);
    }
}
