package com.appdirect.sdk.appmarket.saml;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderInformation {
	private String version;
	private String uuid;
	private String idpIdentifier;
	private String assertionConsumerServiceUrl;
	private String spLaunchUrl;
	private String audienceUrl;
	private String loginUrl;
	private String relayState;
	private String nameIdType;
	private String nameId;
	private String notBeforeMinutes;
	private String notAfterMinutes;
	private Certificate certificate;
	private Map<String, SamlRelyingPartyAttribute> attributes = new HashMap<>();
}
