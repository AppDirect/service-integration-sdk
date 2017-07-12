package com.appdirect.sdk.appmarket.saml;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a SAML relying party attribute as a key value pair
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SamlRelyingPartyAttribute {

	/**
	 * Type
	 */
	@NotNull
	private String type;

	/**
	 * Value
	 */
	@NotNull
	private String value;
}
