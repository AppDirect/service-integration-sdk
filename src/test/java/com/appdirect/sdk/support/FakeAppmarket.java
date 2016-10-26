package com.appdirect.sdk.support;

import static com.appdirect.sdk.support.ContentOf.resourceAsBytes;
import static com.appdirect.sdk.support.HttpClientHelper.anAppmarketHttpClient;
import static com.appdirect.sdk.support.HttpClientHelper.get;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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
	private String lastRequestPath;

	public static FakeAppmarket create(int port) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		return new FakeAppmarket(server);
	}

	private FakeAppmarket(HttpServer server) {
		this.server = server;
	}

	public FakeAppmarket start() {
		server.createContext("/v1/events/dev-order", new OauthSecuredJson("events/subscription-order-development.json"));

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
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer("isv-key", "isv-secret");
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
			if (authorization == null || !authorization.startsWith("OAuth oauth_consumer_key")) {
				sendResponse(t, 401, "UNAUTHORIZED! Use OAUTH!".getBytes(UTF_8));
				return;
			}

			t.getResponseHeaders().add("Content-Type", "application/json");

			byte[] response = resourceAsBytes(jsonResource);

			sendResponse(t, 200, response);
		}

		private void sendResponse(HttpExchange t, int statusCode, byte[] response) throws IOException {
			t.sendResponseHeaders(statusCode, response.length);

			OutputStream os = t.getResponseBody();
			os.write(response);
			os.close();
		}
	}
}
