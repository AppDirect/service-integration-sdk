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

package com.appdirect.sdk.web;

import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.OAuthSignedClientHttpRequestFactory;

public class RestOperationsFactory {
	private final AppmarketEventClientExceptionHandler errorHandler;

	public RestOperationsFactory(AppmarketEventClientExceptionHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public RestTemplate restOperationsForProfile(String key, String secret) {
		RestTemplate restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(key, secret));
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}
}
