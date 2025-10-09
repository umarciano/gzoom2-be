package it.mapsgroup.gzoom.rest.service;

import it.mapsgroup.gzoom.rest.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Service per recuperare le informazioni complete dell'utente
 * Effettua query su user_login, party, party_role_view e empl_position_type
 */
@Service
public class UserInfoService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Recupera tutte le informazioni dell'utente dato il user_login_id
     */
    public UserInfoDto getUserCompleteInfo(String userLoginId) {
        LOG.info("Getting complete user info for user_login_id: {}", userLoginId);
        
        try {
            // Step 1: Recupera i dati base dell'utente da user_login e person
            UserInfoDto userInfo = getUserBasicInfo(userLoginId);
            
            if (userInfo == null) {
                LOG.warn("User not found: {}", userLoginId);
                return createDefaultUserInfo(userLoginId);
            }
            
            // Step 2: Recupera matricola e posizione dalla vista party_role_view
            enrichWithRoleInfo(userInfo);
            
            // Step 3: Recupera codice fiscale dalla tabella party
            enrichWithFiscalCode(userInfo);
            
            // Step 4: Recupera descrizione posizione da empl_position_type
            enrichWithPositionDescription(userInfo);
            
            LOG.info("Complete user info retrieved: {}", userInfo);
            return userInfo;
            
        } catch (Exception e) {
            LOG.error("Error retrieving user info for {}: {}", userLoginId, e.getMessage(), e);
            return createDefaultUserInfo(userLoginId);
        }
    }
    
    /**
     * Recupera dati base da user_login e person
     */
    private UserInfoDto getUserBasicInfo(String userLoginId) {
        String sql = "SELECT ul.user_login_id, ul.party_id, p.first_name, p.last_name " +
                     "FROM user_login ul " +
                     "JOIN person p ON ul.party_id = p.party_id " +
                     "WHERE ul.user_login_id = ?";
        
        List<UserInfoDto> results = jdbcTemplate.query(sql, new Object[]{userLoginId}, new RowMapper<UserInfoDto>() {
            @Override
            public UserInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserInfoDto dto = new UserInfoDto();
                dto.setUserLoginId(rs.getString("user_login_id"));
                dto.setUserPartyId(rs.getString("party_id"));
                dto.setFirstName(rs.getString("first_name"));
                dto.setLastName(rs.getString("last_name"));
                return dto;
            }
        });
        
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
     * Arricchisce con matricola e posizione da party_role_view
     */
    private void enrichWithRoleInfo(UserInfoDto userInfo) {
        String sql = "SELECT parent_role_code, empl_position_type_id " +
                     "FROM party_role_view " +
                     "WHERE party_id = ? " +
                     "LIMIT 1";
        
        try {
            jdbcTemplate.queryForObject(sql, new Object[]{userInfo.getUserPartyId()}, new RowMapper<Void>() {
                @Override
                public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
                    userInfo.setMatricola(rs.getString("parent_role_code"));
                    userInfo.setPositionTypeId(rs.getString("empl_position_type_id"));
                    return null;
                }
            });
            
            LOG.debug("Role info enriched - Matricola: {}, PositionTypeId: {}", 
                     userInfo.getMatricola(), userInfo.getPositionTypeId());
                     
        } catch (Exception e) {
            LOG.warn("Could not retrieve role info for party_id {}: {}", userInfo.getUserPartyId(), e.getMessage());
            // Usa username come fallback per matricola
            userInfo.setMatricola(userInfo.getUserLoginId());
        }
    }
    
    /**
     * Arricchisce con codice fiscale da party
     */
    private void enrichWithFiscalCode(UserInfoDto userInfo) {
        String sql = "SELECT fiscal_code FROM party WHERE party_id = ?";
        
        try {
            String fiscalCode = jdbcTemplate.queryForObject(sql, new Object[]{userInfo.getUserPartyId()}, String.class);
            userInfo.setFiscalCode(fiscalCode);
            LOG.debug("Fiscal code enriched: {}", fiscalCode);
        } catch (Exception e) {
            LOG.warn("Could not retrieve fiscal code for party_id {}: {}", userInfo.getUserPartyId(), e.getMessage());
            userInfo.setFiscalCode("N/A");
        }
    }
    
    /**
     * Arricchisce con descrizione posizione da empl_position_type
     */
    private void enrichWithPositionDescription(UserInfoDto userInfo) {
        if (userInfo.getPositionTypeId() == null) {
            userInfo.setPositionType("N/A");
            return;
        }
        
        String sql = "SELECT description FROM empl_position_type WHERE empl_position_type_id = ?";
        
        try {
            String description = jdbcTemplate.queryForObject(sql, new Object[]{userInfo.getPositionTypeId()}, String.class);
            userInfo.setPositionType(description);
            LOG.debug("Position description enriched: {}", description);
        } catch (Exception e) {
            LOG.warn("Could not retrieve position description for id {}: {}", userInfo.getPositionTypeId(), e.getMessage());
            userInfo.setPositionType("N/A");
        }
    }
    
    /**
     * Crea un oggetto di default in caso di errore
     */
    private UserInfoDto createDefaultUserInfo(String userLoginId) {
        UserInfoDto dto = new UserInfoDto(userLoginId, "N/A", "N/A");
        dto.setMatricola(userLoginId);
        dto.setFiscalCode("N/A");
        dto.setPositionType("N/A");
        return dto;
    }
}