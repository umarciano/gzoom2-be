package it.mapsgroup.gzoom.mybatis.dao;

import it.mapsgroup.gzoom.mybatis.mapper.VisitorMapper;

import it.mapsgroup.gzoom.mybatis.dto.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorDao extends AbstractDao {

	private final VisitorMapper visitorMapper;
	// TODO private final FilterService filterService;

	@Autowired
	public VisitorDao(VisitorMapper visitorMapper) {
		this.visitorMapper = visitorMapper;
		// this.filterService = filterService;
	}

	public List<Visitor> selectVisit() {
		return visitorMapper.selectVisit();
	}
	
}
