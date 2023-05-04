package it.mapsgroup.gzoom.service;

import it.mapsgroup.gzoom.querydsl.dao.PartyDao;
import it.mapsgroup.gzoom.querydsl.dto.Party;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class PartyService {
    private static final Logger LOG = getLogger(PartyService.class);
    private final PartyDao partyDao;

    @Autowired
    public PartyService(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    public String getUserPreference(String userLoginId,String partyId) {
        String ret = null;
        List<Party> parties = partyDao.getParties(userLoginId, "");
        if(parties != null && !parties.isEmpty()){
            for(Party p : parties){
                if(p.getPartyId().equalsIgnoreCase(partyId)){
                    ret = p.getPartyName();
                }
            }
        }
        return ret;
    }
}