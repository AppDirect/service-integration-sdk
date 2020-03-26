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

package com.appdirect.sdk.appmarket.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.Link;

/**
 * To see what is mandatory or not, consult https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#attributes
 */
@Slf4j
class SubscriptionOrderEventParser implements EventParser<SubscriptionOrder> {
	@Override
	public SubscriptionOrder parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		String samlIdpUrl = null;
		try {
			samlIdpUrl = eventInfo.getLinks().stream().filter(link -> link.getRel().equals("samlIdp")).findFirst().map(Link::getHref).orElse(null);
		} catch (Exception e) {
			log.warn("Error when recovering samlIdp, proceeding with empty value", e);
		}

		return new SubscriptionOrder(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getFlag(),
				eventInfo.getCreator(),
				eventInfo.getPayload().getConfiguration(),
				eventInfo.getPayload().getCompany(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getMarketplace().getPartner(),
				eventInfo.getApplicationUuid(),
				eventContext.getQueryParameters(),
				eventInfo.getId(),
				eventInfo.getMarketplace().getBaseUrl(),
				samlIdpUrl
		);
	}
}
