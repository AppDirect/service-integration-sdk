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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
	private DomainOwnershipVerificationHandler domainOwnershipVerificationHandler;
	@Mock
	private DomainAdditionHandler domainAdditionHandler;
	@Mock
	private OAuthKeyExtractor keyExtractor;

	private DomainOwnershipController tested;

	@Before
	public void setUp() throws Exception {
		tested = new DomainOwnershipController(mockDnsVerificationInfoHandler, domainOwnershipVerificationHandler, domainAdditionHandler, keyExtractor);
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
}
