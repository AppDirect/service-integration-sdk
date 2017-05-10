package com.appdirect.sdk.appmarket.domain;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainDnsVerificationInfoController {

	private final DomainDnVerificationInfoHandler handler;

	DomainDnsVerificationInfoController(DomainDnVerificationInfoHandler handler) {
		this.handler = handler;
	}

	@RequestMapping(
			method = GET,
			value = "/api/v1/integration/customers/{customerIdentifier}/domains/{domain}/ownershipProofRecord",
			produces = APPLICATION_JSON_VALUE
	)
	public Set<TxtDnsRecord> readOwnershipVerificationRecord(@PathVariable("customerIdentifier") String customerId,
															 @PathVariable("domain") String domain) {

		return handler.readOwnershipVerificationRecords(customerId, domain);
	}
}
