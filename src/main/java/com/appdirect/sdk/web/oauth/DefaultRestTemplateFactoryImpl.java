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
 *
 */

package com.appdirect.sdk.web.oauth;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class DefaultRestTemplateFactoryImpl implements RestTemplateFactory {
	private final ResponseErrorHandler errorHandler;

	public DefaultRestTemplateFactoryImpl(ResponseErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	@Override
	public RestTemplate getOAuthRestTemplate(String key, String secret) {
		RestTemplate restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(key, secret));
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

	@Override
	public RestTemplate getBasicAuthRestTemplate(String key, String secret) {
		RestTemplate restTemplate  = new BasicAuthRestTemplate(key, secret);
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

}
