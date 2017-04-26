package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentCategoryMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentExMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.EquipmentTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class EquipmentDao {
    // private static final Logger LOG = getLogger(EquipmentDao.class);

    private final EquipmentMapper equipmentMapper;
    private final EquipmentExMapper equipmentExMapper;
    private final EquipmentTypeMapper equipmentTypeMapper;
    private final EquipmentCategoryMapper equipmentCategoryMapper;

    @Autowired
    public EquipmentDao(EquipmentMapper equipmentMapper, EquipmentExMapper equipmentExMapper,
                        EquipmentTypeMapper equipmentTypeMapper, EquipmentCategoryMapper equipmentCategoryMapper) {
        this.equipmentMapper = equipmentMapper;
        this.equipmentExMapper = equipmentExMapper;
        this.equipmentTypeMapper = equipmentTypeMapper;
        this.equipmentCategoryMapper = equipmentCategoryMapper;
    }

    public Equipment findById(long id) {
        return equipmentMapper.selectByPrimaryKey(id);
    }

    public EquipmentEx findExById(long id) {
        return equipmentExMapper.selectByPrimaryKey(id);
    }

    public Integer countByRegistrationNumber(String registrationNumber, UserLogin user) {
        EquipmentExExample equipmentExample = new EquipmentExExample();
        EquipmentExExample.Criteria criteria = equipmentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        criteria.andRegistrationNumberLikeInsensitive(registrationNumber);
        equipmentExample.setOrderByClause("registration_number, id");
        return equipmentExMapper.countByExample(equipmentExample);
    }

    public Tuple2<List<EquipmentEx>, Integer> findByFilter(EquipmentFilter filter, UserLogin user) {
        RowBounds rowBounds = filter.makeRowBounds();
        filter.setOwnerNodeId(user.getActiveNode().getId());

        EquipmentExExample equipmentExample = new EquipmentExExample();
        EquipmentExExample.Criteria criteria = equipmentExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(filter.getOwnerNodeId());
        if (StringUtils.isNotBlank(filter.getRegistrationNumber())) {
            criteria.andRegistrationNumberLikeInsensitive(filter.makeLike(filter.getRegistrationNumber()));
        }
        if (StringUtils.isNotBlank(filter.getBrand())) {
            criteria.andBrandLikeInsensitive(filter.makeLike(filter.getBrand()));
        }
        if (StringUtils.isNotBlank(filter.getModel())) {
            criteria.andModelLikeInsensitive(filter.makeLike(filter.getModel()));
        }
        if (filter.getCompanyId() != null) {//
            equipmentExample.setFilterCompanyId(filter.getCompanyId());
        }
        if (filter.getFilterStatusId() != null) {//
            equipmentExample.setFilterStatusId(filter.getFilterStatusId());
        }
        if (StringUtils.isNotBlank(filter.getFilterType())) {
            equipmentExample.setFilterType(filter.getFilterType());
        }
        if (StringUtils.isNotBlank(filter.getFilterBadgeNumber())) {
            equipmentExample.setFilterBadgeNumber(filter.makeLike(filter.getFilterBadgeNumber().toLowerCase()));
        }
        if (filter.getFilterTimeCardNumber() != null) {
            equipmentExample.setFilterTimeCardNumber(filter.getFilterTimeCardNumber());
        }
        if (filter.getFilterCompanyStatusId() != null) {
            equipmentExample.setFilterCompanyStatusId(filter.getFilterCompanyStatusId());
        }
        equipmentExample.setOrderByClause("registration_number, id");

        int count = equipmentExMapper.countByExample(equipmentExample);
        List<EquipmentEx> list = equipmentExMapper.selectByExampleWithRowbounds(equipmentExample, rowBounds);
        return new Tuple2<>(list, count);
    }

    public Long create(Equipment record, UserLogin user) {
        Date createdStamp = new Date();
        record.setModifiedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setCreatedByUserId(user.getId());
        record.setOwnerNodeId(user.getActiveNode().getId());
        int result = equipmentMapper.insert(record);
        return (result > 0) ? record.getId() : null;
    }

    public boolean update(Equipment record, UserLogin user) {
        record.setModifiedStamp(new Date());
        record.setModifiedByUserId(user.getId());
        int result = equipmentMapper.updateByPrimaryKey(record);
        return result > 0;
    }

    public boolean delete(long id) {
        int result = equipmentMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public List<it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentType> findEquipmentTypes(UserLogin user) {
        EquipmentTypeExample equipmentTypeExample = new EquipmentTypeExample();
        equipmentTypeExample.createCriteria().andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        equipmentTypeExample.setOrderByClause("id");
        return equipmentTypeMapper.selectByExample(equipmentTypeExample);
    }

    public List<it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentCategory> findEquipmentCategories(
            Long equipmentTypeId, UserLogin user) {
        EquipmentCategoryExample equipmentCategoryExample = new EquipmentCategoryExample();
        EquipmentCategoryExample.Criteria criteria = equipmentCategoryExample.createCriteria();
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        if (equipmentTypeId != null) {
            criteria.andEquipmentTypeIdEqualTo(equipmentTypeId);
        }
        equipmentCategoryExample.setOrderByClause("id");
        return equipmentCategoryMapper.selectByExample(equipmentCategoryExample);
    }

    /**
     * Return  list of {@link Equipment Equipment} with an {@link it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmployment EquipmentEmployment}
     * linked in selected {@link it.memelabs.smartnebula.lmm.persistence.main.dto.WorkLog WorkLog}
     *
     * @param workLogId
     * @param filterText text to filter persons
     * @param page
     * @param size
     * @return
     */
    public Tuple2<List<Equipment>, Integer> findByWorkLogId(Long workLogId, String filterText, int page, int size) {
        String text = StringUtils.isNoneBlank(filterText) ? "%" + filterText + "%" : null;
        int count = equipmentExMapper.countByWorkLogId(workLogId, text);
        List<Equipment> list = equipmentExMapper.selectByWorkLogIdWithRowbounds(workLogId, text, new RowBounds((page - 1) * size, size));
        return new Tuple2<>(list, count);
    }
}
