package com.appdirect.sdk.support;

import static com.appdirect.sdk.support.ContentOf.resourceAsBytes;
import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static com.appdirect.sdk.support.HttpClientHelper.anAppmarketHttpClient;
import static com.appdirect.sdk.support.HttpClientHelper.get;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class FakeAppmarket {
	private final HttpServer server;
	private final String isvKey;
	private final String isvSecret;
	private String lastRequestPath;

	public static FakeAppmarket create(int port, String isvKey, String isvSecret) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		return new FakeAppmarket(server, isvKey, isvSecret);
	}

	private FakeAppmarket(HttpServer server, String isvKey, String isvSecret) {
		this.server = server;
		this.isvKey = isvKey;
		this.isvSecret = isvSecret;
	}

	public FakeAppmarket start() {
		server.createContext("/v1/events/dev-order", new OauthSecuredJson("events/subscription-order-development.json"));
		server.createContext("/v1/events/dev-cancel", new OauthSecuredJson("events/subscription-cancel-development.json"));
		server.createContext("/v1/events/dev-change", new OauthSecuredJson("events/subscription-change-development.json"));
		server.createContext("/v1/events/dev-deactivated", new OauthSecuredJson("events/subscription-deactivated-development.json"));
		server.createContext("/v1/events/dev-reactivated", new OauthSecuredJson("events/subscription-reactivated-development.json"));
		server.createContext("/v1/events/dev-closed", new OauthSecuredJson("events/subscription-closed-development.json"));
		server.createContext("/v1/events/dev-upcoming-invoice", new OauthSecuredJson("events/subscription-upcoming-invoice-development.json"));

		server.start();
		return this;
	}

	public void stop() {
		server.stop(0);
	}

	public String lastRequestPath() {
		return lastRequestPath;
	}

	public HttpResponse sendEventTo(String connectorEventEndpointUrl, String appmarketEventPath) throws Exception {
		CloseableHttpClient httpClient = anAppmarketHttpClient();
		HttpGet request = get(connectorEventEndpointUrl, "eventUrl", baseAppmarketUrl() + appmarketEventPath);

		oauthSign(request);

		return httpClient.execute(request);
	}

	private String baseAppmarketUrl() {
		return "http://localhost:" + server.getAddress().getPort();
	}

	private void oauthSign(HttpGet request) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(isvKey, isvSecret);
		consumer.sign(request);
	}

	private class OauthSecuredJson implements HttpHandler {
		private final String jsonResource;

		private OauthSecuredJson(String jsonResource) {
			this.jsonResource = jsonResource;
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			lastRequestPath = t.getRequestURI().toString();

			String authorization = t.getRequestHeaders().getFirst("Authorization");
			if (authorization == null || !authorization.startsWith("OAuth oauth_consumer_key=\"" + isvKey + "\",")) {
				sendResponse(t, 401, "UNAUTHORIZED! Use OAUTH!".getBytes(UTF_8));
				return;
			}
			t.getResponseHeaders().add("Content-Type", "application/json");

			String accountId = getOptionalAccountIdParam(t.getRequestURI());
			byte[] response = accountId == null ? resourceAsBytes(jsonResource) : resourceAsString(jsonResource).replace("{{account-id}}", accountId).getBytes(UTF_8);

			sendResponse(t, 200, response);
		}

		private void sendResponse(HttpExchange t, int statusCode, byte[] response) throws IOException {
			t.sendResponseHeaders(statusCode, response.length);

			OutputStream os = t.getResponseBody();
			os.write(response);
			os.close();
		}

		private String getOptionalAccountIdParam(URI requestURI) {
			String query = requestURI.getQuery();
			if (query == null || !query.startsWith("account-id")) {
				return null;
			}
			return query.split("=")[1];
		}
	}
}
