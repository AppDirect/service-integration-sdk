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
package com.appdirect.sdk.appmarket.saml;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY, fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Certificate {

	/**
	 * UUID of certificate
	 */
	private String uuid;

	/**
	 * Public certificate
	 */
	private String publicCertificate;

	/**
	 * Public key
	 */
	private String publicKey;

	/**
	 * Finger print
	 */
	private String fingerprint;

	/**
	 * Expiration Date
	 */
	private Date expirationDate;

	/**
	 * Partner
	 */
	private String partner;

	/**
	 * Label
	 */
	private String label;

	/**
	 * Default certificate indicator
	 */
	private boolean defaultCertificate = true;

	/**
	 * Subject DN
	 */
	private String subjectDN;

	/**
	 * Key size
	 */
	private Integer keySize;
	private boolean needsRenewal;
}
