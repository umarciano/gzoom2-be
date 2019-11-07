package it.mapsgroup.gzoom.mybatis.mapper;


import it.mapsgroup.gzoom.mybatis.dto.Visitor;



import java.util.List;


public interface VisitorMapper {

	/**
	 * Ritorna la lista di visite
	 * @return
	 */
	List<Visitor> selectVisit();
	

}
