package com.appdirect.sdk.appmarket;


import static com.appdirect.sdk.appmarket.api.EventFlag.DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.appdirect.sdk.appmarket.api.CompanyInfo;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventInfo.EventInfoBuilder;
import com.appdirect.sdk.appmarket.api.EventPayload;
import com.appdirect.sdk.appmarket.api.OrderInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;
import com.appdirect.sdk.appmarket.api.UserInfo;

public class SubscriptionOrderEventParserTest {
	private SubscriptionOrderEventParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new SubscriptionOrderEventParser();
	}

	@Test
	public void parse_setsTheCompanyInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithCompanyInfo = eventWithCompanyInfo("Big Boxes").build();

		SubscriptionOrder parsedEvent = parser.parse("some-key", rawEventWithCompanyInfo);

		assertThat(parsedEvent.getCompanyInfo().getName()).isEqualTo("Big Boxes");
	}

	@Test
	public void parse_setsTheConfiguration_fromThePayload() throws Exception {
		EventInfo rawEventWithConfig = eventWithConfig(config("one", "apple", "two", "apples")).build();

		SubscriptionOrder parsedEvent = parser.parse("some-key", rawEventWithConfig);

		assertThat(parsedEvent.getConfiguration()).contains(entry("one", "apple"), entry("two", "apples"));
	}

	@Test
	public void parse_setsTheDevelopmentFlag() throws Exception {
		EventInfo rawDevEvent = eventWithCompanyInfo("Big Boxes").flag(DEVELOPMENT).build();

		SubscriptionOrder parsedEvent = parser.parse("some-key", rawDevEvent);

		assertThat(parsedEvent.getFlag()).contains(DEVELOPMENT);
	}

	@Test
	public void parse_setsThePurchaserInfo_fromTheCreator() throws Exception {
		EventInfo rawEventWithCreator = eventWithCompanyInfo("Big Boxes").creator(UserInfo.builder().firstName("Joe").lastName("Blo").build()).build();

		SubscriptionOrder parsedEvent = parser.parse("some-key", rawEventWithCreator);

		assertThat(parsedEvent.getPurchaserInfo().getFirstName()).isEqualTo("Joe");
		assertThat(parsedEvent.getPurchaserInfo().getLastName()).isEqualTo("Blo");
	}

	@Test
	public void parse_setsTheOrderInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithOrderInfo = eventWithOrderInfo("COOLEST_EDITION").build();

		SubscriptionOrder parsedEvent = parser.parse("some-key", rawEventWithOrderInfo);

		assertThat(parsedEvent.getOrderInfo().getEditionCode()).isEqualTo("COOLEST_EDITION");
	}

	@Test
	public void parse_setsTheConsumerKey_fromTheParam() throws Exception {
		EventInfo rawEvent = eventWithCompanyInfo("dom").build();

		SubscriptionOrder parsedEvent = parser.parse("the-key", rawEvent);

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	private EventInfoBuilder eventWithOrderInfo(String editionCode) {
		return EventInfo.builder().payload(EventPayload.builder().order(OrderInfo.builder().editionCode(editionCode).build()).build());
	}

	private EventInfoBuilder eventWithConfig(Map<String, String> config) {
		return EventInfo.builder().payload(EventPayload.builder().configuration(config).build());
	}

	private EventInfoBuilder eventWithCompanyInfo(String companyName) {
		return EventInfo.builder().payload(EventPayload.builder().company(CompanyInfo.builder().name(companyName).build()).build());
	}

	private Map<String, String> config(String... keyValues) {
		Map<String, String> config = new HashMap<>();
		for (int i = 0; i < keyValues.length; i++) {
			config.put(keyValues[i], keyValues[++i]);
		}
		return config;
	}
}
