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

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserUnassignment extends EventWithContextWithConfiguration {
	private UserInfo unassignedUser;
	private String accountId;

	public UserUnassignment(UserInfo unassignedUser,
	                        String accountId,
	                        String consumerKey,
	                        Map<String, String[]> queryParameters,
	                        EventFlag eventFlag,
	                        String eventToken,
	                        String marketplaceUrl,
	                        Map<String, String> configuration) {

		super(consumerKey, queryParameters, eventFlag, eventToken, marketplaceUrl, configuration);
		this.unassignedUser = unassignedUser;
		this.accountId = accountId;
	}

	@Deprecated
	public UserUnassignment(UserInfo unassignedUser,
	                        String accountId,
	                        String consumerKey,
	                        Map<String, String[]> queryParameters,
	                        EventFlag eventFlag,
	                        String eventToken,
	                        String marketplaceUrl) {

		this(unassignedUser, accountId, consumerKey, queryParameters,
			 eventFlag, eventToken, marketplaceUrl, new HashMap<>());
	}
}
