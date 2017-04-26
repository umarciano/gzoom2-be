package it.memelabs.smartnebula.lmm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.memelabs.smartnebula.lmm.security.model.ErrorResponse;
import it.memelabs.smartnebula.lmm.security.model.LogoutResponse;
import it.memelabs.smartnebula.lmm.security.model.Messages;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
 * Filters logout requests.
 *
 * @author Andrea Fossi
 */
public class JwtLogoutFilter extends GenericFilterBean {
    private static final Logger LOG = getLogger(JwtLogoutFilter.class);

    private final AuthenticationEntryPoint entryPoint;
    private final PermitsStorage permitsStorage;
    private final ObjectMapper objectMapper;

    public JwtLogoutFilter(AuthenticationEntryPoint entryPoint, PermitsStorage permitsStorage, ObjectMapper objectMapper) {
        this.entryPoint = entryPoint;
        this.permitsStorage = permitsStorage;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (new AntPathRequestMatcher("/logout", "POST").matches(req)) {
            try {
                String token = Tokens.token(req);
                PermitsStorage.Permit permit = permitsStorage.prune(token);
                if (permit == null)
                    throw new BadCredentialsException(Messages.INVALID_AUTHORIZATION_TOKEN);
                if (LOG.isInfoEnabled()) LOG.info("Logged out user: {}", permit.username);
                // everything is fine, user was authenticated, returns ok
                new ObjectMapper().writeValue(response.getOutputStream(), new LogoutResponse(0));
            } catch (AuthenticationException e) {
                if (entryPoint != null) {
                    entryPoint.commence(req, res, e);
                }
            } catch (Exception e) {
                LOG.error("Unexpected exception occurred while filtering logout request [url={}]", req.getRequestURL(), e);
                res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(response.getOutputStream(), new ErrorResponse(Messages.UNEXPECTED_ERROR));
            } finally {
                SecurityContextHolder.clearContext();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
