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

import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * abstract event type that offers the consumer key used by the request publishing the event,
 * the map of query parameters received with this event and the flag (if any) present on this event.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class EventWithContext {
	/**
	 * Returns the consumer key that was used by the appmarket to publish this event.
	 * You can use this to determine which product is the originator of this event.
	 */
	private String consumerKeyUsedByTheRequest;
	private Map<String, String[]> queryParameters;
	@Getter(AccessLevel.NONE)
	private EventFlag flag;
	private String eventToken;
	private String marketplaceUrl;
	private Map<String, String> configuration = new HashMap<>();
	
	/**
	 * Returns the query parameters that were passed to the endpoint when this event was received.
	 * i.e. calling <code>/processEvent?eventUrl=some-url&amp;themeColor=yellow&amp;themeColor=red</code> would yield
	 * a map with 2 entries: <code>eventUrl=[some-url]</code> and <code>themeColor=[yellow, red]</code>.
	 *
	 * @return an unmodifiable view of the query parameters map.
	 */
	public Map<String, String[]> getQueryParameters() {
	    return new HashMap<>(queryParameters == null ? new HashMap<>() : queryParameters);
	}

	/**
	 * Returns the configuration that was passed to the endpoint when this event was received.
	 *
	 * @return an unmodifiable view of the configuration map.
	 */
	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration == null ? new HashMap<>() : configuration);
	}

	/**
	 * Returns whether this event is a development event or not. If it is a development event, implementors
	 * should not make any permanent changes to external systems (billing, emails, etc.) Development events are
	 * sent as part of the INTEGRATION REPORT feature of the marketplace.
	 *
	 * @return <code>true</code> if the event is a development event; <code>false</code> otherwise.
	 */
	public boolean isDevelopment() {
		return flag == DEVELOPMENT;
	}
}
