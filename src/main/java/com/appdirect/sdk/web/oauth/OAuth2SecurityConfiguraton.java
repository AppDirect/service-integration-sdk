package com.appdirect.sdk.web.oauth;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;


@Slf4j
@EnableResourceServer
@EnableWebSecurity
@Configuration
public class OAuth2SecurityConfiguraton extends ResourceServerConfiguration {


    @Bean
    public OAuth2AuthenticationProcessingFilter filter(){
        return  new OAuth2AuthenticationProcessingFilter();
    }

    @Bean
    public BearerTokenExtractor tokenExtractor(){
        return new BearerTokenExtractor();
    }

    @Bean
    public OAuth2AuthenticationManager manager(){
        return new OAuth2AuthenticationManager();
    }

    public OAuth2AuthenticationProcessingFilter getOAuth2Filter() {
        // configure token Extractor
        // BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();
        // log.info("tokenExtractor is {}", tokenExtractor.toString());
        // configure Auth manager
        OAuth2AuthenticationManager manager = manager();
        // configure RemoteTokenServices with your client Id and auth server endpoint
        manager.setTokenServices(tokenService());

        OAuth2AuthenticationProcessingFilter filter =  filter();
        filter.setTokenExtractor(tokenExtractor());
        filter.setAuthenticationManager(manager);
        log.info("filter is {}", filter.toString());
        return filter;
    }

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        //tokenService.setCheckTokenEndpointUrl( "localhost:8080/spring-security-oauth-server/oauth/…");
        tokenService.setClientId("UF1iM3f25M");
        tokenService.setClientSecret("mYLa1YoDYUGn8PWDNzhc");
        log.info("tokenService is {}", tokenService.toString());
        return tokenService;
    }
}
