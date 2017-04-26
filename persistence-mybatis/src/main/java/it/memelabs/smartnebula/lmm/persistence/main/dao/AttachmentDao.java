package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentStatus;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AttachmentMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AttachmentMapperEx;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.AttachmentTypeMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Service
public class AttachmentDao {
    private static final Logger LOG = getLogger(AttachmentDao.class);

    private AttachmentMapper attachmentMapper;
    private AttachmentMapperEx attachmentMapperEx;
    private AttachmentTypeMapper typeMapper;

    @Autowired
    public AttachmentDao(AttachmentMapper attachmentMapper, AttachmentMapperEx attachmentMapperEx, AttachmentTypeMapper typeMapper) {
        this.attachmentMapper = attachmentMapper;
        this.attachmentMapperEx = attachmentMapperEx;
        this.typeMapper = typeMapper;
    }

    @Deprecated
    public List<AttachmentEx> findByOwnerDocumentId(Long id) {
        return null;
    }

    public Attachment findByPrimaryKey(Long id) {
        return attachmentMapper.selectByPrimaryKey(id);
    }

    public Long create(Attachment record, UserLogin user) {
        Date createdStamp = new Date();
        record.setCreatedByUserId(user.getId());
        record.setCreatedStamp(createdStamp);
        record.setModifiedByUserId(user.getId());
        record.setModifiedStamp(createdStamp);
        record.setUploadedByUserId(user.getId());
        record.setUploadedStamp(createdStamp);
        record.setOwnerNodeId(user.getActiveNode().getId());
        int insert = attachmentMapper.insert(record);
        if (insert > 0)
            return record.getId();
        else
            return null;
    }

    public boolean updateByPrimaryKey(Attachment record) {
        int ret = attachmentMapper.updateByPrimaryKey(record);
        return ret > 0;
    }

    @Deprecated
    public boolean updateForUpload(Attachment record, UserLogin user) {
        Date updateStamp = new Date();
        record.setModifiedByUserId(user.getId());
        record.setModifiedStamp(updateStamp);
        record.setUploadedByUserId(user.getId());
        record.setUploadedStamp(updateStamp);
        int update = attachmentMapper.updateByPrimaryKey(record);
        return update > 0;
    }

    public AttachmentEx findByIdEx(Long id) {
        return attachmentMapperEx.selectByPrimaryKey(id);
    }

    public boolean deleteById(Long id) {
        int result = attachmentMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public boolean updateDescription(String description, Date validSince, Date validUntil, String referenceMonth, String referenceYear, long id, UserLogin user) {
        Date updateStamp = new Date();
        Attachment record = attachmentMapper.selectByPrimaryKey(id);
        record.setId(id);
        record.setDescription(description);
        record.setModifiedByUserId(user.getId());
        record.setModifiedStamp(updateStamp);
        record.setValidSince(validSince);
        record.setValidUntil(validUntil);
        record.setReferenceMonth(referenceMonth);
        record.setReferenceYear(referenceYear);
        int update = attachmentMapper.updateByPrimaryKey(record);
        return update > 0;
    }

    public Tuple2<List<AttachmentEx>, Integer> findByFilter(AttachmentFilter filter, UserLogin user) {
        AttachmentExample example = getAttachmentCriteria(filter, user);
        int count = attachmentMapper.countByExample(example);
        List<AttachmentEx> list = attachmentMapperEx.selectByExample(example);
        return new Tuple2<>(list, count);
    }

    public List<AttachmentEx> findExByFilter(AttachmentFilter filter, AttachmentEntity entity, UserLogin user) {
        AttachmentExExample example = getAttachmentCriteria(filter, user);
        List<AttachmentEx> list = null;
        switch (entity) {
            case ANTIMAFIA_PROCESS:
                list = attachmentMapperEx.selectWithAntimafiaProcess(example);
                break;
            case ATI:
            case COMPANY:
            case ATI_SECURITY:
            case COMPANY_SECURITY:
                list = attachmentMapperEx.selectWithCompany(example);
                break;
            case CONTRACT:
            case CONTRACT_TRACEABILITY:
            case CONTRACT_REGULARITY:
                list = attachmentMapperEx.selectWithContract(example);
                break;
            case EQUIPMENT:
                list = attachmentMapperEx.selectWithEquipment(example);
                break;
            case PERSON:
                list = attachmentMapperEx.selectWithPerson(example);
                break;
            case PERSON_EMPLOYMENT:
                list = attachmentMapperEx.selectWithPersonEmployment(example);
                break;
            case ACCIDENT:
                list = attachmentMapperEx.selectWithAccident(example);
                break;
            default:
                break;

        }
        return list;
    }

    public AttachmentEx findExByIdEx(AttachmentFilter filter, AttachmentEntity entity, UserLogin user) {
        AttachmentExExample example = getAttachmentCriteria(filter, user);
        List<AttachmentEx> list = null;
        switch (entity) {
            case ANTIMAFIA_PHASE:
                example.setFilterAntimafiaPhase(true);
            case ANTIMAFIA_PROCESS:
                list = attachmentMapperEx.selectWithAntimafiaProcess(example);
                break;
            case ATI:
            case COMPANY:
            case ATI_SECURITY:
            case COMPANY_SECURITY:
                list = attachmentMapperEx.selectWithCompany(example);
                break;
            case CONTRACT:
            case CONTRACT_TRACEABILITY:
            case CONTRACT_REGULARITY:
                list = attachmentMapperEx.selectWithContract(example);
                break;
            case EQUIPMENT:
                list = attachmentMapperEx.selectWithEquipment(example);
                break;
            case PERSON:
            case PERSON_IMAGE:
                list = attachmentMapperEx.selectWithPerson(example);
                break;
            case PERSON_EMPLOYMENT:
                example.setFilterEmploymentDate(false);
                list = attachmentMapperEx.selectWithPersonEmployment(example);
                break;
            case ACCIDENT:
                list = attachmentMapperEx.selectWithAccident(example);
                break;
            default:
                break;

        }
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    public Integer countByFilter(AttachmentFilter filter, UserLogin user) {
        AttachmentExample example = getAttachmentCriteria(filter, user);
        return attachmentMapper.countByExample(example);
    }

    private AttachmentExExample getAttachmentCriteria(AttachmentFilter filter, UserLogin user) {
        AttachmentExExample example = new AttachmentExExample();
        AttachmentExExample.Criteria criteria = example.createCriteria();
        if (filter.getCompanyId() != null)
            criteria.andCompanyIdEqualTo(filter.getCompanyId());
        if (filter.getAntimafiaProcessId() != null)
            criteria.andAntimafiaProcessIdEqualTo(filter.getAntimafiaProcessId());
        if (filter.getAntimafiaProcessPhaseId() != null)
            criteria.andAntimafiaProcessPhaseIdEqualTo(filter.getAntimafiaProcessPhaseId());
        if (filter.getPersonId() != null)
            criteria.andPersonIdEqualTo(filter.getPersonId());
        if (filter.getPersonEmploymentId() != null)
            criteria.andPersonEmploymentIdEqualTo(filter.getPersonEmploymentId());
        if (filter.getEquipmentId() != null)
            criteria.andEquipmentIdEqualTo(filter.getEquipmentId());
        if (filter.getEquipmentEmploymentId() != null)
            criteria.andEquipmentEmploymentIdEqualTo(filter.getEquipmentEmploymentId());
        if (filter.getContractId() != null)
            criteria.andContractIdEqualTo(filter.getContractId());
        if (filter.getValidUntil() != null) {
            Instant nowAM = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Instant validUntilPM = filter.getValidUntil().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
            Date now = Date.from(nowAM);
            Date future = Date.from(validUntilPM);
            criteria.andValidUntilBetween(now, future);
        }
        if (filter.getId() != null) {
            criteria.andIdEqualTo(filter.getId());
        }
        if (filter.getEntityStateTag() != null) {
            example.setEntityStateTag(filter.getEntityStateTag());
        }
        if (filter.getValidUntilBefore() != null) {
            criteria.andValidUntilLessThan(filter.getValidUntilBefore());
        }

        criteria.andEntityEqualTo(filter.getEntity());
        criteria.andStatusEqualTo(AttachmentStatus.UPLOADED);
        criteria.andOwnerNodeIdEqualTo(user.getActiveNode().getId());
        return example;
    }

    public List<AttachmentType> types(String itemType, UserLogin principal) {
        AttachmentTypeExample example = new AttachmentTypeExample();
        AttachmentTypeExample.Criteria criteria = example.createCriteria();
        criteria.andEntityEqualTo(itemType);
        criteria.andOwnerNodeIdEqualTo(principal.getActiveNode().getId());
        example.setOrderByClause("description");
        return typeMapper.selectByExample(example);
    }

    public long selectNextId() {
        return attachmentMapperEx.selectNextId();
    }

}