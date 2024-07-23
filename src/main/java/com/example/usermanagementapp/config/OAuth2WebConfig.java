package com.example.usermanagementapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OAuth2WebConfig {

    private List<ClientRegistration> registrations = new ArrayList<>();

    @Bean
    public ClientRegistration googleClientRegistration(@Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId,
                                                       @Value("${spring.security.oauth2.client.registration.google.client-secret}") String clientSecret,
                                                       @Value("${spring.security.oauth2.client.registration.google.scope}") String scope,
                                                       @Value("${spring.security.oauth2.client.provider.google.authorization-uri}") String authorizationUri,
                                                       @Value("${spring.security.oauth2.client.provider.google.token-uri}") String tokenUri,
                                                       @Value("${spring.security.oauth2.client.provider.google.user-info-uri}") String userInfoUri,
                                                       @Value("${spring.security.oauth2.client.provider.google.user-name-attribute}") String userNameAttributeName,
                                                       @Value("${spring.security.oauth2.client.registration.google.client-name}") String clientName,
                                                       @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String redirectUri) {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope.split(","))
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName(userNameAttributeName)
                .clientName(clientName)
                .redirectUriTemplate(redirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
        registrations.add(clientRegistration);
        return clientRegistration;
    }

    @Bean
    public ClientRegistration microsoftClientRegistration(@Value("${spring.security.oauth2.client.registration.microsoft.client-id}") String clientId,
                                                          @Value("${spring.security.oauth2.client.registration.microsoft.client-secret}") String clientSecret,
                                                          @Value("${spring.security.oauth2.client.registration.microsoft.scope}") String scope,
                                                          @Value("${spring.security.oauth2.client.provider.microsoft.authorization-uri}") String authorizationUri,
                                                          @Value("${spring.security.oauth2.client.provider.microsoft.token-uri}") String tokenUri,
                                                          @Value("${spring.security.oauth2.client.provider.microsoft.user-info-uri}") String userInfoUri,
                                                          @Value("${spring.security.oauth2.client.provider.microsoft.user-name-attribute}") String userNameAttributeName,
                                                          @Value("${spring.security.oauth2.client.registration.microsoft.client-name}") String clientName,
                                                          @Value("${spring.security.oauth2.client.registration.microsoft.redirect-uri}") String redirectUri) {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("microsoft")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope.split(","))
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName(userNameAttributeName)
                .clientName(clientName)
                .redirectUriTemplate(redirectUri)
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
        registrations.add(clientRegistration);
        return clientRegistration;
    }

    @Bean
    @DependsOn({"googleClientRegistration", "microsoftClientRegistration"})
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
    }
}