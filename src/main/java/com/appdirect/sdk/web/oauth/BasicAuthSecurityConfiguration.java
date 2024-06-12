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

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.header.HeaderWriterFilter;

import com.appdirect.sdk.appmarket.BasicAuthCredentialsSupplier;
import com.appdirect.sdk.web.oauth.model.SessionRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Order(50)
@ConditionalOnProperty(value = "sdk.basic.auth.enabled", havingValue = "true", matchIfMissing = true)
public class BasicAuthSecurityConfiguration {

    @Autowired
    private BasicAuthSupplier basicAuthSupplier;

    @Autowired
    private BasicAuthCredentialsSupplier basicAuthCredentialsSupplier;

    @Bean
    public SessionRequestMatcher sessionRequestMatcher() {
        return new SessionRequestMatcher();
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
    public UserDetailsService userDetailsService() {
        return new BasicAuthCredentialsUserDetailsService(basicAuthCredentialsSupplier);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }

    @Bean
    @Order(50)
    public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher(sessionRequestMatcher())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(new AntPathRequestMatcher("/unsecured/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(basicAuthenticationFilter(), HeaderWriterFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED)))
                .build();
    }
}
