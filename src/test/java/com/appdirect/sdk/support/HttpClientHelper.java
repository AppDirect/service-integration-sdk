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

package com.appdirect.sdk.support;


import static java.util.Arrays.asList;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class HttpClientHelper {

	public static CloseableHttpClient anAppmarketHttpClient() {
		return HttpClients.custom()
				.setUserAgent("Apache-HttpClient/4.3.6 (java 1.5)")
				.setDefaultHeaders(asList(
						new BasicHeader(HttpHeaders.ACCEPT, "application/json, application/xml"),
						new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate")))
				.disableRedirectHandling()
				.build();
	}

	public static HttpGet get(String endpoint, String... params) throws URISyntaxException {
		URI uri = buildURI(endpoint, params);
		return new HttpGet(uri);
	}

	public static URI buildURI(String endpoint, String[] params) throws URISyntaxException {
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("pass params in name=value form");
		}

		URIBuilder builder = new URIBuilder(endpoint);
		for (int i = 0; i < params.length; i = i + 2) {
			builder.addParameter(params[i], params[i + 1]).build();
		}
		return builder.build();
	}
}
