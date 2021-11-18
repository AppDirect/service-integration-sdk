/*
 * Copyright 2018 AppDirect, Inc. and/or its affiliates
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

package com.appdirect.sdk.appmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.UUID;

import lombok.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.support.FakeAppmarket;
import wiremock.org.apache.commons.lang3.RandomStringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class DomainOwnershipControllerIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	@Autowired
	private DomainRemovalHandler domainRemovalHandler;
	private FakeAppmarket fakeAppmarket;

	@Before
	public void setUp() throws Exception {
		fakeAppmarket = FakeAppmarket.create(localConnectorPort + 1, "isv-key", "isv-secret").start();
	}

	@After
	public void stop() {
		fakeAppmarket.stop();
	}

	/**
	 * This test is added to assure that domains as path parameter are processed properly. This was assured with
	 * {@link com.appdirect.sdk.web.config.MvcConfiguration#configurePathMatch(PathMatchConfigurer)}.
	 */
	@Test
	public void testRemoveDomain_givenDomainAsPathParameter_shouldProcessDomainProperly() throws Exception {
		String customerId = UUID.randomUUID().toString();
		String domain = RandomStringUtils.randomAlphanumeric(8) + ".com";
		HttpResponse httpResponse = fakeAppmarket.sendSignedDeleteRequestTo(domainRemovalEndpoint(customerId, domain));

		assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		verify(domainRemovalHandler).removeDomain(customerId, domain);
	}

	private String domainRemovalEndpoint(@NonNull String customerId, @NonNull String domain) {
		return baseConnectorUrl() + "/api/v1/domainassociation/customers/" + customerId + "/domains/" + domain;
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
