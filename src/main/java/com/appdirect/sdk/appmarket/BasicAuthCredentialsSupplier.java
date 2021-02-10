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

package com.appdirect.sdk.appmarket;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the developer credentials. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
@FunctionalInterface
public interface BasicAuthCredentialsSupplier {

	/**
	 * Returns the credentials that applies to the given consumer key
	 *
	 * @param consumerKey the key used to make a request
	 * @return the full credentials object associated with this key or {@link Credentials#invalidCredentials()}
	 */
	Credentials getConsumerCredentials(String consumerKey);
}
