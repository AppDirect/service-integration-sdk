/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class CanDispatchSubscriptionNoticeIntegrationTest {
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
	public void subscriptionClosedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-closed");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/subscription-closed");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_CLOSED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionDeactivatedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-deactivated");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/subscription-deactivated");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_DEACTIVATED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionReactivatedIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-reactivated");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/subscription-reactivated");
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":true,\"message\":\"SUB_REACTIVATED a3f72246-5377-4d92-8bdc-b1b6b450c55c has been processed, for real.\"}");
	}

	@Test
	public void subscriptionUpcomingInvoiceIsProcessedSuccessfully() throws Exception {
		HttpResponse response = fakeAppmarket.sendEventTo(connectorEventEndpoint(), "/v1/events/subscription-upcoming-invoice");

		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo("/v1/events/subscription-upcoming-invoice");
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
