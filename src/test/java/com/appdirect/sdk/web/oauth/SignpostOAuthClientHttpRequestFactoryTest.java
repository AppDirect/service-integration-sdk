package com.appdirect.sdk.web.oauth;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignpostOAuthClientHttpRequestFactoryTest {

	private SignpostOAuthClientHttpRequestFactory requestFactory;

	@BeforeMethod
	public void setup() throws Exception {
		requestFactory = new SignpostOAuthClientHttpRequestFactory("some-key", "some-secret");
	}

	@Test
	public void prepareConnection_signsRequest() throws Exception {
		HttpURLConnection connection = connectionTo("http://some-domain.com/?p1=v1");

		requestFactory.prepareConnection(connection, "GET");

		verify(connection).setRequestMethod("GET");
		verify(connection).setRequestProperty(eq("Authorization"), startsWith("OAuth oauth_consumer_key=\"some-key\""));
	}

	@Test
	public void prepareConnection_setsTimeoutsToDefaults() throws Exception {
		HttpURLConnection connection = connectionTo("http://some-other.com");

		requestFactory.prepareConnection(connection, "GET");

		verify(connection).setConnectTimeout(10_000);
		verify(connection).setReadTimeout(60_000);
	}

	private HttpURLConnection connectionTo(String url) throws IOException {
		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(connection.getURL()).thenReturn(URI.create(url).toURL());

		return connection;
	}
}
