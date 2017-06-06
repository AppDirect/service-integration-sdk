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

package com.appdirect.sdk.feature.sample_connector.minimal;

import static com.appdirect.sdk.appmarket.events.APIResult.success;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.appdirect.sdk.appmarket.events.SubscriptionOrder;
import com.appdirect.sdk.support.DummyRestController;

/**
 * Sample connector that only supports the mandatory events, not the
 * optional ones.
 */
@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return someKey -> new Credentials(someKey, "isv-secret");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return event -> success("Mandatory SUB_ORDER has been processed");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return event -> success("Mandatory SUB_CANCEL has been processed");
	}

	@Bean
	public DummyRestController dummyRestController() {
		return new DummyRestController();
	}
}
