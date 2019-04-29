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

package com.appdirect.sdk.credentials;

import static com.appdirect.sdk.appmarket.Credentials.invalidCredentials;

import java.util.Map;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

/**
 * Creates an in-memory cache of (developer key ⟶ developer secret) pairs from a string of the following format:
 * <p>
 * devKey1:devPassword1,devKey2:devPassTwo
 * @deprecated will be replaced by {@link SourceBackedCredentialsSupplier}
 */
@Deprecated
public class StringBackedCredentialsSupplier implements DeveloperSpecificAppmarketCredentialsSupplier {
	private final Map<String, String> allowedCredentials;

	public StringBackedCredentialsSupplier(String rawAllowedCredentials) {
		this.allowedCredentials = MapBuilderUtils.fromCommaDelimitedKeyValuePairs(rawAllowedCredentials);
	}

	@Override
	public Credentials getConsumerCredentials(String consumerKey) {
		String secret = allowedCredentials.get(consumerKey);
		return secret == null ? invalidCredentials() : new Credentials(consumerKey, secret);
	}
}
