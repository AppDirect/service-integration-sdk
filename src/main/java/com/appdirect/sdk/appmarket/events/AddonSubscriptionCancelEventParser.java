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

class AddonSubscriptionCancelEventParser implements EventParser<AddonSubscriptionCancel> {
	@Override
	public AddonSubscriptionCancel parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new AddonSubscriptionCancel(
			eventInfo.getPayload().getAccount().getAccountIdentifier(),
			eventInfo.getPayload().getAccount().getParentAccountIdentifier(),
			eventContext.getConsumerKeyUsedByTheRequest(),
			eventContext.getQueryParameters(),
			eventInfo.getFlag(),
			eventInfo.getId(),
			eventInfo.getMarketplace().getBaseUrl(),
			eventInfo.getPayload().getConfiguration()
		);
	}
}
