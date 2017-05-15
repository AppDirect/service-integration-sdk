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

import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;

import java.util.Map;

/**
 * Helper class to instantiate <code>EventHandlingContext</code>s.
 * Needs to be in this package because class is package-private.
 */
public class EventHandlingContexts {
	private EventHandlingContexts() {

	}

	public static EventHandlingContext defaultEventContext() {
		return eventContext("some-key");
	}

	public static EventHandlingContext eventContext(String consumerKey) {
		return eventContext(consumerKey, oneQueryParam());
	}

	public static EventHandlingContext eventContext(String consumerKey, Map<String, String[]> queryParam) {
		return new EventHandlingContext(consumerKey, queryParam);
	}
}
