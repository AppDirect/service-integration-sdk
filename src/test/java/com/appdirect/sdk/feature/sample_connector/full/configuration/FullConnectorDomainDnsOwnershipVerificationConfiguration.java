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

package com.appdirect.sdk.feature.sample_connector.full.configuration;

import static java.util.stream.Collectors.toSet;
import static org.mockito.Mockito.spy;

import java.util.Set;
import java.util.stream.Stream;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.DomainAdditionHandler;
import com.appdirect.sdk.appmarket.domain.DomainDnsOwnershipVerificationConfiguration;
import com.appdirect.sdk.appmarket.domain.DomainDnsVerificationInfoHandler;
import com.appdirect.sdk.appmarket.domain.DomainOwnershipVerificationHandler;
import com.appdirect.sdk.appmarket.domain.DomainRemovalHandler;
import com.appdirect.sdk.appmarket.domain.DomainVerificationNotificationClient;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;

public class FullConnectorDomainDnsOwnershipVerificationConfiguration extends DomainDnsOwnershipVerificationConfiguration {

	@Override
	public DomainOwnershipVerificationHandler domainOwnershipVerificationHandler(DomainVerificationNotificationClient domainVerificationNotificationClient) {
		return (customerId, domain, callbackUrl, key) -> domainVerificationNotificationClient.resolveDomainVerification(callbackUrl, key, true);
	}

	@Override
	public DomainAdditionHandler domainAdditionHandler() {
		return (customerId, domain) -> {};
	}

	@Override
	public DomainRemovalHandler domainRemovalHandler() {
		return spy(new DomainRemovalHandlerImpl());
	}

	@Override
	public DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler() {
		return (customerId, domain) -> new DnsOwnershipVerificationRecords(generateTestTxtRecords(customerId, domain), generateTestMxDnsRecords());
	}

	private Set<MxDnsRecord> generateTestMxDnsRecords() {
		return Stream.of(new MxDnsRecord("@",
			3600,
			1,
			"abc.example.com."))
			.collect(toSet());
	}

	private Set<TxtDnsRecord> generateTestTxtRecords(String customerId, String domain) {
		return Stream.of(new TxtDnsRecord(
			"@",
			3600,
			"key1=value1"))
			.collect(toSet());
	}

	class DomainRemovalHandlerImpl implements DomainRemovalHandler {
		@Override
		public void removeDomain(String customerId, String domain) {
		}
	}
}
