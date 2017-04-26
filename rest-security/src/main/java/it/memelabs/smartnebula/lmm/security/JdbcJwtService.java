package it.memelabs.smartnebula.lmm.security;

import com.thoughtworks.xstream.XStream;
import it.memelabs.smartnebula.lmm.persistence.security.dao.JdbcKeyStore;
import it.memelabs.smartnebula.lmm.security.model.SecurityConfiguration;
import it.memelabs.smartnebula.spring.boot.config.ApplicationContextProvider;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Generates and validates Json Web Tokens (JWT).
 *
 * @author Fabio G. Strozzi
 */
@Profile("persistJwt")
@Service("jdbcJwtService")
@DependsOn({"applicationContextProvider", "jdbcKeyStore"})
public class JdbcJwtService extends JwtService {
    private static final Logger LOG = getLogger(JdbcJwtService.class);

    @Autowired
    public JdbcJwtService(SecurityConfiguration config,
                          JwtPayloadMapper payloadMapper) throws JoseException {
        super(config, payloadMapper);
    }

    @Override
    protected RsaJsonWebKey initRsaJsonWebKey() throws JoseException {
        JdbcKeyStore keyStore = ApplicationContextProvider.getApplicationContext().getBean(JdbcKeyStore.class);
        XStream xStream = new XStream();
        String strKey = keyStore.loadKey();
        if (StringUtils.isEmpty(strKey)) {
            LOG.info("Key not found, creating key");
            RsaJsonWebKey key = super.initRsaJsonWebKey();
            keyStore.saveKey(xStream.toXML(key));
            return key;
        } else {
            LOG.info("Key found");
            RsaJsonWebKey key = (RsaJsonWebKey) xStream.fromXML(strKey);
            return key;
        }
    }
}
