package com.appdirect.sdk.feature.sample_connector.full;

import java.util.HashSet;
import java.util.Set;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.DomainDnVerificationInfoHandler;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;

/**
 * Mock implementation of the {@link DomainDnVerificationInfoHandler} used in the integration tests:
 * returns a DNS record that returns the parameters passed to the method
 */
public class TestDomainDnsVerificationInfoHandler implements DomainDnVerificationInfoHandler {
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
