package com.appdirect.sdk.web.openid;

import com.appdirect.sdk.utils.RoleMapper;
import com.appdirect.sdk.utils.RoleMapperConfiguration;
import org.openid4java.consumer.ConsumerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenIdSsoConfiguration {

    private final String identityUrl;

    public OpenIdSsoConfiguration(@Value("${openid.identityUrl:}") String identityUrl) {
        this.identityUrl = identityUrl;
    }

    @Bean
    public RoleMapperConfiguration mapperConfiguration() {
        return new RoleMapperConfiguration();
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
