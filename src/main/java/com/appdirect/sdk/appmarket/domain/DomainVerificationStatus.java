package com.appdirect.sdk.appmarket.domain;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
public class DomainVerificationStatus  {
	private final boolean verified;
	@JsonCreator
	public DomainVerificationStatus(@JsonProperty("verified") boolean verified) {
		this.verified = verified;
	}
}
