package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserAssignmentParserTest {
	private UserAssignmentParser testedEventParser;

	@Before
	public void setUp() throws Exception {
		testedEventParser = new UserAssignmentParser();
	}

	@Test
	public void testParse_whenParsingAnEventInfoInstanceOfTypeUserAssign_generateTheAppropriateRichEventObject() throws Exception {
		//Given
		String expectedAccountId = "expectedAccountId";
		String expectedAssignedUserId = "expectedAssignedUserId";
		UserInfo userInfo = UserInfo.builder().uuid(expectedAssignedUserId).build();
		String expectedConsumerKey = "expectedConsumerKey";
		HashMap<String, String[]> expectedQueryParams = new HashMap<>();
		EventFlag expectedEventFlag = null;
		String expectedEventId = "expectedEventId";
		String expectedBaseUrl = "http://www.example.com";
		UserAssignment expectedRichEvent = new UserAssignment(
				userInfo, 
				expectedAccountId, 
				expectedConsumerKey, 
				expectedQueryParams, 
				expectedEventFlag, 
				expectedEventId,
				expectedBaseUrl
		);

		EventInfo testEventInfo = userAssignmentEvent(expectedAccountId, userInfo, expectedEventId, expectedBaseUrl);
		EventHandlingContext testEventHandlingContext = new EventHandlingContext(expectedConsumerKey, expectedQueryParams);


		//When
		UserAssignment parsedRichEvent = testedEventParser.parse(testEventInfo, testEventHandlingContext);

		//Then
		assertThat(parsedRichEvent).isEqualTo(expectedRichEvent);
	}

	private EventInfo userAssignmentEvent(String accountIdentifier, UserInfo userInfo, String eventToken, String baseUrl) {
		return EventInfo.builder()
			.type(EventType.USER_ASSIGNMENT)
			.marketplace(new MarketInfo("APPDIRECT", baseUrl))
			.payload(EventPayload.builder()
				.account(AccountInfo.builder()
					.accountIdentifier(accountIdentifier)
					.build())
				.user(userInfo)
				.build())
			.id(eventToken)
			.build();
	}
}
