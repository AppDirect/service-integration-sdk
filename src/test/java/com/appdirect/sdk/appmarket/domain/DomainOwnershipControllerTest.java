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

package com.appdirect.sdk.appmarket.domain;

import static com.appdirect.sdk.appmarket.domain.DomainOwnershipController.OWNERSHIP_PROOF_DNS_OPERATION_TYPE;
import static com.appdirect.sdk.appmarket.domain.DomainOwnershipController.SERVICE_CONFIGURATION_DNS_OPERATION_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@RunWith(MockitoJUnitRunner.class)
public class DomainOwnershipControllerTest {
	@Mock
	private DomainDnsVerificationInfoHandler mockDnsVerificationInfoHandler;
	@Mock
	private DomainServiceConfigurationHandler mockedDomainServiceConfigurationHandler;
	@Mock
	private DomainOwnershipVerificationHandler domainOwnershipVerificationHandler;
	@Mock
	private DomainAdditionHandler domainAdditionHandler;
	@Mock
	private DomainRemovalHandler domainRemovalHandler;
	@Mock
	private OAuthKeyExtractor keyExtractor;

	private DomainOwnershipController tested;

	@Before
	public void setUp() throws Exception {
		tested = new DomainOwnershipController(mockDnsVerificationInfoHandler,
			mockedDomainServiceConfigurationHandler,
				domainOwnershipVerificationHandler,
				domainAdditionHandler,
				domainRemovalHandler,
				keyExtractor);
	}

	@Test
	public void testReadOwnershipVerificationRecord_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.readOwnershipVerificationRecord(testCustomerId, testDomain);

		//Then
		verify(mockDnsVerificationInfoHandler).readOwnershipVerificationRecords(testCustomerId, testDomain);
	}

	@Test
	public void testReadDnsRecord_whenCalledWithTypeOwnershipProof_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.readDnsRecord(testCustomerId, testDomain, OWNERSHIP_PROOF_DNS_OPERATION_TYPE);

		//Then
		verify(mockDnsVerificationInfoHandler).readOwnershipVerificationRecords(testCustomerId, testDomain);
	}

	@Test
	public void testReadDnsRecord_whenCalledWithTypeServiceConfiguration_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.readDnsRecord(testCustomerId, testDomain, SERVICE_CONFIGURATION_DNS_OPERATION_TYPE);

		//Then
		verify(mockedDomainServiceConfigurationHandler).readServiceConfigurationRecords(testCustomerId, testDomain);
	}

	@Test
	public void testReadDnsRecord_whenCalledWithUnkownType_thenReturnsEmptyDnsRecords() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		final DnsRecords records = tested.readDnsRecord(testCustomerId, testDomain, "IamNotAValidType");

		//Then
		verifyZeroInteractions(mockDnsVerificationInfoHandler);
		verifyZeroInteractions(mockedDomainServiceConfigurationHandler);
		assertThat(records).isNotNull();
		assertThat(records.getMx()).isEmpty();
		assertThat(records.getTxt()).isEmpty();
		assertThat(records.getCname()).isEmpty();
		assertThat(records.getSrv()).isEmpty();
	}

	@Test
	public void testVerifyDomainOwnership_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";
		String callbackUrl = "http://appmarket.com/callback/url";
		String key = "the-key";
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(keyExtractor.extractFrom(request)).thenReturn(key);

		//When
		tested.verifyDomainOwnership(request, testCustomerId, testDomain, callbackUrl);

		//Then
		verify(domainOwnershipVerificationHandler).verifyDomainOwnership(testCustomerId, testDomain, callbackUrl, key);
	}

	@Test
	public void test_VerifyDomainOwnership_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";
		String callbackUrl = "http://appmarket.com/callback/url";
		String applicationUuid = "applicationUuid";
		HttpServletRequest request = mock(HttpServletRequest.class);
		//When
		tested.verifyDomainOwnership(request, testCustomerId, testDomain, callbackUrl, applicationUuid);

		//Then
		verify(domainOwnershipVerificationHandler).verifyDomainOwnership(testCustomerId, testDomain, callbackUrl, applicationUuid);
	}

	@Test
	public void testAddDomain_whenCalled_thenControllerForwardsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";
		DomainAdditionPayload domainAdditionPayload = new DomainAdditionPayload(testDomain);

		//When
		tested.addDomain(testCustomerId, domainAdditionPayload);

		//Then
		verify(domainAdditionHandler).addDomain(testCustomerId, testDomain);
	}

	@Test
	public void testRemoveDomain_whenCalled_thenControllerForwardsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.removeDomain(testCustomerId, testDomain);

		//Then
		verify(domainRemovalHandler).removeDomain(testCustomerId, testDomain);
	}
}
