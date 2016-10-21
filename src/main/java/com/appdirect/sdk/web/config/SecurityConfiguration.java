package com.appdirect.sdk.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appdirect.sdk.isv.IsvSpecificMarketplaceCredentialsSupplier;
import com.appdirect.sdk.web.oauth.IsvSpecificMarketplaceCredentialsConsumerDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private IsvSpecificMarketplaceCredentialsSupplier credentialsSupplier;

    @Bean
    public ConsumerDetailsService consumerDetailsService() {
        return new IsvSpecificMarketplaceCredentialsConsumerDetailsService(credentialsSupplier);
    }

    @Bean
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        return new InMemorySelfCleaningProviderTokenServices();
    }

    @Bean
    public OAuthProcessingFilterEntryPoint oAuthProcessingFilterEntryPoint() {
        return new OAuthProcessingFilterEntryPoint();
    }

    @Bean
    public CustomProtectedResourceProcessingFilter customProtectedResourceFilter() {
        CustomProtectedResourceProcessingFilter filter = new CustomProtectedResourceProcessingFilter();
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(oauthProviderTokenServices());
        filter.setAuthenticationEntryPoint(oAuthProcessingFilterEntryPoint());
        return filter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // fixme Core module will expose an API to register secured URLs
        http
            .antMatcher("/api/v1/**")
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(customProtectedResourceFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
