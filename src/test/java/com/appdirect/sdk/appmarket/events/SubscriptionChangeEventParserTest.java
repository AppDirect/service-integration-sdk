package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

public class SubscriptionChangeEventParserTest {

	private SubscriptionChangeEventParser testedParser = new SubscriptionChangeEventParser();

	@Test
	public void parse() throws Exception {
		//Given
		UserInfo expectedCreatorDetails = UserInfo.builder().build();
		AccountInfo expectedAccountInfo = AccountInfo.builder().build();
		OrderInfo expectedOrderInfo = OrderInfo.builder().build();
		EventInfo testEventInfo = EventInfo.builder()
				.creator(expectedCreatorDetails)
				.payload(EventPayload.builder()
						.account(expectedAccountInfo)
						.order(expectedOrderInfo)
						.build())
				.build();

		//When
		SubscriptionChange parsedEvent = testedParser.parse("the-magic-key", testEventInfo, new HashMap<>());

		//Then
		assertThat(parsedEvent.getConsumerKeyUsedByRequest()).isEqualTo("the-magic-key");
		assertThat(parsedEvent.getOwner()).isEqualTo(expectedCreatorDetails);
		assertThat(parsedEvent.getOrder()).isEqualTo(expectedOrderInfo);
		assertThat(parsedEvent.getAccount()).isEqualTo(expectedAccountInfo);
	}

}
