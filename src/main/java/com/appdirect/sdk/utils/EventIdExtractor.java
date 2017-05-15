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

package com.appdirect.sdk.utils;

import java.net.URI;

public final class EventIdExtractor {

	private EventIdExtractor() {
	}

	/**
	 * Extracts the id of the incoming AppMarket event from the eventUrl parameter passed to the connector.
	 * Check the AppDirect documentation for more details regarding the eventUrl parameter
	 *
	 * @param eventUrl a url from which the connector can fetch the payload of an incoming subscription event.
	 * @return the id of the incoming event
	 * @see <a href="http://google.com">https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events</a>
	 */
	public static String extractId(String eventUrl) {
		String path = URI.create(eventUrl).getPath();
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
