package com.appdirect.sdk.domain;

import java.util.List;
import java.util.Set;

import com.appdirect.sdk.domain.model.DnsRecordModel;
import com.appdirect.sdk.domain.model.DomainModel;
import com.appdirect.sdk.domain.model.LegalTermsModel;

public interface DomainService {
	/**
	 * Read some domain suggestions for provided string.
	 */
	List<DomainModel> readSuggestions(String query, int limit, String currency, Set<String> tlds);

	/**
	 * Read domain pricing
	 */
	DomainModel readDomainPricing(String domain, String currency);

	/**
	 * Sets a dns record on a domain
	 */
	void setDnsRecord(String domain, String type, DnsRecordModel model);

	/**
	 * Read Legal Terms for given domains.
	 */
	List<LegalTermsModel> readLegalTerms(String domain, boolean privacy);
}
