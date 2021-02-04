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
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.CollectionUtils.isEmpty;

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
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.appdirect.sdk.web.oauth.model.OpenIdCustomUrlPattern;

@Configuration
@EnableWebSecurity
@Slf4j
@Order(101)
public class BasicAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BasicAuthSupplier basicAuthSupplier;

	@Bean
	public OpenIdCustomUrlPattern openIdUrlPatterns() {
		return new OpenIdCustomUrlPattern();
	}

	@Bean
	public BasicAuthService basicAuthService() {
		return new BasicAuthServiceImpl(basicAuthSupplier);
	}

	@Bean
	public Filter basicAuthenticationFilter() {
		return basicAuthService().getBasicFilter();
	}

	@Bean
	public RequestIdFilter requestIdFilter() {
		return new RequestIdFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] securedUrlPatterns = createSecuredUrlPatterns();
		log.info("Receiving api call to doFilter");
		http
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.requestMatchers()
			.antMatchers(securedUrlPatterns)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf().disable()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.httpBasic()
			.and()
			.addFilterBefore(requestIdFilter(), ProtectedResourceProcessingFilter.class)
			.addFilterBefore(basicAuthenticationFilter(), BasicAuthenticationFilter.class)
			.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED));
	}

	private String[] createSecuredUrlPatterns() {
		OpenIdCustomUrlPattern openIdCustomUrlPattern = openIdUrlPatterns();
		List<String> securedPaths = new ArrayList<>(asList("/api/v1/basic/integration/**"));
		log.debug("Found custom secured paths: {}", openIdCustomUrlPattern.getPatterns());
		if (!isEmpty(openIdCustomUrlPattern.getPatterns())) {
			securedPaths.addAll(openIdCustomUrlPattern.getPatterns());
		}
		log.debug("Configuring the following paths as secured: {}", securedPaths);
		return securedPaths.toArray(new String[securedPaths.size()]);
	}
}
