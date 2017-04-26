package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.AttachmentEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.AttachmentExample;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
public interface AttachmentMapperEx {
    long selectNextId();

    List<AttachmentEx> selectByExample(AttachmentExample example);
    
    List<AttachmentEx> selectWithCompany(AttachmentExample example);

    List<AttachmentEx> selectWithEquipment(AttachmentExample example);
    
    List<AttachmentEx> selectWithPerson(AttachmentExample example);
    
    List<AttachmentEx> selectWithPersonEmployment(AttachmentExample example);
    
    List<AttachmentEx> selectWithContract(AttachmentExample example);

    List<AttachmentEx> selectWithAntimafiaProcess(AttachmentExample example);

    List<AttachmentEx> selectWithAccident(AttachmentExample example);


    AttachmentEx selectByPrimaryKey(Long id);
}
