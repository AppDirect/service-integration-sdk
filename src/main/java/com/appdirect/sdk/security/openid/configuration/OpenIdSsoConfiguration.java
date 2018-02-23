package com.appdirect.sdk.security.openid.configuration;

import com.appdirect.sdk.security.configuration.model.RoleMapping;
import com.appdirect.sdk.security.openid.CustomAxFetchListFactory;
import com.appdirect.sdk.security.openid.CustomOpenIdConsumer;
import com.appdirect.sdk.security.openid.service.CustomUserDetailsService;
import com.appdirect.sdk.security.utils.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.openid4java.consumer.ConsumerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenIdSsoConfiguration {

    private final String identityUrl;

    public OpenIdSsoConfiguration(@Value("${openid.identityUrl}") String identityUrl) {
        this.identityUrl = identityUrl;
    }

    @Bean
    public RoleMapping mapperConfiguration() {
        return new RoleMapping();
    }

    @Bean
    public RoleMapper roleMapper() {
        return new RoleMapper(mapperConfiguration().getRoles());
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(roleMapper());
    }

    @Bean
    public CustomAxFetchListFactory customAxFetchListFactory() {
        return new CustomAxFetchListFactory();
    }

    @Bean
    public CustomOpenIdConsumer customOpenIdConsumer() throws ConsumerException {
        return new CustomOpenIdConsumer(this.identityUrl, customAxFetchListFactory());
    }
}
