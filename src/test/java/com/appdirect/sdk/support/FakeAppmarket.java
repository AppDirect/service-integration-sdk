package com.appdirect.sdk.support;

import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static com.appdirect.sdk.support.ContentOf.streamAsString;
import static com.appdirect.sdk.support.HttpClientHelper.anAppmarketHttpClient;
import static com.appdirect.sdk.support.HttpClientHelper.get;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;

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
	private final List<String> allRequestPaths;
	private final List<String> resolvedEvents;
	private String lastRequestBody;

	public static FakeAppmarket create(int port, String isvKey, String isvSecret) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		return new FakeAppmarket(server, isvKey, isvSecret);
	}

	private FakeAppmarket(HttpServer server, String isvKey, String isvSecret) {
		this.server = server;
		this.isvKey = isvKey;
		this.isvSecret = isvSecret;
		this.allRequestPaths = new ArrayList<>();
		this.resolvedEvents = new ArrayList<>();
	}

	public FakeAppmarket start() {
		Predicate<HttpExchange> oauthInTheHeader = httpExchange -> {
			String authorization = httpExchange.getRequestHeaders().getFirst("Authorization");
			return authorization != null && authorization.startsWith("OAuth oauth_consumer_key=\"" + isvKey + "\",");
		};

		server.createContext("/v1/events/order", new ReturnResourceContent(oauthInTheHeader, "events/subscription-order.json"));
		server.createContext("/v1/events/cancel", new ReturnResourceContent(oauthInTheHeader, "events/subscription-cancel.json"));
		server.createContext("/v1/events/change", new ReturnResourceContent(oauthInTheHeader, "events/subscription-change.json"));
		server.createContext("/v1/events/deactivated", new ReturnResourceContent(oauthInTheHeader, "events/subscription-deactivated.json"));
		server.createContext("/v1/events/reactivated", new ReturnResourceContent(oauthInTheHeader, "events/subscription-reactivated.json"));
		server.createContext("/v1/events/closed", new ReturnResourceContent(oauthInTheHeader, "events/subscription-closed.json"));
		server.createContext("/v1/events/upcoming-invoice", new ReturnResourceContent(oauthInTheHeader, "events/subscription-upcoming-invoice.json"));
		server.createContext("/api/integration/v1/events/", new OauthSecuredHandler(oauthInTheHeader) {
			@Override
			byte[] buildJsonResponse(URI requestUri) throws IOException {
				String eventId = requestUri.getPath().split("/")[5];
				markEventAsResolved(eventId);
				return "".getBytes(UTF_8);
			}
		});

		server.start();
		return this;
	}

	public void stop() {
		server.stop(0);
	}

	public String lastRequestPath() {
		return lastItemOrNull(allRequestPaths);
	}

	public List<String> allRequestPaths() {
		return new ArrayList<>(allRequestPaths);
	}

	public String lastRequestBody() {
		return lastRequestBody;
	}

	public List<String> resolvedEvents() {
		return resolvedEvents;
	}

	private void markEventAsResolved(String eventId) {
		synchronized (resolvedEvents) {
			System.out.println("ADDING to list");
			resolvedEvents.add(eventId);
			resolvedEvents.notify();
		}
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

	private <T> T lastItemOrNull(List<T> list) {
		return list.isEmpty() ? null : list.get(list.size() - 1);
	}

	private void oauthSign(HttpGet request) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(isvKey, isvSecret);
		consumer.sign(request);
	}

	public void waitForResolvedEvents(int desiredNumberOfResolvedEvents) throws Exception {
		long maxNumberOfTries = 100, timeoutOfOneTryMs = 50;
		synchronized (resolvedEvents) {
			int tries = 0;
			while (resolvedEvents.size() < desiredNumberOfResolvedEvents && tries < maxNumberOfTries) {
				tries++;
				resolvedEvents.wait(timeoutOfOneTryMs);
			}

			if (tries == maxNumberOfTries) {
				throw new TimeoutException("Could not find " + desiredNumberOfResolvedEvents + " resolved event after trying for " + maxNumberOfTries * timeoutOfOneTryMs + "ms. | resolvedEvents: " + resolvedEvents);
			}
		}
	}

	class ReturnResourceContent extends OauthSecuredHandler {
		private final String jsonResource;

		ReturnResourceContent(Predicate<HttpExchange> authorized, String jsonResource) {
			super(authorized);
			this.jsonResource = jsonResource;
		}

		@Override
		byte[] buildJsonResponse(URI requestUri) throws IOException {
			return resourceAsString(jsonResource)
					.replace("{{fake-appmarket-url}}", baseAppmarketUrl())
					.replace("{{account-id}}", getOptionalAccountIdParam(requestUri))
					.getBytes(UTF_8);
		}

		private String getOptionalAccountIdParam(URI requestURI) {
			String query = requestURI.getQuery();
			if (query == null || !query.startsWith("account-id")) {
				return "";
			}
			return query.split("=")[1];
		}
	}

	@RequiredArgsConstructor
	abstract class OauthSecuredHandler implements HttpHandler {
		private final Predicate<HttpExchange> authorized;

		@Override
		public void handle(HttpExchange t) throws IOException {
			allRequestPaths.add(t.getRequestURI().toString());

			if (!authorized.test(t)) {
				sendResponse(t, 401, "UNAUTHORIZED! Use OAUTH!".getBytes(UTF_8));
				return;
			}
			lastRequestBody = streamAsString(t.getRequestBody());

			t.getResponseHeaders().add("Content-Type", "application/json");
			sendResponse(t, 200, buildJsonResponse(t.getRequestURI()));
		}

		abstract byte[] buildJsonResponse(URI requestUri) throws IOException;

		private void sendResponse(HttpExchange t, int statusCode, byte[] response) throws IOException {
			t.sendResponseHeaders(statusCode, response.length);

			OutputStream os = t.getResponseBody();
			os.write(response);
			os.close();
		}
	}
}
