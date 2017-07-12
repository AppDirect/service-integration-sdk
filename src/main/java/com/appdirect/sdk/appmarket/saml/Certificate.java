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
