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

import java.util.function.Function;

import org.assertj.core.api.Condition;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceBackedCredentialsTest {


	private Function<String, Credentials> functionTest = (String k) ->  {
		if (k.equalsIgnoreCase("bad-key")) {
			return new Credentials("this key does not exist in the supplier",
					"this key does not exist in the supplier");
		}
		return new Credentials(k,"s1");
	};

	@Test
	public void returnsGoodCredentials_whenKeyIsFound() throws Exception {
		//Given
		SourceBackedCredentials findCredentials = new SourceBackedCredentials(functionTest);

		//When
		Credentials credentials = findCredentials.getConsumerCredentials("key1");

		//Then
		assertThat(credentials).is(credentialsOf("key1", "s1"));
	}

	@Test
	public void returnsInvalidCredentials_whenKeyIsNotFound() throws Exception {
		//Given
		SourceBackedCredentials findCredentials = new SourceBackedCredentials(functionTest);


		//When
		Credentials credentials = findCredentials.getConsumerCredentials("bad-key");

		//Then
		assertThat(credentials).is(credentialsOf("this key does not exist in the supplier", "this key does not exist in the supplier"));
	}

	private Condition<Credentials> credentialsOf(String key, String secret) {
		return new Condition<>(c -> c.developerKey.equals(key) && c.developerSecret.equals(secret), "credentials of %s:%s", key, secret);
	}
}
