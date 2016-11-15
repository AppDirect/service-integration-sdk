package com.appdirect.sdk.appmarket;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.appmarket.api.AccountInfo;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventPayload;
import com.appdirect.sdk.appmarket.api.OrderInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionChange;
import com.appdirect.sdk.appmarket.api.UserInfo;

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
		SubscriptionChange parsedEvent = testedParser.parse("the-magic-key", testEventInfo);

		//Then
		assertThat(parsedEvent.getOwner()).isEqualTo(expectedCreatorDetails);
		assertThat(parsedEvent.getConsumerKeyUsedByRequest()).isEqualTo("the-magic-key");
	}

}
