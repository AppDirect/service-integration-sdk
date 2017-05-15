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

import java.util.Optional;

/**
 * Determines if an event is related to an add-on subscription or to a regular subscription.
 * This is used to dispatch the add-on events to the add-on event handlers.
 */
public class AddonEventDetector {

	/**
	 * Determines if a given event is related to an add-on subscription or not.
	 *
	 * @param rawEvent the event to check; can't be null.
	 * @return <code>true</code> if the event is related to an add-on subscription; <code>false</code> otherwise.
	 */
	public boolean eventIsRelatedToAddon(EventInfo rawEvent) {
		return Optional.ofNullable(rawEvent.getPayload())
				.map(EventPayload::getAccount)
				.map(AccountInfo::getParentAccountIdentifier)
				.isPresent();
	}
}
