package it.mapsgroup.gzoom;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClientConfig;
import it.mapsgroup.gzoom.ofbiz.client.impl.AuthenticationOfBizClientImpl;
import it.mapsgroup.gzoom.ofbiz.service.LoginServiceOfBiz;
import it.mapsgroup.gzoom.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Andrea Fossi.
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class GZoomConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PermitsStorage permitsStorage;


    @Autowired
    private Http403ForbiddenEntryPoint http403ForbiddenEntryPoint;


    @Bean
    public LoginServiceOfBiz loginServiceOfBiz() {
        return new LoginServiceOfBiz(new AuthenticationOfBizClientImpl(new OfBizClientConfig() {
            @Override
            public URL getServerXmlRpcUrl() {
                try {
                    return new URL("http://localhost:19079/gzoom/control/xmlrpc");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }


    /* @Autowired
     @Qualifier("jwtDbLoginAuthenticationProvider")
     JwtDbLoginAuthenticationProvider jwtDbLoginAuthenticationProvider;
     @Autowired
     @Qualifier("jwtADLoginAuthenticationProvider")
     JwtADLoginAuthenticationProvider jwtADLoginAuthenticationProvider;*/
    @Autowired
    @Qualifier("jwtTokenAuthenticationProvider")
    JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider;

    @Autowired
    @Qualifier("jwtOfBizLoginAuthenticationProvider")
    JwtOfBizLoginAuthenticationProvider jwtOfBizLoginAuthenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.authenticationProvider(jwtDbLoginAuthenticationProvider);
        //  auth.authenticationProvider(jwtADLoginAuthenticationProvider);
        auth.authenticationProvider(jwtOfBizLoginAuthenticationProvider);
        auth.authenticationProvider(jwtTokenAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/profile/i18n");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.logout().disable();
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/logout", "/login", "/profile/18n").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/profile/18n").permitAll();
        http.authorizeRequests().antMatchers("/**").authenticated();

        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager, objectMapper);
        http.addFilterBefore(jwtLoginFilter, BasicAuthenticationFilter.class);

        JwtLogoutFilter jwtLogoutFilter = new JwtLogoutFilter(restAuthenticationEntryPoint, permitsStorage, objectMapper);
        http.addFilterBefore(jwtLogoutFilter, LogoutFilter.class);

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(authenticationManager, restAuthenticationEntryPoint, objectMapper);
        RequestMatcher profile = new AntPathRequestMatcher("/profile/i18n");
        RequestMatcher logout = new AntPathRequestMatcher("/logout");
        RequestMatcher login = new AntPathRequestMatcher("/login");
        RequestMatcher ignoredRequests = new OrRequestMatcher(profile, logout, login);

        http.antMatcher("/**")
                .addFilterAfter(new DelegateRequestMatchingFilter(ignoredRequests, jwtTokenFilter), JwtLogoutFilter.class);


    }
}
