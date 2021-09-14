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

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.header.HeaderWriterFilter;

import com.appdirect.sdk.appmarket.BasicAuthCredentialsSupplier;
import com.appdirect.sdk.web.oauth.model.SessionRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(50)
@ConditionalOnProperty(value = "sdk.basic.auth.supported", havingValue = "true", matchIfMissing = true)
public class BasicAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BasicAuthSupplier basicAuthSupplier;

    @Autowired
    private BasicAuthCredentialsSupplier basicAuthCredentialsSupplier;

    @Bean
    public SessionRequestMatcher sessionRequestMatcher(){
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .requestMatcher(sessionRequestMatcher())
                .authorizeRequests()
                .antMatchers("/unsecured/**")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .addFilterAfter(basicAuthenticationFilter(), HeaderWriterFilter.class)
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED));
    }
}
