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

import java.util.Map;

import com.appdirect.sdk.web.oauth.spring.oauth1.OAuthProviderSupport;
import jakarta.servlet.http.HttpServletRequest;


public class OAuthKeyExtractor {
	private final OAuthProviderSupport oauthProviderSupport;

	OAuthKeyExtractor(OAuthProviderSupport oauthProviderSupport) {
		this.oauthProviderSupport = oauthProviderSupport;
	}

	public String extractFrom(HttpServletRequest request) {
		Map<String, String> oauthParams = oauthProviderSupport.parseParameters(request);
		String consumerKey = oauthParams.get("oauth_consumer_key");
		if (consumerKey == null) {
			throw new IllegalArgumentException("Could not extract consumer key from request.");
		}
		return consumerKey;
	}
}
