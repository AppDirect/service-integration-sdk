package com.appdirect.sdk.web.oauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth.provider.OAuthProviderSupport;

public class OAuthKeyExtractor {
	private final OAuthProviderSupport oauthProviderSupport;

	public OAuthKeyExtractor(OAuthProviderSupport oauthProviderSupport) {
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
