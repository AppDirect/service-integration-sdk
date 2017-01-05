package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventFlag.STATELESS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddonSubscriptionCancelEventParserTest {

	AddonSubscriptionCancelEventParser testedParser = new AddonSubscriptionCancelEventParser();

	@Test
	public void parse_whenAnAddonSubscriptionCancelEventWithNoFlagIsParsed_aCorrespondingRichEventIsCreated() throws Exception {
		//Given
		String expectedConsumerKey = "expectedConsumerKey";
		String expectedAddonAccountIdentifier = "expectedAddonAccountIdentifier";
		String expectedParentAccountIdentifier = "expectedParentAccountIdentifier";
		Map<String, String[]> expectedParameters = new HashMap<>();
		EventFlag expectedFlag = null;
		EventInfo testEvent = addonCancelEvent(expectedAddonAccountIdentifier, expectedParentAccountIdentifier, expectedFlag);
		EventHandlingContext testEventContext = EventHandlingContexts.eventContext(expectedConsumerKey, expectedParameters);
		AddonSubscriptionCancel expectedEvent = new AddonSubscriptionCancel(
			expectedAddonAccountIdentifier,
			expectedParentAccountIdentifier,
			expectedConsumerKey,
			expectedParameters,
			expectedFlag
		);

		//When
		AddonSubscriptionCancel parsedEvent = testedParser.parse(testEvent, testEventContext);

		//Then
		assertThat(parsedEvent).isEqualTo(expectedEvent);
	}

	@Test
	public void parse_whenAnAddonSubscriptionCancelEventWithFlagIsParsed_aCorrespondingRichEventIsCreated() throws Exception {
		//Given
		String expectedConsumerKey = "expectedConsumerKey";
		String expectedAddonAccountIdentifier = "expectedAddonAccountIdentifier";
		String expectedParentAccountIdentifier = "expectedParentAccountIdentifier";
		Map<String, String[]> expectedParameters = new HashMap<>();
		EventFlag expectedFlag = STATELESS;
		EventInfo testEvent = addonCancelEvent(expectedAddonAccountIdentifier, expectedParentAccountIdentifier, expectedFlag);
		EventHandlingContext testEventContext = EventHandlingContexts.eventContext(expectedConsumerKey, expectedParameters);
		AddonSubscriptionCancel expectedEvent = new AddonSubscriptionCancel(
			expectedAddonAccountIdentifier,
			expectedParentAccountIdentifier,
			expectedConsumerKey,
			expectedParameters,
			expectedFlag
		);

		//When
		AddonSubscriptionCancel parsedEvent = testedParser.parse(testEvent, testEventContext);

		//Then
		assertThat(parsedEvent).isEqualTo(expectedEvent);
	}
	
	private EventInfo addonCancelEvent(String accountIdentifier, String parentAccountIdentidier, EventFlag eventFlag) {
		return EventInfo.builder()
			.flag(eventFlag)
			.payload(
				EventPayload.builder()
					.account(
						AccountInfo.builder()
							.accountIdentifier(accountIdentifier)
							.parentAccountIdentifier(parentAccountIdentidier)
							.build()
					).build()
			).build();
	}

}
