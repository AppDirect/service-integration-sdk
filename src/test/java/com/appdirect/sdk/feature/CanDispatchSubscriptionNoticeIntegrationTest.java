package com.appdirect.sdk.feature;

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
public class CanDispatchSubscriptionNoticeIntegrationTest {
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
	public void subscriptionClosedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "v1/events/closed");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/closed");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_CLOSED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionDeactivatedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "v1/events/deactivated");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/deactivated");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_DEACTIVATED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionReactivatedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "v1/events/reactivated");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/reactivated");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_REACTIVATED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionUpcomingInvoiceIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "v1/events/upcoming-invoice");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/upcoming-invoice");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_INVOICE a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
