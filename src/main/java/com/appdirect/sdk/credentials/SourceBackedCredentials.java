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

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

import java.util.function.Function;

import static com.appdirect.sdk.appmarket.Credentials.invalidCredentials;

/**
 *
 */
public class SourceBackedCredentials implements DeveloperSpecificAppmarketCredentialsSupplier {
	private final Function<String, Credentials> credentialsFinder;

	public SourceBackedCredentials(Function<String, Credentials> credentialsFinder) {
		this.credentialsFinder = credentialsFinder;
	}

	@Override
	public Credentials getConsumerCredentials(String consumerKey) {
		return credentialsFinder.apply(consumerKey);
	}
}
