package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthMessageSignerException;

public class OAuthSignedClientHttpRequestFactoryTest {
	private OAuthSignedClientHttpRequestFactory requestFactory;

	@Before
	public void setup() throws Exception {
		requestFactory = new OAuthSignedClientHttpRequestFactory("some-key", "some-secret");
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

	@Test
	public void prepareConnection_throwsExceptionWhenSigningFailed() throws Exception {
		OAuthConsumer someCrashingConsumer = mock(OAuthConsumer.class);
		when(someCrashingConsumer.sign(any(Object.class))).thenThrow(new OAuthMessageSignerException("could not sign :("));

		requestFactory = new OAuthSignedClientHttpRequestFactory(someCrashingConsumer);

		assertThatThrownBy(() -> requestFactory.prepareConnection(connectionTo("http://some-domain.com"), "GET"))
				.hasMessage("Could not sign request to http://some-domain.com")
				.hasCauseExactlyInstanceOf(OAuthMessageSignerException.class);
	}

	private HttpURLConnection connectionTo(String url) throws IOException {
		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(connection.getURL()).thenReturn(URI.create(url).toURL());

		return connection;
	}
}
