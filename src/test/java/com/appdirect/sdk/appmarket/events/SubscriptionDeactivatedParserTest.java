package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.AccountStatus.SUSPENDED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;

import java.util.HashMap;

import org.junit.Test;

public class SubscriptionDeactivatedParserTest {
	private SubscriptionDeactivatedParser subscriptionDeactivatedParser = new SubscriptionDeactivatedParser();

	@Test
	public void testParse_whenEventInfoContainsASubscriptionDeactivatedMessage_thenParseAsAppropriateDeveloperEventType() throws Exception {
		//Given
		String testConsumerKey = "testConsumerKey";
		String testAccountIdentifier = "testAccountIdentifier";
		AccountStatus testAccountStatus = SUSPENDED;
		EventInfo testEventInfo = createSubscriptionDeactivatedEvent(testAccountIdentifier, testAccountStatus);

		HashMap<String, String[]> testQueryParams = new HashMap<>();
		testQueryParams.put("hello", array("world"));

		//When
		SubscriptionDeactivated parsedEvent = subscriptionDeactivatedParser.parse(testConsumerKey, testEventInfo, testQueryParams);

		//Then
		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo(testAccountIdentifier);
		assertThat(parsedEvent.getAccountInfo().getStatus()).isEqualTo(testAccountStatus);
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo(testConsumerKey);
		assertThat(parsedEvent.getQueryParameters()).isEqualTo(testQueryParams);
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
