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

package com.appdirect.sdk;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.events.AppmarketCommunicationConfiguration;
import com.appdirect.sdk.appmarket.events.DefaultEventHandlersForOptionalEvents;
import com.appdirect.sdk.appmarket.events.EventHandlingConfiguration;
import com.appdirect.sdk.appmarket.migration.DefaultMigrationHandlers;
import com.appdirect.sdk.appmarket.restrictions.RestrictionConfiguration;
import com.appdirect.sdk.appmarket.usersync.UserSyncConfiguration;
import com.appdirect.sdk.appmarket.validation.DefaultValidationHandlers;
import com.appdirect.sdk.vendorFields.configuration.VendorFieldConfiguration;
import com.appdirect.sdk.vendorFields.configuration.VendorRequiredFieldConfiguration;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.MvcConfiguration;
import com.appdirect.sdk.web.oauth.SecurityConfiguration;

/**
 * A {@link Configuration} instance that a connector using the SDK must import into their Spring Boot
 * {@link org.springframework.context.ApplicationContext} in order to be able to make use of the SDK.
 */
@Configuration
@Import({
		MvcConfiguration.class,
		JacksonConfiguration.class,
		SecurityConfiguration.class,
		DefaultEventHandlersForOptionalEvents.class,
		EventHandlingConfiguration.class,
		AppmarketCommunicationConfiguration.class,
		DefaultMigrationHandlers.class,
		DefaultValidationHandlers.class,
		UserSyncConfiguration.class,
		RestrictionConfiguration.class,
		VendorFieldConfiguration.class,
		VendorRequiredFieldConfiguration.class
})
public class ConnectorSdkConfiguration {
}
