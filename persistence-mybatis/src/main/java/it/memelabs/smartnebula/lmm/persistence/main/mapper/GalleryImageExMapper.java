package it.memelabs.smartnebula.lmm.persistence.main.mapper;

import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImageEx;
import it.memelabs.smartnebula.lmm.persistence.main.dto.GalleryImageExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GalleryImageExMapper extends GalleryImageMapper {

    long selectNextId();

    List<GalleryImageEx> selectExByExampleWithRowbounds(GalleryImageExample example, RowBounds rowBounds);

    GalleryImageEx selectExByPrimaryKey(Long id);
}