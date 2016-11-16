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
public class Returns401WhenNotAuthenticated {
	@LocalServerPort
	private int localConnectorPort;
	private FakeAppmarket fakeAppmarketWithWrongCredentials;

	@Before
	public void setUp() throws Exception {
		fakeAppmarketWithWrongCredentials = FakeAppmarket.create(localConnectorPort + 1, "wrong-key", "wrong-secret").start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarketWithWrongCredentials.stop();
	}

	@Test
	public void unauthorizedIsReturned_whenBadCredentialsArePassed() throws Exception {
		HttpResponse response = fakeAppmarketWithWrongCredentials.sendEventTo(connectorEventEndpoint(), "some/event");

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(401);
		assertThat(EntityUtils.toString(response.getEntity())).matches("\\{\"timestamp\":\".*\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Invalid signature for signature method HMAC-SHA1\",\"path\":\"/api/v1/integration/processEvent\"\\}");
	}

	private String connectorEventEndpoint() {
		return baseConnectorUrl() + "/api/v1/integration/processEvent";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
