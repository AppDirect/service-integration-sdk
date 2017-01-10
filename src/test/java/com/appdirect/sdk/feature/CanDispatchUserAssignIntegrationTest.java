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

import com.appdirect.sdk.support.FakeAppmarket;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MinimalConnector.class, webEnvironment = RANDOM_PORT)
public class CanDispatchUserAssignIntegrationTest {
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
	public void userAssignIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/user-assign");

		assertThat(fakeAppmarket.allRequestPaths()).first().isEqualTo("/v1/events/user-assign");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(202);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"Event with eventId=user-assign has been accepted by the connector. It will be processed soon.\"}");

		fakeAppmarket.waitForResolvedEvents(1);
		assertThat(fakeAppmarket.resolvedEvents()).contains("user-assign");
		assertThat(fakeAppmarket.allRequestPaths()).last().isEqualTo("/api/integration/v1/events/user-assign/result");
		assertThat(fakeAppmarket.lastRequestBody()).isEqualTo("{\"success\":true,\"message\":\"USER_ASSIGN for user 09c94342-38bf-4bc3-bceb-8bb1f75b8454 for account 4bcd8661-25ee-4c44-9b65-82dd532eeee7 has been processed, for real.\"}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
