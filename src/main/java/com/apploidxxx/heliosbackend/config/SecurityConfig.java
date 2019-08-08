package com.apploidxxx.heliosbackend.config;

import com.apploidxxx.heliosbackend.handlers.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

/**
 * @author Arthur Kupriyanov
 */
@Configuration
@EnableWebSecurity
@EnableJpaRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthorizationFilter authorizationFilter;
    private final DataSource dataSource;

    public SecurityConfig(AccessDeniedHandler accessDeniedHandler, DataSource dataSource, AuthorizationFilter authorizationFilter) {
        this.dataSource = dataSource;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class)
                .antMatcher("/**")
                .anonymous().disable();
        http.cors().and().csrf().disable();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("sql...")
                .authoritiesByUsernameQuery("sql...");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}