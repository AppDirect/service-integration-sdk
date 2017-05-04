package com.appdirect.sdk.web.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.OAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.CoreOAuthProviderSupport;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	@Bean
	public ConsumerDetailsService consumerDetailsService() {
		return new DeveloperSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
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
	public OAuthProviderSupport oauthProviderSupport() {
		return new CoreOAuthProviderSupport();
	}

	@Bean
	public OAuthKeyExtractor oauthKeyExtractor() {
		return new OAuthKeyExtractor(oauthProviderSupport());
	}

	@Bean
	public OAuthSignatureCheckingFilter oAuthSignatureCheckingFilter() {
		OAuthSignatureCheckingFilter filter = new OAuthSignatureCheckingFilter();
		filter.setConsumerDetailsService(consumerDetailsService());
		filter.setTokenServices(oauthProviderTokenServices());
		filter.setAuthenticationEntryPoint(oAuthProcessingFilterEntryPoint());
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/unsecured/**")
				.permitAll()
					.and()
				.antMatcher("/api/v1/**")
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.csrf().disable()
				.authorizeRequests().anyRequest().authenticated()
					.and()
				.addFilterBefore(oAuthSignatureCheckingFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
