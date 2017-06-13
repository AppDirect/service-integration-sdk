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

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@RestController
@RequestMapping("/api/v1/domainassociation")
public class DomainOwnershipController {

	private final DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler;
	private final DomainOwnershipVerificationHandler domainOwnershipVerificationHandler;
	private final OAuthKeyExtractor keyExtractor;

	DomainOwnershipController(DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler,
							  DomainOwnershipVerificationHandler domainOwnershipVerificationHandler, OAuthKeyExtractor keyExtractor) {
		this.domainDnsVerificationInfoHandler = domainDnsVerificationInfoHandler;
		this.domainOwnershipVerificationHandler = domainOwnershipVerificationHandler;
		this.keyExtractor = keyExtractor;
	}

	@RequestMapping(
			method = GET,
			value = "/customers/{customerIdentifier}/domains/{domain}/ownershipProofRecord",
			produces = APPLICATION_JSON_VALUE
	)
	public DnsOwnershipVerificationRecords readOwnershipVerificationRecord(@PathVariable("customerIdentifier") String customerId,
																		   @PathVariable("domain") String domain) {

		return domainDnsVerificationInfoHandler.readOwnershipVerificationRecords(customerId, domain);
	}

	@RequestMapping(
			method = POST,
			value = "/customers/{customerIdentifier}/domains/{domain}/ownershipVerification"
	)
	@ResponseStatus(value = ACCEPTED)
	public void verifyDomainOwnership(HttpServletRequest request,
									  @PathVariable("customerIdentifier") String customerId,
									  @PathVariable("domain") String domain,
									  @RequestParam("callbackUrl") String callbackUrl) {

		String key = keyExtractor.extractFrom(request);
		domainOwnershipVerificationHandler.verifyDomainOwnership(customerId, domain, callbackUrl, key);
	}

}
