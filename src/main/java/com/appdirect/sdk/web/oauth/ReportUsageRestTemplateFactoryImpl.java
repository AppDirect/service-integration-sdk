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

import lombok.NoArgsConstructor;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.exception.ReportUsageApiExceptionHandler;

@NoArgsConstructor
public class ReportUsageRestTemplateFactoryImpl implements RestTemplateFactory {
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_READ_TIMEOUT = 60000;

	@Override
	public RestTemplate getOAuthRestTemplate(String key, String secret) {
		BaseProtectedResourceDetails oauthCredentials = new BaseProtectedResourceDetails();
		oauthCredentials.setConsumerKey(key);
		oauthCredentials.setSharedSecret(new SharedConsumerSecretImpl(secret));

		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setConnectTimeout(Timeout.ofMilliseconds(DEFAULT_CONNECT_TIMEOUT))
				.build();
		SocketConfig socketConfig = SocketConfig.custom()
				.setSoTimeout(Timeout.ofMilliseconds(DEFAULT_READ_TIMEOUT))
				.build();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(Timeout.ofMilliseconds(DEFAULT_CONNECT_TIMEOUT))
				.build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultSocketConfig(socketConfig);
		connectionManager.setDefaultConnectionConfig(connectionConfig);

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
				.build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new OAuthRestTemplate(clientHttpRequestFactory, oauthCredentials);
		restTemplate.setErrorHandler(new ReportUsageApiExceptionHandler());
		return restTemplate;
	}

	@Override
	public RestTemplate getBasicAuthRestTemplate(String key, String secret) {
		RestTemplate restTemplate = new BasicAuthRestTemplate(key, secret);
		restTemplate.setErrorHandler(new ReportUsageApiExceptionHandler());
		return restTemplate;
	}

	@Override
	public RestTemplate getOAuth2RestTemplate(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails);
		oAuth2RestTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		return oAuth2RestTemplate;
	}
}
