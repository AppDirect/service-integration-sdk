package com.appdirect.sdk.feature.sample_connector.full;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.appdirect.sdk.appmarket.domain.DomainDnVerificationInfoHandler;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtRecordItem;

/**
 * Mock implementation of the {@link DomainDnVerificationInfoHandler} used in the integration tests:
 * returns a DNS record that returns the parameters passed to the method
 */
public class TestDomainDnsVerificationInfoHandler implements DomainDnVerificationInfoHandler {
	@Override
	public Set<TxtDnsRecord> readOwnershipVerificationRecords(String customerId, String domain) {
		List<TxtRecordItem> expectedEntries = Arrays.asList(
				new TxtRecordItem("customerIdentifier", customerId),
				new TxtRecordItem("domain", domain)
		);

		TxtDnsRecord txtDnsRecord = new TxtDnsRecord(
				"@",
				3600,
				new HashSet<>(expectedEntries));

		HashSet<TxtDnsRecord> records = new HashSet<>();
		records.add(txtDnsRecord);
		return records;
	}
}
