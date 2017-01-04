package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import org.junit.Before;
import org.junit.Test;

public class AddonSubscriptionOrderEventParserTest {
	private AddonSubscriptionOrderEventParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new AddonSubscriptionOrderEventParser();
	}

	@Test
	public void parse_setsTheCompanyInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithCompanyInfo = eventWithCompanyInfo("Big Boxes").build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawEventWithCompanyInfo, defaultEventContext());

		assertThat(parsedEvent.getCompanyInfo().getName()).isEqualTo("Big Boxes");
	}

	@Test
	public void parse_setsTheDevelopmentFlag() throws Exception {
		EventInfo rawDevEvent = eventWithCompanyInfo("Big Boxes").flag(DEVELOPMENT).build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawDevEvent, defaultEventContext());

		assertThat(parsedEvent.isDevelopment()).isTrue();
	}

	@Test
	public void parse_setsThePurchaserInfo_fromTheCreator() throws Exception {
		EventInfo rawEventWithCreator = eventWithCompanyInfo("Big Boxes").creator(UserInfo.builder().firstName("Joe").lastName("Blo").build()).build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawEventWithCreator, defaultEventContext());

		assertThat(parsedEvent.getPurchaserInfo().getFirstName()).isEqualTo("Joe");
		assertThat(parsedEvent.getPurchaserInfo().getLastName()).isEqualTo("Blo");
	}

	@Test
	public void parse_setsTheOrderInfo_fromThePayload() throws Exception {
		EventInfo rawEventWithOrderInfo = eventWithOrderInfo("COOLEST_EDITION").build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawEventWithOrderInfo, defaultEventContext());

		assertThat(parsedEvent.getOrderInfo().getEditionCode()).isEqualTo("COOLEST_EDITION");
	}

	@Test
	public void parse_setsTheConsumerKey_fromTheContext() throws Exception {
		EventInfo rawEvent = eventWithCompanyInfo("dom").build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawEvent, eventContext("the-key"));

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_setsThePartnerName() throws Exception {
		EventInfo rawEvent = someEvent().marketplace(new MarketInfo("Huge Partner", "some-url")).build();

		AddonSubscriptionOrder parsedEvent = parser.parse(rawEvent, defaultEventContext());

		assertThat(parsedEvent.getPartner()).isEqualTo("Huge Partner");
	}

	@Test
	public void parse_setsTheParentAccountIdentifier_fromThePayload() throws Exception {
		AddonSubscriptionOrder parsedEvent = parser.parse(eventWithParentAccount("the-parent-account-id").build(), defaultEventContext());

		assertThat(parsedEvent.getParentAccountIdentifier()).contains("the-parent-account-id");
	}

	@Test
	public void parse_setsTheQueryParameters() throws Exception {
		AddonSubscriptionOrder parsedEvent = parser.parse(someEvent().build(), eventContext("key", oneQueryParam("a-key", "one value")));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("a-key", array("one value")));
	}

	private EventInfo.EventInfoBuilder eventWithOrderInfo(String editionCode) {
		return someEvent().payload(EventPayload.builder().order(OrderInfo.builder().editionCode(editionCode).build()).account(someAccount("p-id")).build());
	}

	private EventInfo.EventInfoBuilder eventWithCompanyInfo(String companyName) {
		return someEvent().payload(EventPayload.builder().company(CompanyInfo.builder().name(companyName).build()).account(someAccount("p-id")).build());
	}

	private EventInfo.EventInfoBuilder eventWithParentAccount(String parentAccountId) {
		return someEvent().payload(EventPayload.builder().account(someAccount(parentAccountId)).build());
	}

	private EventInfo.EventInfoBuilder someEvent() {
		return EventInfo.builder().marketplace(new MarketInfo("some-partner", "some-url")).payload(EventPayload.builder().account(someAccount("p-id")).build());
	}

	private AccountInfo someAccount(String parentAccountId) {
		return AccountInfo.builder().parentAccountIdentifier(parentAccountId).build();
	}
}
