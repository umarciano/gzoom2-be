package it.mapsgroup.gzoom;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.mapsgroup.gzoom.ofbiz.client.OfBizClientConfig;
import it.mapsgroup.gzoom.ofbiz.client.impl.AuthenticationOfBizClientImpl;
import it.mapsgroup.gzoom.ofbiz.service.ChangePasswordServiceOfBiz;
import it.mapsgroup.gzoom.ofbiz.service.LoginServiceOfBiz;
import it.mapsgroup.gzoom.ofbiz.service.VersionServiceOfBiz;
import it.mapsgroup.gzoom.ofbiz.client.impl.VersionOfBizClientImpl;
import it.mapsgroup.gzoom.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.net.URL;

/**
 * @author Andrea Fossi.
 */


@EnableWebSecurity
@Configuration
public class GZoomWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermitsStorage permitsStorage;

    @Bean
    public AuthenticationEntryPoint http403ForbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    @Autowired
    public LoginServiceOfBiz loginServiceOfBiz(OfBizClientConfig ofBizClientConfig) {
        return new LoginServiceOfBiz(new AuthenticationOfBizClientImpl(new OfBizClientConfig() {
            @Override
            public URL getServerXmlRpcUrl() {
                return ofBizClientConfig.getServerXmlRpcUrl();
            }
        }));
    }

    @Bean
    @Autowired
    public VersionServiceOfBiz versionServiceOfBiz(OfBizClientConfig ofBizClientConfig) {
        return new VersionServiceOfBiz(new VersionOfBizClientImpl(new OfBizClientConfig() {
            @Override
            public URL getServerXmlRpcUrl() {
                return ofBizClientConfig.getServerXmlRpcUrl();
            }
        }));
    }


    @Bean
    public HttpFirewall allowUrlEncodePercentHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);

        return firewall;
    }

    @Bean
    @Autowired
    public ChangePasswordServiceOfBiz changePasswordServiceOfBiz(OfBizClientConfig ofBizClientConfig) {
        return new ChangePasswordServiceOfBiz(new AuthenticationOfBizClientImpl(new OfBizClientConfig() {
            @Override
            public URL getServerXmlRpcUrl() {
                return ofBizClientConfig.getServerXmlRpcUrl();
            }
        }));
    }

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
        //@formatter:off
        super.configure(web);
        web.ignoring().antMatchers("/profile/i18n");
        web.httpFirewall(allowUrlEncodePercentHttpFirewall());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.logout().disable();
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/logout", "/login","/api/getToken","/api/doLogout").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/profile/i18n/*", "/reminder-period", "/reminder-expiry", "/user-preference-na/VISUAL_THEME", "/node/configuration/*", "/node/version/*", "/node/logo/*/*", "/party/partiesExposed","/api/getLoginMethod","/api/getOneLogin-LoginUrl","/api/getOneLogin-LogoutUrl").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/getToken").permitAll();
        http.authorizeRequests().antMatchers("/**").authenticated();
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager, objectMapper);
        http.addFilterBefore(jwtLoginFilter, BasicAuthenticationFilter.class);
        JwtLogoutFilter jwtLogoutFilter = new JwtLogoutFilter(restAuthenticationEntryPoint, permitsStorage, objectMapper);
        http.addFilterBefore(jwtLogoutFilter, LogoutFilter.class);
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(authenticationManager, restAuthenticationEntryPoint, objectMapper);
        RequestMatcher profile = new AntPathRequestMatcher("/profile/i18n/*");
        RequestMatcher logout = new AntPathRequestMatcher("/logout");
        RequestMatcher login = new AntPathRequestMatcher("/login");
        RequestMatcher partiesExposed = new AntPathRequestMatcher("/party/partiesExposed");
        RequestMatcher reminderPeriod = new AntPathRequestMatcher("/reminder-period"); //TODO
        RequestMatcher reminderExipry = new AntPathRequestMatcher("/reminder-expiry"); //TODO
        RequestMatcher userPreferenceNA = new AntPathRequestMatcher("/user-preference-na/VISUAL_THEME");
        RequestMatcher userPreference = new AntPathRequestMatcher("/user-preference/VISUAL_THEME");
        RequestMatcher configuration = new AntPathRequestMatcher("/node/configuration/*");
        RequestMatcher version = new AntPathRequestMatcher("/node/version/*");
        RequestMatcher logo = new AntPathRequestMatcher("/node/logo/*/*");
        RequestMatcher ignoredRequests = new OrRequestMatcher(profile, logout, login, reminderPeriod, reminderExipry, userPreferenceNA, configuration, version, logo, partiesExposed);
        //RequestMatcher ignoredRequests = new OrRequestMatcher(profile, logout, login, reminderPeriod, reminderExipry, userPreferenceNA, configuration, logo, partiesExposed);
        http.antMatcher("/**").addFilterAfter(new DelegateRequestMatchingFilter(ignoredRequests, jwtTokenFilter), JwtLogoutFilter.class);
    }
}
