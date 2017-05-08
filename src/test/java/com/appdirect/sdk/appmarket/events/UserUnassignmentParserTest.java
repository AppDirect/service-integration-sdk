package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserUnassignmentParserTest {
	private UserUnassignmentParser testedEventParser;

	@Before
	public void setUp() throws Exception {
		testedEventParser = new UserUnassignmentParser();
	}

	@Test
	public void testParse_whenParsingAnEventInfoInstanceOfTypeUserUnassign_generateTheAppropriateRichEventObject() throws Exception {
		//Given
		String expectedAccountId = "expectedAccountId";
		String expectedAssignedUserId = "expectedAssignedUserId";
		String expectedConsumerKey = "expectedConsumerKey";
		HashMap<String, String[]> expectedQueryParams = new HashMap<>();
		EventFlag expectedEventFlag = null;
		String expectedEventId = "expectedEventId";
		UserUnassignment expectedRichEvent = new UserUnassignment(expectedAssignedUserId, expectedAccountId, expectedConsumerKey, expectedQueryParams, expectedEventFlag, expectedEventId);

		EventInfo testEventInfo = userUnassignmentEvent(expectedAccountId, expectedAssignedUserId, expectedEventId);
		EventHandlingContext testEventHandlingContext = new EventHandlingContext(expectedConsumerKey, expectedQueryParams);


		//When
		UserUnassignment parsedRichEvent = testedEventParser.parse(testEventInfo, testEventHandlingContext);

		//Then
		assertThat(parsedRichEvent).isEqualTo(expectedRichEvent);
	}

	private EventInfo userUnassignmentEvent(String accountIdentifier, String userIdentifier, String eventId) {
		return EventInfo.builder()
			.type(EventType.USER_ASSIGNMENT)
			.payload(EventPayload.builder()
				.account(AccountInfo.builder()
					.accountIdentifier(accountIdentifier)
					.build())
				.user(UserInfo.builder()
					.uuid(userIdentifier)
					.build())
				.build())
			.id(eventId)
			.build();
	}
}
