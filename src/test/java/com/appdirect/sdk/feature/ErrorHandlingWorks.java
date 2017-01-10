package com.appdirect.sdk.feature;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.support.FakeAppmarket;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MinimalConnector.class}, webEnvironment = RANDOM_PORT)
public class ErrorHandlingWorks {
	@LocalServerPort
	private int localConnectorPort;
	private FakeAppmarket fakeAppmarket;

	@Before
	public void setUp() throws Exception {
		fakeAppmarket = FakeAppmarket.create(findAvailableTcpPort(), "isv-key", "isv-secret").start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarket.stop();
	}

	@Test
	public void whenEventIsNotFoundOnAppmarket_weReturnAPayloadMatchingMarketplaceFormat() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/nonExistant/v1/events/order");

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":false,\"errorCode\":\"NOT_FOUND\",\"message\":\"Failed to fetch event: Not Found\"}");
	}

	@Test
	public void whenHostOfEventIsUnknown_weReturnTheRightPayloadToAppmarket() throws Exception {
		HttpResponse response = fakeAppmarket.sendSignedRequestTo(connectorEventEndpoint(), asList("eventUrl", "http://does-not.exists"));

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":false,\"errorCode\":\"UNKNOWN_ERROR\",\"message\":\"Failed to process event. eventUrl=http://does-not.exists | exception=I/O error on GET request for \\\"http://does-not.exists\\\": does-not.exists; nested exception is java.net.UnknownHostException: does-not.exists\"}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
