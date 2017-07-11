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

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.web.client.RestTemplate;

public class UserSyncOAuthRestTemplateFactoryImpl implements OAuthRestTemplateFactory {
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_READ_TIMEOUT = 60000;

	@Override
	public RestTemplate getRestTemplate(String key, String secret) {
		BaseProtectedResourceDetails oauthCredentials = new BaseProtectedResourceDetails();
		oauthCredentials.setConsumerKey(key);
		oauthCredentials.setSharedSecret(new SharedConsumerSecretImpl(secret));

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT);
		clientHttpRequestFactory.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		OAuthRestTemplate foo = new OAuthRestTemplate(clientHttpRequestFactory, oauthCredentials);
		return new OAuthRestTemplate(clientHttpRequestFactory, oauthCredentials);
	}
}
