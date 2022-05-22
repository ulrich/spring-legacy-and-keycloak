package com.reservoircode.springlegacyandkeycloak.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@KeycloakConfiguration
@DependsOn("keycloakConfigResolver")
@ConditionalOnProperty(value = "app.keycloak.enabled", havingValue = "true")
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LegacySecurityConfig.class);

    public static final String VALIDATING_BY_KEYCLOAK = "/api/v2/**";

    @Autowired
    public void authenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        return new CustomKeycloakAuthenticationProcessingFilter(authenticationManagerBean());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring the Keycloak security layer");

        super.configure(httpSecurity);

        httpSecurity.cors();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers(VALIDATING_BY_KEYCLOAK).authenticated();
        httpSecurity.authorizeRequests().anyRequest().permitAll();
    }
}
