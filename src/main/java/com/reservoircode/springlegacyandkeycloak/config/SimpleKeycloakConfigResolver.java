package com.reservoircode.springlegacyandkeycloak.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component("keycloakConfigResolver")
public class SimpleKeycloakConfigResolver implements KeycloakConfigResolver {

    private final static Logger logger = LoggerFactory.getLogger(SimpleKeycloakConfigResolver.class);

    @Value("${app.keycloak.server_url}")
    private String keycloakServerUrl;

    @Value("${app.keycloak.config_ssl}")
    private String keycloakConfigSSL;

    @Value("${app.keycloak.config_realm}")
    private String keycloakConfigRealm;

    @Value("${app.keycloak.config_resource}")
    private String keycloakConfigResource;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        logger.info("Configuring the Keycloak deployment");

        var adapterConfig = new AdapterConfig();

        adapterConfig.setBearerOnly(true);
        adapterConfig.setRealm(keycloakConfigRealm);
        adapterConfig.setSslRequired(keycloakConfigSSL);
        adapterConfig.setResource(keycloakConfigResource);
        adapterConfig.setAuthServerUrl(keycloakServerUrl);

        return KeycloakDeploymentBuilder
                .build(adapterConfig);
    }
}