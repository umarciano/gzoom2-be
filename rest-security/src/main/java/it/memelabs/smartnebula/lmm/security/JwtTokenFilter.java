package it.memelabs.smartnebula.lmm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.memelabs.smartnebula.lmm.security.model.ErrorResponse;
import it.memelabs.smartnebula.lmm.security.model.JwtAuthentication;
import it.memelabs.smartnebula.lmm.security.model.Messages;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Authenticate requests.
 * <p>
 * Valid requests must include the Authorization header prefixed with <code>Bearer </code> and followed by the JWT
 * token.
 * </p>
 *
 * @author Andrea Fossi
 */
public class JwtTokenFilter extends GenericFilterBean {
    private static final Logger LOG = getLogger(JwtTokenFilter.class);

    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JwtTokenFilter(AuthenticationManager authenticationManager,
                          AuthenticationEntryPoint entryPoint,
                          ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.entryPoint = entryPoint;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            String token = Tokens.token(req);
            JwtAuthentication jwtAuth = new JwtAuthentication(token);
            Authentication auth = authenticationManager.authenticate(jwtAuth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            if (LOG.isDebugEnabled()) LOG.debug("Token valid for user: {}", auth.getName());
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            if (entryPoint != null) {
                entryPoint.commence(req, res, e);
            }
        } catch (AccessDeniedException e) {
            LOG.error("Access denied [url={}]", req.getRequestURL(), e);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(Messages.FORBIDDEN));
        } catch (Exception e) {
            LOG.error("Unexpected exception occurred [url={}]", req.getRequestURL(), e);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(Messages.UNEXPECTED_ERROR));
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

}
