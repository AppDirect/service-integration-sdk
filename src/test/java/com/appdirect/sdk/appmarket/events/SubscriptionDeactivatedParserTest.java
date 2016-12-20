package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.AccountStatus.SUSPENDED;
import static com.appdirect.sdk.appmarket.events.EventExecutionContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

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

		Map<String, String[]> testQueryParams = oneQueryParam("hello", "world");

		//When
		SubscriptionDeactivated parsedEvent = subscriptionDeactivatedParser.parse(testEventInfo, eventContext(testConsumerKey, testQueryParams));

		//Then
		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo(testAccountIdentifier);
		assertThat(parsedEvent.getAccountInfo().getStatus()).isEqualTo(testAccountStatus);
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo(testConsumerKey);
		assertThat(parsedEvent.isDevelopment()).isTrue();
		assertThat(parsedEvent.getQueryParameters()).isEqualTo(testQueryParams);
	}

	private EventInfo createSubscriptionDeactivatedEvent(String testAccountIdentifier, AccountStatus testAccountStatus) {
		return EventInfo.builder()
				.flag(DEVELOPMENT)
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
