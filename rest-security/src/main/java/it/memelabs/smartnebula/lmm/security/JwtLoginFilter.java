package it.memelabs.smartnebula.lmm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.memelabs.smartnebula.lmm.security.model.*;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Filters incoming requests to '/login' and treats them as authentication requests using username and password.
 *
 * @author Andrea Fossi.
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private static final Logger LOG = getLogger(JwtLoginFilter.class);

    public JwtLoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Credentials cred = objectMapper.readValue(request.getReader(), Credentials.class);
        UsernamePasswordAuthenticationToken usrPwdAuth = new UsernamePasswordAuthenticationToken(cred.getUsername(), cred.getPassword());
        return getAuthenticationManager().authenticate(usrPwdAuth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = ((JwtAuthentication) authResult).getJwtToken();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (LOG.isInfoEnabled()) LOG.info("Successfully authenticated user: {}", authResult.getName());
        objectMapper.writeValue(response.getOutputStream(), new LoginResponse(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (failed instanceof InternalAuthenticationServiceException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(Messages.UNEXPECTED_ERROR));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(Messages.INVALID_USERNAME_OR_PASSWORD));
        }
    }
}
