package it.mapsgroup.gzoom.querydsl.dao;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import com.querydsl.sql.SQLQueryFactory;

public class PartyRelationshipDao extends AbstractDao {
	private static final Logger LOG = getLogger(PartyRelationshipDao.class);

    private final SQLQueryFactory queryFactory;
    
    public PartyRelationshipDao(SQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

}
