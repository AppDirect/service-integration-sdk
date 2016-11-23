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

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo(
			format("/v1/events/cancel?account-id=%s", expectedAccountId)
		);
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo(
			format("{\"success\":true,\"asynchronous\":false,\"message\":\"SUB_CANCEL %s has been processed, for real.\"}", expectedAccountId)
		);
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
