package com.appdirect.sdk.feature;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
@SpringBootTest(classes = MinimalConnector.class, webEnvironment = RANDOM_PORT)
public class CanDispatchSubscriptionCancelIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	private FakeAppmarket fakeAppmarket;

	@Before
	public void setUp() throws Exception {
		fakeAppmarket = FakeAppmarket.create(localConnectorPort + 1, "isv-key", "isv-secret").start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarket.stop();
	}

	@Test
	public void subscriptionCancelIsProcessedSuccessfully() throws Exception {
		String expectedAccountId = "123";
		HttpResponse response = fakeAppmarket.sendEventTo(
				connectorEventEndpoint(),
				format("v1/events/cancel?account-id=%s", expectedAccountId)
		);

		assertThat(fakeAppmarket.allRequestPaths()).contains("/v1/events/cancel?account-id=123");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(202);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"Event has been accepted by the connector. It will be processed soon.\"}");

		// TODO: wait for the event result to be POSTed to /api/integration/v1/events/{eventUrl}/result
		//assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/api/integration/v1/events/cancel/result");
		//assertThat(fakeAppmarket.lastRequestBody()).isEqualTo("\"{\"success\":true}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
