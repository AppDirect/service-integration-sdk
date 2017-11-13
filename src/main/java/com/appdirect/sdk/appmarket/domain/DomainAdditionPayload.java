package com.appdirect.sdk.appmarket.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
public class DomainAdditionPayload {
	private String domainName;
}
