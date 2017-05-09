package com.appdirect.sdk.appmarket.domain;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainDnsVerificationInfoController {

	private final DomainDnVerificationInfoHandler verificationInfoHandler;

	public DomainDnsVerificationInfoController(DomainDnVerificationInfoHandler verificationInfoHandler) {
		this.verificationInfoHandler = verificationInfoHandler;
	}

	@RequestMapping(
			method = GET,
			value = "/api/v1/integration/customers/{customerIdentifier}/domains/{domain}/ownershipProofRecord",
			produces = APPLICATION_JSON_VALUE
	)
	public TXTDnsRecord readOwnershipVerificationRecord(@PathVariable("customerIdentifier") String customerId,
														@PathVariable("domain") String domain) {


		return verificationInfoHandler.readOwnershipVerificationRecord(customerId, domain);
	}
}
