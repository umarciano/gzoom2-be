package it.memelabs.smartnebula.lmm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Andrea Fossi.
 */
public abstract class CommonTest {
    protected ObjectMapper objectMapper;

    protected Long getNodeId() {
        return 1L;
    }

    @Before
    public void setUp() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(getUserLogin());

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public UserLogin getUserLogin() {
        Node node = new Node();
        node.setId(getNodeId());
        UserLogin userLogin = new UserLogin();
        userLogin.setActiveNode(node);
        userLogin.setId(getNodeId());
        userLogin.setUsername("jUnit test");
        return userLogin;
    }
}
