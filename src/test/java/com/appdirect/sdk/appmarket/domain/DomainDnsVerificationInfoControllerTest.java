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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DomainDnsVerificationInfoControllerTest {

	@Mock
	private DomainDnVerificationInfoHandler mockDnsVerificationInfoHandler;

	private DomainDnsOwnershipVerificationInfoController tested;

	@Before
	public void setUp() throws Exception {
		tested = new DomainDnsOwnershipVerificationInfoController(mockDnsVerificationInfoHandler);
	}

	@Test
	public void testREadOwnershipVerificationRecord_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.readOwnershipVerificationRecord(testCustomerId, testDomain);

		//Then
		verify(mockDnsVerificationInfoHandler)
				.readOwnershipVerificationRecords(eq(testCustomerId), eq(testDomain));
	}
}
