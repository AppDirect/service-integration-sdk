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

import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppmarketBillingClientTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private RestTemplateFactory restTemplateFactory;
	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketBillingClient appmarketBillingClient;
	private ObjectMapper jsonMapper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		jsonMapper = Jackson2ObjectMapperBuilder.json().build();

		appmarketBillingClient = spy(new AppmarketBillingClient(restTemplateFactory, credentialsSupplier, jsonMapper));

		when(credentialsSupplier.getConsumerCredentials("some-key")).thenReturn(new Credentials("some-key", "some-secret"));

		when(restTemplateFactory.getOAuthRestTemplate("some-key", "some-secret")).thenReturn(restTemplate);
	}

	@Test
	public void billUsage_callsPost_onTheRightUrl() throws Exception {

		Usage usage = initializeUsage();

		final ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);

		appmarketBillingClient.billUsage("http://base.com", "some-key", usage);

		verify(restTemplate).postForObject(eq("http://base.com/api/integration/v1/billing/usage"), httpEntityArgumentCaptor.capture(), eq(APIResult.class));

		verify(restTemplateFactory).getOAuthRestTemplate("some-key", "some-secret");

		final HttpEntity actualEntity = httpEntityArgumentCaptor.getValue();

		assertThat(actualEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

		Usage resourceUsage = jsonMapper.readValue(resourceAsString("events/usage-report-bill-request.json"), Usage.class);
		Usage actualUsage = jsonMapper.readValue(actualEntity.getBody().toString(), Usage.class);

		assertThat(actualUsage.getAccount()).isEqualTo(resourceUsage.getAccount());
		assertThat(actualUsage.getAddonInstance().getId()).isEqualTo(resourceUsage.getAddonInstance().getId());
		assertThat(actualUsage.getCurrency()).isEqualTo(resourceUsage.getCurrency());
		assertThat(actualUsage.getDate()).isEqualTo(resourceUsage.getDate());
		assertThat(actualUsage.getItems().get(0).getCustomUnit()).isEqualTo(resourceUsage.getItems().get(0).getCustomUnit());
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
