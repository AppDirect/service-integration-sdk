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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth.provider.OAuthProviderSupport;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasicAuthUserExtractor {
	private final OAuthProviderSupport oauthProviderSupport;

	BasicAuthUserExtractor(OAuthProviderSupport oauthProviderSupport) {
		this.oauthProviderSupport = oauthProviderSupport;
	}

	public String extractFrom(HttpServletRequest request) {
		final String authorization = request.getHeader("Authorization");
		String[] values;
		String usr = null;
		String credentials = null;

		log.info("authorization ={}",authorization);
		if (authorization != null ) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			credentials = new String(credDecoded, StandardCharsets.UTF_8);
			log.info("credentials={}",credentials);
			// credentials = username:password
			values = credentials.split(":", 2);
			usr = values[0];
		}
		if (usr == null) {
			throw new IllegalArgumentException("Could not extract basic auth user from request.");
		}
		return usr;
	}
}
