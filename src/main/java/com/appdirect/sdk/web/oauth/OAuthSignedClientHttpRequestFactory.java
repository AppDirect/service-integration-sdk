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

import java.io.IOException;
import java.net.HttpURLConnection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

@Slf4j
public class OAuthSignedClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_READ_TIMEOUT = 60000;

	private final OAuthConsumer consumer;

	public OAuthSignedClientHttpRequestFactory(String consumerKey, String consumerSecret) {
		this(new DefaultOAuthConsumer(consumerKey, consumerSecret));
	}

	OAuthSignedClientHttpRequestFactory(OAuthConsumer consumer) {
		this.consumer = consumer;
		setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		setReadTimeout(DEFAULT_READ_TIMEOUT);
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);

		connection.setInstanceFollowRedirects(true);

		try {
			consumer.sign(connection);
			log.debug("Signed request to {}", connection.getURL());
		} catch (OAuthException e) {
			log.error("Could not sign request to {}", connection.getURL(), e);
			throw new IOException("Could not sign request to " + connection.getURL(), e);
		}
	}
}
