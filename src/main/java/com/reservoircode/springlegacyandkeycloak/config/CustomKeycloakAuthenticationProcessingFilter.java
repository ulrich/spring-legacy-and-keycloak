package com.reservoircode.springlegacyandkeycloak.config;

import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.reservoircode.springlegacyandkeycloak.config.KeycloakSecurityConfig.VALIDATING_BY_KEYCLOAK;

public class CustomKeycloakAuthenticationProcessingFilter extends KeycloakAuthenticationProcessingFilter {

    public CustomKeycloakAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager, new AntPathRequestMatcher(VALIDATING_BY_KEYCLOAK));
    }
}