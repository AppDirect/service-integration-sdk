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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.hateoas.Link;

/**
 * Representation of the JSON payload of an event received from the AppMarket.
 * SDK internal, a user of the SDK should never interact with those directly.
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
class EventInfo {
	private EventType type;
	private MarketInfo marketplace;
	private String applicationUuid;
	private EventFlag flag;
	private UserInfo creator;
	private EventPayload payload;
	private String returnUrl;
	@Setter
	private String id;
	@Builder.Default
	private List<Link> links = new ArrayList<>();

	public List<Link> getLinks() {
		return Optional.ofNullable(links).orElseGet(ArrayList::new);
	}
}
