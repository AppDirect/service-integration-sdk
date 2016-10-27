package com.appdirect.sdk.support;


import static java.util.Arrays.asList;

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
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("pass params in name=value form");
		}

		URIBuilder builder = new URIBuilder(endpoint);
		for (int i = 0; i < params.length; i = i + 2) {
			builder.addParameter(params[i], params[i + 1]).build();
		}
		return new HttpGet(builder.build());
	}
}
