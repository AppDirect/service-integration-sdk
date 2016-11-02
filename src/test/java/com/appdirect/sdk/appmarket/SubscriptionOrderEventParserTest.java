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
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;
import com.appdirect.sdk.appmarket.api.UserInfo;

public class SubscriptionOrderEventParserTest {
	private SubscriptionOrderEventParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new SubscriptionOrderEventParser();
	}

	@Test
	public void parse_setsTheCompany_fromThePayload() throws Exception {
		EventInfo rawEventWithCompany = eventWithCompany().build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithCompany);

		assertThat(parsedEvent.getCompany().getName()).isEqualTo("Big Boxes");
	}

	@Test
	public void parse_setsTheConfiguration_fromThePayload() throws Exception {
		EventInfo rawEventWithConfig = eventWithConfigAndCompany(config("one", "apple", "two", "apples")).build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithConfig);

		assertThat(parsedEvent.getConfiguration())
				.contains(entry("one", "apple"), entry("two", "apples"));
	}

	@Test
	public void parse_setsTheDevelopmentFlag() throws Exception {
		EventInfo rawDevEvent = eventWithCompany().flag(DEVELOPMENT).build();

		SubscriptionOrder parsedEvent = parser.parse(rawDevEvent);

		assertThat(parsedEvent.getFlag()).contains(DEVELOPMENT);
	}

	@Test
	public void parse_setsThePurchaser_fromTheCreator() throws Exception {
		EventInfo rawEventWithCreator = eventWithCompany().creator(UserInfo.builder().firstName("Joe").lastName("Blo").build()).build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithCreator);

		assertThat(parsedEvent.getPurchaser().getFirstName()).isEqualTo("Joe");
		assertThat(parsedEvent.getPurchaser().getLastName()).isEqualTo("Blo");
	}

	@Test
	public void parse_setsTheOrderInfo_fromThePayload() throws Exception {
		// TODO!
	}

	private EventInfoBuilder eventWithConfigAndCompany(Map<String, String> config) {
		return EventInfo.builder().payload(EventPayload.builder().company(aCompany("Big Boxes")).configuration(config).build());
	}

	private EventInfoBuilder eventWithCompany() {
		return EventInfo.builder().payload(EventPayload.builder().company(aCompany("Big Boxes")).build());
	}

	private CompanyInfo aCompany(String name) {
		return CompanyInfo.builder().name(name).build();
	}

	private Map<String, String> config(String... keyValues) {
		Map<String, String> config = new HashMap<>();
		for (int i = 0; i < keyValues.length; i++) {
			config.put(keyValues[i], keyValues[++i]);
		}
		return config;
	}
}
