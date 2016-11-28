package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionCancelEventParserTest {

	private SubscriptionCancelEventParser testedParser = new SubscriptionCancelEventParser();

	@Test
	public void testParse_whenParsingEventInfo_thenTheAccountIdShouldBeExtracted() throws Exception {
		//Given
		String testAccountIdentifier = "testAccountIdentifier";
		EventInfo testEventInfo = EventInfo.builder()
			.payload(
				EventPayload.builder()
					.account(
						AccountInfo.builder()
							.accountIdentifier(testAccountIdentifier)
						.build()
					)
				.build()
			)
			.build();

		//When
		SubscriptionCancel parsedEvent = testedParser.parse("the-key", testEventInfo);

		//Then
		assertThat(parsedEvent.getAccountIdentifier())
			.isEqualTo(testEventInfo.getPayload().getAccount().getAccountIdentifier());
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

}
