package com.appdirect.sdk.feature;

import static com.appdirect.sdk.web.oauth.RequestIdFilter.REQUEST_ID_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.minimal.MinimalConnector;
import com.appdirect.sdk.support.FakeAppmarket;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MinimalConnector.class, webEnvironment = RANDOM_PORT)
public class RequestIdFilterIntegrationTest {

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
	public void requestIdShouldBeAddedProperly() throws Exception {
		//Given
		String requestId = "requestId";

		//When
		HttpResponse response = fakeAppmarket.callDummyRestController(dummyEndpointUrl(), new BasicHeader(REQUEST_ID_HEADER, requestId));
		String requestIdFromMDC = EntityUtils.toString(response.getEntity());

		//Then
		assertThat(requestIdFromMDC).isEqualTo(requestId);
	}

	private String dummyEndpointUrl() {
		return baseConnectorUrl() + "/api/v1/dummy";
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
