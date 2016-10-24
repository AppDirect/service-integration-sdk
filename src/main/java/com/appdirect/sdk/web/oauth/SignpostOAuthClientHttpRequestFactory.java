package com.appdirect.sdk.web.oauth;

import java.io.IOException;
import java.net.HttpURLConnection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

@Slf4j
public class SignpostOAuthClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_READ_TIMEOUT = 60000;

	private final OAuthConsumer consumer;

	public SignpostOAuthClientHttpRequestFactory(String consumerKey, String consumerSecret) {
		this(new DefaultOAuthConsumer(consumerKey, consumerSecret));
	}

	SignpostOAuthClientHttpRequestFactory(OAuthConsumer consumer) {
		this.consumer = consumer;
		setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		setReadTimeout(DEFAULT_READ_TIMEOUT);
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);
		try {
			consumer.sign(connection);
			log.debug("Signed request to {}", connection.getURL());
		} catch (OAuthException e) {
			log.error("Could not sign request to {}", connection.getURL(), e);
			throw new IOException("Could not sign request to " + connection.getURL(), e);
		}
	}
}
