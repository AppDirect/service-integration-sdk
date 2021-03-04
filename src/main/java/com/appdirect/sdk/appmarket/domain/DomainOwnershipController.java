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
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@RestController
public class DomainOwnershipController {

	public static final String OWNERSHIP_PROOF_DNS_OPERATION_TYPE = "ownershipProof";
	public static final String SERVICE_CONFIGURATION_DNS_OPERATION_TYPE = "configuration";
	public static final String V1_PATH = "/api/v1";
	public static final String V2_PATH = "/api/v2";
	public static final String V2_BASIC_PATH = "/api/v2/basic";
	public static final String READ_OWNERSHIP_RECORD_PATH = "/domainassociation/customers/{customerIdentifier}/domains/{domain}/ownershipProofRecord";
	public static final String READ_DNS_RECORD_PATH = "/domainassociation/customers/{customerIdentifier}/domains/{domain}/dns";
	public static final String VERIFY_DOMAIN_OWNERSHIP_PATH = "/domainassociation/customers/{customerIdentifier}/domains/{domain}/ownershipVerification";
	public static final String ADD_DOMAIN_PATH = "/domainassociation/customers/{customerIdentifier}/domains";
	public static final String REMOVE_DOMAIN_PATH = "/domainassociation/customers/{customerIdentifier}/domains/{domainName}";

	private final DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler;
	private final DomainServiceConfigurationHandler domainServiceConfigurationHandler;
	private final DomainOwnershipVerificationHandler domainOwnershipVerificationHandler;
	private final DomainAdditionHandler domainAdditionHandler;
	private final DomainRemovalHandler domainRemovalHandler;
	private final OAuthKeyExtractor keyExtractor;

	DomainOwnershipController(DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler,
														DomainServiceConfigurationHandler domainServiceConfigurationHandler,
														DomainOwnershipVerificationHandler domainOwnershipVerificationHandler,
														DomainAdditionHandler domainAdditionHandler,
														DomainRemovalHandler domainRemovalHandler,
														OAuthKeyExtractor keyExtractor) {
		this.domainDnsVerificationInfoHandler = domainDnsVerificationInfoHandler;
		this.domainServiceConfigurationHandler = domainServiceConfigurationHandler;
		this.domainOwnershipVerificationHandler = domainOwnershipVerificationHandler;
		this.domainAdditionHandler = domainAdditionHandler;
		this.domainRemovalHandler = domainRemovalHandler;
		this.keyExtractor = keyExtractor;
	}


	/**
	 * @deprecated
	 * Use readDnsRecord with the type OWNERSHIP_PROOF_DNS_OPERATION_TYPE instead.
	 */
	@Deprecated
	@RequestMapping(
			method = GET,
			value = {V1_PATH + READ_OWNERSHIP_RECORD_PATH, V2_PATH + READ_OWNERSHIP_RECORD_PATH, V2_BASIC_PATH + READ_OWNERSHIP_RECORD_PATH},
			produces = APPLICATION_JSON_VALUE
	)
	public DnsRecords readOwnershipVerificationRecord(@PathVariable("customerIdentifier") String customerId,
																										@PathVariable("domain") String domain) {

		return readDnsRecord(customerId, domain, OWNERSHIP_PROOF_DNS_OPERATION_TYPE);
	}

	@RequestMapping(
		method = GET,
			value = {V1_PATH + READ_DNS_RECORD_PATH, V2_PATH + READ_DNS_RECORD_PATH, V2_BASIC_PATH + READ_DNS_RECORD_PATH},
		produces = APPLICATION_JSON_VALUE
	)
	public DnsRecords readDnsRecord(
		@PathVariable("customerIdentifier") String customerId,
		@PathVariable("domain") String domain,
		@RequestParam("type") String type) {

		if (SERVICE_CONFIGURATION_DNS_OPERATION_TYPE.equalsIgnoreCase(type)) {
			return domainServiceConfigurationHandler.readServiceConfigurationRecords(customerId, domain);
		} else if (OWNERSHIP_PROOF_DNS_OPERATION_TYPE.equalsIgnoreCase(type)) {
			return domainDnsVerificationInfoHandler.readOwnershipVerificationRecords(customerId, domain);
		} else {
			return DnsRecords.empty();
		}
	}

	@RequestMapping(
			method = POST,
			value = V1_PATH + VERIFY_DOMAIN_OWNERSHIP_PATH
	)
	@ResponseStatus(value = ACCEPTED)
	public void verifyDomainOwnership(HttpServletRequest request,
									  @PathVariable("customerIdentifier") String customerId,
									  @PathVariable("domain") String domain,
									  @RequestParam("callbackUrl") String callbackUrl) {

		String key = keyExtractor.extractFrom(request);
		domainOwnershipVerificationHandler.verifyDomainOwnership(customerId, domain, callbackUrl, key);
	}

	@RequestMapping(
			method = POST,
			value = {V2_PATH + VERIFY_DOMAIN_OWNERSHIP_PATH, V2_BASIC_PATH + VERIFY_DOMAIN_OWNERSHIP_PATH}
	)
	@ResponseStatus(value = ACCEPTED)
	public void verifyDomainOwnership(HttpServletRequest request,
									  @PathVariable("customerIdentifier") String customerId,
									  @PathVariable("domain") String domain,
									  @RequestParam("callbackUrl") String callbackUrl,
									  @RequestParam("applicationUuid") String applicationUuid) {

		domainOwnershipVerificationHandler.verifyDomainOwnership(customerId, domain, callbackUrl, applicationUuid);
	}

	@RequestMapping(
			method = POST,
			value = {V1_PATH + ADD_DOMAIN_PATH, V2_PATH + ADD_DOMAIN_PATH, V2_BASIC_PATH + ADD_DOMAIN_PATH}
	)
	@ResponseStatus(value = OK)
	public void addDomain(@PathVariable("customerIdentifier") String customerId,
						  @RequestBody DomainAdditionPayload domainAdditionPayload) {

		domainAdditionHandler.addDomain(customerId, domainAdditionPayload.getDomainName());
	}

	@RequestMapping(
			method = DELETE,
			value = {V1_PATH + REMOVE_DOMAIN_PATH, V2_PATH + REMOVE_DOMAIN_PATH, V2_BASIC_PATH + REMOVE_DOMAIN_PATH}
	)
	@ResponseStatus(value = OK)
	public void removeDomain(@PathVariable("customerIdentifier") String customerId,
						  @PathVariable("domainName") String domainName) {

		domainRemovalHandler.removeDomain(customerId, domainName);
	}
}
