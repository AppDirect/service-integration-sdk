package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

public class SubscriptionChangeEventParserTest {

	private SubscriptionChangeEventParser testedParser = new SubscriptionChangeEventParser();

	@Test
	public void parses_creatorDetails_orderInfo_accountInfo_consumerKey_andQueryParameters() throws Exception {
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
		SubscriptionChange parsedEvent = testedParser.parse("the-magic-key", testEventInfo, oneQueryParam("param1", "value12"));

		//Then
		assertThat(parsedEvent.getOwner()).isEqualTo(expectedCreatorDetails);
		assertThat(parsedEvent.getOrder()).isEqualTo(expectedOrderInfo);
		assertThat(parsedEvent.getAccount()).isEqualTo(expectedAccountInfo);
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-magic-key");
		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("param1", array("value12")));
	}
}
