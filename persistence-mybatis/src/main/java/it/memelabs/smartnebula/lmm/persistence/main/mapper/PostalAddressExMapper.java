package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddressEx;

import java.util.List;

public interface PostalAddressExMapper extends PostalAddressMapper {

    PostalAddressEx selectByPrimaryKey(Long id);

    PostalAddressEx selectByLotId(Long id);

    List<PostalAddressEx> selectByConstructionSiteId(Long id);
}
