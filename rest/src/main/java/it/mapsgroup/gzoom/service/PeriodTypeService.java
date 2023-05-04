package it.mapsgroup.gzoom.service;

import com.querydsl.sql.SQLQueryFactory;
import it.mapsgroup.gzoom.model.Messages;
import it.mapsgroup.gzoom.model.Result;
import it.mapsgroup.gzoom.querydsl.dao.AbstractDao;
import it.mapsgroup.gzoom.querydsl.dao.PeriodTypeDao;
import it.mapsgroup.gzoom.querydsl.dto.PeriodType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * PeriodType service.
 *
 */
@Service
public class PeriodTypeService extends AbstractDao {

    private static final Logger LOG = getLogger(PeriodTypeService.class);

    private final PeriodTypeDao periodTypeDao;
    private final DtoMapper dtoMapper;
    private final SQLQueryFactory queryFactory;

    @Autowired
    public PeriodTypeService(SQLQueryFactory queryFactory, PeriodTypeDao periodTypeDao, DtoMapper dtoMapper) {
        this.periodTypeDao = periodTypeDao;
        this.dtoMapper = dtoMapper;
        this.queryFactory = queryFactory;
    }

    public Result<PeriodType> getPeriodTypes() {
        List<PeriodType> list = periodTypeDao.getPeriodTypes();
        return new Result<>(list, list.size());
    }

    public boolean updatePeriodType(PeriodType periodType) {
        Validators.assertNotNull(periodType.getPeriodTypeId(), Messages.PERIOD_TYPE_ID_REQUIRED);
        PeriodType record = periodTypeDao.getPeriodType(periodType.getPeriodTypeId());
        Validators.assertNotNull(record, Messages.INVALID_PERIOD_TYPE);
        return periodTypeDao.update(periodType);
    }

    public boolean deletePeriodType(String[] periodTypes) {
        for(String id : periodTypes ) {
            if (id != null ) {
                if(id.length() < 3 || !id.substring(0, 3).equals("new")){
                    Validators.assertNotBlank(id, Messages.PERIOD_TYPE_ID_REQUIRED);
                    PeriodType record = periodTypeDao.getPeriodType(id);
                    Validators.assertNotNull(record, Messages.INVALID_TIME_ENTRY);
                    System.out.print(id);
                    periodTypeDao.delete(id);
                }
            }
        }
        return true;
    }

    public String createPeriodType(PeriodType periodType) {
        Validators.assertNotNull(periodType, Messages.PERIOD_TYPE_REQUIRED);
        Validators.assertNotBlank(periodType.getPeriodTypeId(), Messages.PERIOD_TYPE_ID_REQUIRED);
        Validators.assertNotBlank(periodType.getDescription(), Messages.PERIOD_TYPE_DESCRIPTION_REQUIRED);

        periodType.setPeriodLength(null);
        periodType.setUomId(null);
        periodTypeDao.create(periodType);
        return periodType.getPeriodTypeId();
    }

}

