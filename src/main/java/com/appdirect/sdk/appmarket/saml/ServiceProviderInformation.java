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
