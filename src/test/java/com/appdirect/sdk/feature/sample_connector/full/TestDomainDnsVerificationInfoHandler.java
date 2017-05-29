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

import java.util.HashSet;
import java.util.Set;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.DomainDnsVerificationInfoHandler;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;

/**
 * Mock implementation of the {@link com.appdirect.sdk.appmarket.domain.DomainDnsVerificationInfoHandler} used in the integration tests:
 * returns a DNS record that returns the parameters passed to the method
 */
public class TestDomainDnsVerificationInfoHandler implements DomainDnsVerificationInfoHandler {
	@Override
	public DnsOwnershipVerificationRecords readOwnershipVerificationRecords(String customerId, String domain) {
		Set<TxtDnsRecord> txtDnsRecords = generateTestTxtRecords(customerId, domain);
		Set<MxDnsRecord> mxDnsRecords = generateTestMxDnsRecords();

		return new DnsOwnershipVerificationRecords(txtDnsRecords, mxDnsRecords);
	}

	private Set<MxDnsRecord> generateTestMxDnsRecords() {
		MxDnsRecord mxRecord = new MxDnsRecord("@", 3600, 1, "abc.example.com.");
		Set<MxDnsRecord> mxDnsRecords = new HashSet<>();
		mxDnsRecords.add(mxRecord);
		return mxDnsRecords;
	}

	private Set<TxtDnsRecord> generateTestTxtRecords(String customerId, String domain) {

		TxtDnsRecord txtDnsRecord = new TxtDnsRecord(
				"@",
				3600,
				"key1=value1");

		HashSet<TxtDnsRecord> txtDnsRecords = new HashSet<>();
		txtDnsRecords.add(txtDnsRecord);
		return txtDnsRecords;
	}
}
