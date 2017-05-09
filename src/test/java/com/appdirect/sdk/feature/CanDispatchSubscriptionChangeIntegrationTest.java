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
public class CanDispatchSubscriptionChangeIntegrationTest {
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
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-change");

		assertThat(fakeAppmarket.allRequestPaths()).first().isEqualTo("/v1/events/subscription-change");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(202);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"Event with eventToken=subscription-change has been accepted by the connector. It will be processed soon.\"}");

		fakeAppmarket.waitForResolvedEvents(1);
		assertThat(fakeAppmarket.resolvedEvents()).contains("subscription-change");
		assertThat(fakeAppmarket.allRequestPaths()).last().isEqualTo("/api/integration/v1/events/subscription-change/result");
		assertThat(fakeAppmarket.lastRequestBody()).isEqualTo("{\"success\":true,\"message\":\"SUB_CHANGE for accountId=206123 has been processed, 25GB has been requested.\"}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
