package com.reservoircode.springlegacyandkeycloak.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "app.keycloak.enabled", havingValue = "false", matchIfMissing = true)
public class LegacySecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LegacySecurityConfig.class);

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring the legacy security layer");

        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().anyRequest().permitAll();
    }
}