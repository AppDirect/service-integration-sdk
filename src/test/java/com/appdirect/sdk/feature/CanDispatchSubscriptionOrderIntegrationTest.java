package com.appdirect.sdk.feature;

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

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.support.FakeAppmarket;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class CanDispatchSubscriptionOrderIntegrationTest {
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
	public void subscriptionOrderIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-order");

		assertThat(fakeAppmarket.allRequestPaths()).first().isEqualTo("/v1/events/subscription-order");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(202);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"Event with eventId=subscription-order has been accepted by the connector. It will be processed soon.\"}");

		fakeAppmarket.waitForResolvedEvents(1);
		assertThat(fakeAppmarket.resolvedEvents()).contains("subscription-order");
		assertThat(fakeAppmarket.allRequestPaths()).last().isEqualTo("/api/integration/v1/events/subscription-order/result");
		assertThat(fakeAppmarket.lastRequestBody()).isEqualTo("{\"success\":true,\"message\":\"SUB_ORDER has been processed, trust me.\"}");
	}

	@Test
	public void subscriptionOrderFailsAndIsReportedToAppMarket() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-order-without-creator");

		assertThat(fakeAppmarket.allRequestPaths()).first().isEqualTo("/v1/events/subscription-order-without-creator");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(202);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"Event with eventId=subscription-order-without-creator has been accepted by the connector. It will be processed soon.\"}");

		fakeAppmarket.waitForResolvedEvents(1);
		assertThat(fakeAppmarket.resolvedEvents()).contains("subscription-order-without-creator");
		assertThat(fakeAppmarket.allRequestPaths()).last().isEqualTo("/api/integration/v1/events/subscription-order-without-creator/result");
		assertThat(fakeAppmarket.lastRequestBody()).isEqualTo("{\"success\":false,\"errorCode\":\"USER_NOT_FOUND\",\"message\":\"You should always have a creator\"}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
