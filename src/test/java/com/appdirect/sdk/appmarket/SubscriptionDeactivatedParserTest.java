package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.AccountStatus.SUSPENDED;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.appmarket.api.AccountInfo;
import com.appdirect.sdk.appmarket.api.AccountStatus;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventPayload;
import com.appdirect.sdk.appmarket.api.SubscriptionDeactivated;

public class SubscriptionDeactivatedParserTest {
	private SubscriptionDeactivatedParser subscriptionDeactivatedParser = new SubscriptionDeactivatedParser();

	@Test
	public void parse() throws Exception {
		//Given
		String testConsumerKey = "testConsumerKey";
		String testAccountIdentifier = "testAccountIdentifier";
		AccountStatus testAccountStatus = SUSPENDED;
		EventInfo testEventInfo = createSubscriptionDeactivatedEvent(testAccountIdentifier, testAccountStatus);

		//When
		SubscriptionDeactivated parsedEvent = subscriptionDeactivatedParser.parse(testConsumerKey, testEventInfo);

		//Then
		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo(testAccountIdentifier);
		assertThat(parsedEvent.getAccountInfo().getStatus()).isEqualTo(testAccountStatus);
	}

	private EventInfo createSubscriptionDeactivatedEvent(String testAccountIdentifier, AccountStatus testAccountStatus) {
		return EventInfo.builder()
				.payload(EventPayload.builder()
					.account(
						AccountInfo.builder()
							.accountIdentifier(testAccountIdentifier)
							.status(testAccountStatus)
						.build())
					.build())
				.build();
	}

}
