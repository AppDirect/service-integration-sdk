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

import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static com.appdirect.sdk.appmarket.events.ErrorCode.USER_NOT_FOUND;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.utils.URIBuilder;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.exception.ReportUsageException;
import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import wiremock.com.google.common.io.Resources;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class AppmarketBillingClientITTest {

	@Rule
	public WireMockRule mockHttpServer = new WireMockRule(wireMockConfig().dynamicPort());

	private String mockAppMarketUrl;

	@Autowired
	private AppmarketBillingClient appmarketBillingClient;

	@Before
	public void setUp() throws Exception {
		mockAppMarketUrl = new URIBuilder().setScheme("http").setHost("localhost").setPort(mockHttpServer.port()).build().toString();
	}

	@Test
	public void whenBillingUsage_thenACorrectJsonPayloadShouldBeSentToTheAppMarket_withOauthHeaders() throws Exception {
		//Given
		String testUserKey = "testKey";
		Usage testUsage = initializeUsage();
		final String eventResolutionEntpointUrl = "/api/integration/v1/billing/usage";

		final String expectedCallbackPayload = Resources.toString(Resources.getResource("resolutionCallbacks/request_payload_billing_api.json"), Charset.forName("UTF-8"));

		mockHttpServer
			.givenThat(
				post(
					urlEqualTo(eventResolutionEntpointUrl)
				).willReturn(
					aResponse().withStatus(200)
				)
			);

		//When
		appmarketBillingClient.billUsage(mockAppMarketUrl, testUserKey, testUsage);

		//Then
		mockHttpServer
			.verify(
				postRequestedFor(
					urlEqualTo(eventResolutionEntpointUrl)
				).withRequestBody(
					equalToJson(expectedCallbackPayload)
				).withHeader(
					"Authorization", containing("OAuth oauth_consumer_key=")
				).withHeader(
					"Content-Type", equalTo("application/json")
				)
			);
	}

	@Test
	public void whenBillingUsage_notFoundResponse_thenUserNotFoundExceptionIsThrown() throws Exception {
		//Given
		String testUserKey = "testKey";
		Usage testUsage = initializeUsage();
		final String eventResolutionEntpointUrl = "/api/integration/v1/billing/usage";

		mockHttpServer
			.givenThat(
				post(
					urlEqualTo(eventResolutionEntpointUrl)
				).willReturn(
					aResponse().withStatus(404)
				)
			);

		//Then
		assertThatThrownBy(() -> appmarketBillingClient.billUsage(mockAppMarketUrl, testUserKey, testUsage))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: User not found.")
			.hasFieldOrPropertyWithValue("result.errorCode", USER_NOT_FOUND);
	}

	@Test
	public void whenBillingUsage_BadRequestResponse_thenConfigurationErrorExceptionIsThrown() throws Exception {
		//Given
		String testUserKey = "testKey";
		Usage testUsage = initializeUsage();
		final String eventResolutionEntpointUrl = "/api/integration/v1/billing/usage";

		mockHttpServer
			.givenThat(
				post(
					urlEqualTo(eventResolutionEntpointUrl)
				).willReturn(
					aResponse().withStatus(400)
				)
			);

		//Then
		assertThatThrownBy(() -> appmarketBillingClient.billUsage(mockAppMarketUrl, testUserKey, testUsage))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: Usage missing data.")
			.hasFieldOrPropertyWithValue("result.errorCode", CONFIGURATION_ERROR);
	}

	@Test
	public void whenBillingUsage_InternalServerErrorResponse_thenUnknownErrorExceptionIsThrown() throws Exception {
		//Given
		String testUserKey = "testKey";
		Usage testUsage = initializeUsage();
		final String eventResolutionEntpointUrl = "/api/integration/v1/billing/usage";

		mockHttpServer
			.givenThat(
				post(
					urlEqualTo(eventResolutionEntpointUrl)
				).willReturn(
					aResponse().withStatus(500)
				)
			);

		//Then
		assertThatThrownBy(() -> appmarketBillingClient.billUsage(mockAppMarketUrl, testUserKey, testUsage))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: Server Error")
			.hasFieldOrPropertyWithValue("result.errorCode", UNKNOWN_ERROR);
	}

	private Usage initializeUsage() {
		final AccountInfo accountInfo = AccountInfo.builder()
			.accountIdentifier("account-identifier")
			.parentAccountIdentifier("parent-account-identifier")
			.status(AccountStatus.ACTIVE)
			.build();

		final AddonInstanceInfo addonInstanceInfo = AddonInstanceInfo.builder()
			.id("1")
			.build();

		final UsageItem usageItem = UsageItem.builder()
			.customUnit("custom-unit")
			.description("description")
			.price(BigDecimal.ONE)
			.quantity(BigDecimal.ONE)
			.unit(PricingUnit.UNIT)
			.build();

		List<UsageItem> usageLists = Lists.newArrayList();
		usageLists.add(usageItem);

		return Usage.builder()
			.account(accountInfo)
			.addonInstance(addonInstanceInfo)
			.currency(Currency.getInstance(Locale.CANADA))
			.date(Date.from(Instant.EPOCH))
			.items(usageLists)
			.build();
	}

}
