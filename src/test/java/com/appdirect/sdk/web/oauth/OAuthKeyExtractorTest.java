package com.appdirect.sdk.web.oauth;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.provider.OAuthProviderSupport;

public class OAuthKeyExtractorTest {

	private OAuthKeyExtractor keyExtractor;
	private OAuthProviderSupport oauthProviderSupport;

	@Before
	public void setUp() throws Exception {
		oauthProviderSupport = mock(OAuthProviderSupport.class);
		when(oauthProviderSupport.parseParameters(any())).thenReturn(someParams("some-key"));

		keyExtractor = new OAuthKeyExtractor(oauthProviderSupport);
	}

	@Test
	public void extractFrom_fetchesParamFromProvider() throws Exception {
		HttpServletRequest request = aRequest();
		keyExtractor.extractFrom(request);

		verify(oauthProviderSupport).parseParameters(request);
	}

	@Test
	public void extractFrom_throwsIfRequestDoesNotHaveKey() throws Exception {
		HttpServletRequest requestWithoutKey = aRequest();
		when(oauthProviderSupport.parseParameters(requestWithoutKey)).thenReturn(emptyMap());

		assertThatThrownBy(() -> keyExtractor.extractFrom(requestWithoutKey)).hasMessage("Could not extract consumer key from request.");
	}

	@Test
	public void extractFrom_returnsKeyFromParams() throws Exception {
		HttpServletRequest requestWithKey = aRequest();
		when(oauthProviderSupport.parseParameters(requestWithKey)).thenReturn(someParams("the-right-key"));

		assertThat(keyExtractor.extractFrom(requestWithKey)).isEqualTo("the-right-key");
	}

	private HttpServletRequest aRequest() {
		return mock(HttpServletRequest.class);
	}

	private Map<String, String> someParams(String consumerKey) {
		Map<String, String> params = new HashMap<>();
		params.put("oauth_consumer_key", consumerKey);
		return params;
	}
}
