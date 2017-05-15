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
public class Returns401WhenNotAuthenticated {
	@LocalServerPort
	private int localConnectorPort;
	private FakeAppmarket fakeAppmarketWithWrongCredentials;

	@Before
	public void setUp() throws Exception {
		fakeAppmarketWithWrongCredentials = FakeAppmarket.create(findAvailableTcpPort(), "wrong-key", "wrong-secret").start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarketWithWrongCredentials.stop();
	}

	@Test
	public void unauthorizedIsReturned_whenBadCredentialsArePassed() throws Exception {
		HttpResponse response = fakeAppmarketWithWrongCredentials.sendEventTo(connectorEventEndpoint(), "/some/event");

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
