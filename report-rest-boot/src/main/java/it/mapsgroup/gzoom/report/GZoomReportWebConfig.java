package it.mapsgroup.gzoom.report;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class GZoomReportWebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //http.authorizeRequests().antMatchers("/").permitAll();
        http
            .authorizeRequests()
            .antMatchers("/").authenticated() // These urls are allowed by any authenticated user
            .and()
            .httpBasic();
        http.csrf().disable();
    }
}
