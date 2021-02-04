/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.web.oauth;

import static java.util.Arrays.asList;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.OAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.CoreOAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.OAuth2CredentialsSupplier;
import com.appdirect.sdk.web.oauth.model.OpenIdCustomUrlPattern;

@Configuration
@EnableWebSecurity
@Slf4j
@Order(100)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	@Autowired
	private OAuth2AuthorizationSupplier oAuth2AuthorizationSupplier;
	@Autowired
	private OAuth2FeatureFlagSupplier oAuth2FeatureFlagSupplier;

	@Autowired
	private OAuth2CredentialsSupplier oAuth2CredentialsSupplier;


	@Bean
	public OpenIdCustomUrlPattern openIdUrlPatterns() {
		return new OpenIdCustomUrlPattern();
	}

	@Bean
	public ConsumerDetailsService consumerDetailsService() {
		return new DeveloperSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Bean
	public OAuth2AuthorizationService oAuth2consumerDetailsService() {
		return new OAuth2AuthorizationServiceImpl(oAuth2AuthorizationSupplier);
	}

	@Bean
	public OAuth2ClientDetailsService oAuth2ClientDetailsService() {
		return new OAuth2ClientDetailsServiceImpl(oAuth2CredentialsSupplier);
	}
	/**
	 * The feature flag will be used to enable oAuth2 authorization.
	 * The flag value is retrieved from connector.
	 */
	@Bean
	public OAuth2FeatureFlagService OAuth2FeatureFlagService() {
		return new OAuth2FeatureFlagServiceImpl(oAuth2FeatureFlagSupplier);
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
	public ProtectedResourceProcessingFilter oAuthSignatureCheckingFilter() {
		ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter();
		filter.setConsumerDetailsService(consumerDetailsService());
		filter.setTokenServices(oauthProviderTokenServices());
		filter.setAuthenticationEntryPoint(oAuthProcessingFilterEntryPoint());
		return filter;
	}

	@Bean
	public Filter oAuth2SignatureCheckingFilter() {
		return oAuth2consumerDetailsService().getOAuth2Filter();
	}

	private boolean isOAuth2Enabled() {
		return OAuth2FeatureFlagService().isOAuth2Enabled();
	}

	@Bean
	public RequestIdFilter requestIdFilter() {
		return new RequestIdFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		mainConfiguration(http);
		if(isOAuth2Enabled()) {
			oAuth2ProtectionOnApi(http);
		}
	}

	private void mainConfiguration(HttpSecurity http) throws Exception {
		String[] securedUrlPatterns = createSecuredUrlPatterns();
		http
				.authorizeRequests()
				.antMatchers("/unsecured/**")
				.permitAll()
				.and()
				.requestMatchers()
				.antMatchers(securedUrlPatterns)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.addFilterBefore(oAuthSignatureCheckingFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(requestIdFilter(), ProtectedResourceProcessingFilter.class)
				.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED));;
	}

	private void oAuth2ProtectionOnApi(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/unsecured/**").permitAll()
				.antMatchers("/api/v2/integration/**", "/api/v2/domainassociation/**", "/api/v2/migration/**", "/api/v2/restrictions/**")
				.authenticated()
				.and().addFilterAfter(oAuth2SignatureCheckingFilter(), HeaderWriterFilter.class);
	}

	private String[] createSecuredUrlPatterns() {
		OpenIdCustomUrlPattern openIdCustomUrlPattern = openIdUrlPatterns();
		List<String> securedPaths = new ArrayList<>(asList("/api/v1/integration/**", "/api/v1/domainassociation/**", "/api/v1/migration/**", "/api/v1/restrictions/**"));
		log.debug("Found custom secured paths: {}", openIdCustomUrlPattern.getPatterns());
		if (!isEmpty(openIdCustomUrlPattern.getPatterns())) {
			securedPaths.addAll(openIdCustomUrlPattern.getPatterns());
		}
		log.debug("Configuring the following paths as secured: {}", securedPaths);
		return securedPaths.toArray(new String[securedPaths.size()]);
	}
}
