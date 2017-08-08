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
import com.appdirect.sdk.web.oauth.ReportUsageRestTemplateFactoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppmarketBillingClientTest {
	@Mock
	private RestTemplate restTemplate;
	private AppmarketBillingClient appmarketBillingClient;
	@Mock
	private ReportUsageRestTemplateFactoryImpl restTemplateFactory;
	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private AccountInfo accountInfo;
	private AddonInstanceInfo addonInstanceInfo;
	private UsageItem usageItem;
	private ObjectMapper jsonMapper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		jsonMapper = Jackson2ObjectMapperBuilder.json().build();

		appmarketBillingClient = spy(new AppmarketBillingClient(restTemplateFactory, credentialsSupplier, jsonMapper));

		when(credentialsSupplier.getConsumerCredentials("some-key")).thenReturn(new Credentials("some-key", "some-secret"));

		accountInfo = AccountInfo.builder().accountIdentifier("account-identifier")
			.parentAccountIdentifier("parent-account-identifier")
			.status(AccountStatus.ACTIVE)
			.build();

		addonInstanceInfo = AddonInstanceInfo.builder().id("1").build();

		usageItem = UsageItem.builder().customUnit("custom-unit")
			.description("description")
			.price(BigDecimal.ONE)
			.quantity(BigDecimal.ONE)
			.unit(PricingUnit.UNIT)
			.build();
	}

	@Test
	public void billUsage_callsPost_onTheRightUrl() throws Exception {

		when(restTemplateFactory.getOAuthRestTemplate("some-key", "some-secret")).thenReturn(restTemplate);

		List<UsageItem> usageLists = Lists.newArrayList();
		usageLists.add(usageItem);

		UsageBean usageBean = UsageBean.builder().account(accountInfo)
			.addonInstance(addonInstanceInfo)
			.currency(Currency.getInstance(Locale.CANADA))
			.date(Date.from(Instant.EPOCH))
			.items(usageLists)
			.build();

		final ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);

		UsageBean resourceUsageBean = jsonMapper.readValue(resourceAsString("events/usage-report-bill-request.json"), UsageBean.class);

		appmarketBillingClient.billUsage("http://base.com", "some-key", usageBean);

		verify(restTemplate).postForObject(eq("http://base.com/api/integration/v1/billing/usage"), httpEntityArgumentCaptor.capture(), eq(APIResult.class));

		verify(restTemplateFactory).getOAuthRestTemplate("some-key", "some-secret");

		final HttpEntity actualEntity = httpEntityArgumentCaptor.getValue();

		assertThat(actualEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

		UsageBean actualUsageBean = jsonMapper.readValue(actualEntity.getBody().toString(), UsageBean.class);

		assertThat(actualUsageBean.getAccount()).isEqualTo(resourceUsageBean.getAccount());
		assertThat(actualUsageBean.getAddonInstance().getId()).isEqualTo(resourceUsageBean.getAddonInstance().getId());
		assertThat(actualUsageBean.getCurrency()).isEqualTo(resourceUsageBean.getCurrency());
		assertThat(actualUsageBean.getDate()).isEqualTo(resourceUsageBean.getDate());
		assertThat(actualUsageBean.getItems().get(0).getCustomUnit()).isEqualTo(resourceUsageBean.getItems().get(0).getCustomUnit());
	}
}
