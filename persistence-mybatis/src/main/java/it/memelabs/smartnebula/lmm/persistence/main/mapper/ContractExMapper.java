package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Contract;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ContractEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ContractExExample;
import it.memelabs.smartnebula.lmm.persistence.main.dto.ContractExMgo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface ContractExMapper extends ContractMapper {

    int countByExample(ContractExExample example);

    ContractExMgo selectByPrimaryKeyEx(Long id);

    List<ContractEx> selectByExampleWithRowboundsEx(ContractExExample example, RowBounds rowBounds);

    List<ContractEx> selectMissingMgoData(@Param("ownerNodeId") Long ownerNodeId,
                                          @Param("modifiedStateStampFrom") Date modifiedStateStampFrom,
                                          @Param("modifiedStateStampTo") Date modifiedStateStampTo);

    List<ContractExMgo> selectByExampleWithRowboundsExMgo(ContractExExample example, RowBounds rowBounds);

    int countForReferenceContracts(ContractExExample example);

    List<Contract> lookForReferenceContracts(ContractExExample example, RowBounds rowBounds);

    List<ContractEx> selectByExampleWithRowboundsExForTree(ContractExExample example, RowBounds rowBounds);
}
