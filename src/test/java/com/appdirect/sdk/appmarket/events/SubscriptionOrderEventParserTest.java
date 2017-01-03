package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.appdirect.sdk.appmarket.events.EventInfo.EventInfoBuilder;

public class SubscriptionOrderEventParserTest {
	private SubscriptionOrderEventParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new SubscriptionOrderEventParser();
	}

	@Test
	public void parse_setsTheCompanyInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithCompanyInfo = eventWithCompanyInfo("Big Boxes").build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithCompanyInfo, defaultEventContext());

		assertThat(parsedEvent.getCompanyInfo().getName()).isEqualTo("Big Boxes");
	}

	@Test
	public void parse_setsTheConfiguration_fromThePayload() throws Exception {
		EventInfo rawEventWithConfig = eventWithConfig(config("one", "apple", "two", "apples")).build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithConfig, defaultEventContext());

		assertThat(parsedEvent.getConfiguration()).contains(entry("one", "apple"), entry("two", "apples"));
	}

	@Test
	public void parse_setsTheDevelopmentFlag() throws Exception {
		EventInfo rawDevEvent = eventWithCompanyInfo("Big Boxes").flag(DEVELOPMENT).build();

		SubscriptionOrder parsedEvent = parser.parse(rawDevEvent, defaultEventContext());

		assertThat(parsedEvent.isDevelopment()).isTrue();
	}

	@Test
	public void parse_setsThePurchaserInfo_fromTheCreator() throws Exception {
		EventInfo rawEventWithCreator = eventWithCompanyInfo("Big Boxes").creator(UserInfo.builder().firstName("Joe").lastName("Blo").build()).build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithCreator, defaultEventContext());

		assertThat(parsedEvent.getPurchaserInfo().getFirstName()).isEqualTo("Joe");
		assertThat(parsedEvent.getPurchaserInfo().getLastName()).isEqualTo("Blo");
	}

	@Test
	public void parse_setsTheOrderInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithOrderInfo = eventWithOrderInfo("COOLEST_EDITION").build();

		SubscriptionOrder parsedEvent = parser.parse(rawEventWithOrderInfo, defaultEventContext());

		assertThat(parsedEvent.getOrderInfo().getEditionCode()).isEqualTo("COOLEST_EDITION");
	}

	@Test
	public void parse_setsTheConsumerKey_fromTheContext() throws Exception {
		EventInfo rawEvent = eventWithCompanyInfo("dom").build();

		SubscriptionOrder parsedEvent = parser.parse(rawEvent, eventContext("the-key"));

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_setsThePartnerName() throws Exception {
		EventInfo rawEvent = someEvent().marketplace(new MarketInfo("Huge Partner", "some-url")).build();

		SubscriptionOrder parsedEvent = parser.parse(rawEvent, defaultEventContext());

		assertThat(parsedEvent.getPartner()).isEqualTo("Huge Partner");
	}

	@Test
	public void parse_setsTheAppUuid() throws Exception {
		SubscriptionOrder parsedEvent = parser.parse(someEvent().applicationUuid("the-app-uuid").build(), defaultEventContext());

		assertThat(parsedEvent.getApplicationUuid()).contains("the-app-uuid");
	}

	@Test
	public void parse_setsTheQueryParameters() throws Exception {
		SubscriptionOrder parsedEvent = parser.parse(someEvent().build(), eventContext("key", oneQueryParam("a-key", "one value")));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("a-key", array("one value")));
	}

	private EventInfoBuilder eventWithOrderInfo(String editionCode) {
		return someEvent().payload(EventPayload.builder().order(OrderInfo.builder().editionCode(editionCode).build()).build());
	}

	private EventInfoBuilder eventWithConfig(Map<String, String> config) {
		return someEvent().payload(EventPayload.builder().configuration(config).build());
	}

	private EventInfoBuilder eventWithCompanyInfo(String companyName) {
		return someEvent().payload(EventPayload.builder().company(CompanyInfo.builder().name(companyName).build()).build());
	}

	private EventInfoBuilder someEvent() {
		return EventInfo.builder().marketplace(new MarketInfo("some-partner", "some-url")).payload(EventPayload.builder().build());
	}

	private Map<String, String> config(String... keyValues) {
		Map<String, String> config = new HashMap<>();
		for (int i = 0; i < keyValues.length; i++) {
			config.put(keyValues[i], keyValues[++i]);
		}
		return config;
	}
}
