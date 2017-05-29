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

package com.appdirect.sdk.feature.sample_connector.full;

import org.springframework.beans.factory.annotation.Autowired;

import com.appdirect.sdk.appmarket.domain.DomainOwnershipVerificationHandler;
import com.appdirect.sdk.appmarket.domain.DomainVerificationNotificationClient;

/**
 * Mock implementation of the {@link com.appdirect.sdk.appmarket.domain.DomainOwnershipVerificationHandler} used in the integration tests:
 * will call the callbackUrl with a success
 */
public class TestDomainOwnershipVerificationHandler implements DomainOwnershipVerificationHandler {

	private final DomainVerificationNotificationClient domainVerificationNotificationClient;

	@Autowired
	public TestDomainOwnershipVerificationHandler(DomainVerificationNotificationClient domainVerificationNotificationClient) {
		this.domainVerificationNotificationClient = domainVerificationNotificationClient;
	}

	@Override
	public void verifyDomainOwnership(String customerId, String domain, String callbackUrl, String key) {
		domainVerificationNotificationClient.resolveDomainVerification(callbackUrl, key, true);
	}
}
