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
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

import java.io.IOException;
import java.net.URI;

import javax.xml.ws.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.DomainAdditionPayload;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;
import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.support.FakeAppmarket;
import com.appdirect.sdk.web.oauth.OAuthSignedClientHttpRequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class CanVerifyDomainsWhenModuleImportedIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ObjectMapper objectMapper;

	private static String testOauthKey;
	private static String testOauthSecret;
	private static RestTemplate restTemplate;
	private FakeAppmarket fakeAppmarket;

	static {
		testOauthKey = "isv-key";
		testOauthSecret = "isv-secret";
		restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(testOauthKey, testOauthSecret));
	}

	private static final String DOMAIN_ADDITION_ENDPOINT_TEMPLATE =
			"http://localhost:%d/api/v1/domainassociation/customers/%s/domains";
	private static final String DOMAIN_OWNERSHIP_PROOF_ENDPOINT_TEMPLATE =
			"http://localhost:%d/api/v1/domainassociation/customers/%s/domains/%s/ownershipProofRecord";
	private static final String DOMAIN_OWNERSHIP_VERIFICATION_ENDPOINT_TEMPLATE =
			"http://localhost:%d/api/v1/domainassociation/customers/%s/domains/%s/ownershipVerification";
	private static final String APPMARKET_DOMAIN_VERIFICATION_CALLBACK_TEMPLATE =
			"/api/integration/v1/customers/%s/domains/%s/verificationStatus";

	@Before
	public void setup() throws IOException {
		fakeAppmarket = FakeAppmarket.create(findAvailableTcpPort(), testOauthKey, testOauthSecret).start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarket.stop();
	}


	@Test
	public void testGetOwnershipProofRecord_whenCalled_txtRecordIsReturned() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		TxtDnsRecord expectedTxtRecord = new TxtDnsRecord(
				"@",
				3600,
				"key1=value1");
		MxDnsRecord expectedMxRecord = new MxDnsRecord(
				"@",
				3600,
				1,
				"abc.example.com."
		);

		//When
		ResponseEntity<DnsOwnershipVerificationRecords> actualRecord = restTemplate.exchange(
				formatEndpoint(DOMAIN_OWNERSHIP_PROOF_ENDPOINT_TEMPLATE, localConnectorPort, testCustomerId, testDomain),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<DnsOwnershipVerificationRecords>() {
				}
		);

		//Then
		assertThat(actualRecord.getBody().getTxt()).containsExactly(expectedTxtRecord);
		assertThat(actualRecord.getBody().getMx()).containsExactly(expectedMxRecord);
	}

	@Test
	public void testValidateOwnership() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		String appmarketCallbackPath = formatEndpoint(APPMARKET_DOMAIN_VERIFICATION_CALLBACK_TEMPLATE, testCustomerId, testDomain);
		fakeAppmarket.sendTriggerDomainVerificationTo(
				formatEndpoint(DOMAIN_OWNERSHIP_VERIFICATION_ENDPOINT_TEMPLATE, localConnectorPort, testCustomerId, testDomain),
				appmarketCallbackPath
		);

		fakeAppmarket.waitForDomainVerifications(1);
		assertThat(fakeAppmarket.lastRequestPath()).isEqualTo(appmarketCallbackPath);
		assertThat(fakeAppmarket.domainVerificationStatuses().get(0).isVerified()).isTrue();
	}

	@Test
	public void testAddDomain() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		ResponseEntity<Void> actual = restTemplate.postForEntity(
				formatEndpoint(DOMAIN_ADDITION_ENDPOINT_TEMPLATE, localConnectorPort, testCustomerId, testDomain),
				new DomainAdditionPayload("domain2.com"),
				Void.class
		);

		assertThat(actual.getStatusCode()).isEqualTo(OK);
	}

	public String formatEndpoint(String endpointTemplate, Object... attributes) {
		return String.format(endpointTemplate, attributes);
	}
}
