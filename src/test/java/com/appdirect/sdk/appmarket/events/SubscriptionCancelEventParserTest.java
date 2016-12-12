package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionCancelEventParserTest {

	private SubscriptionCancelEventParser testedParser = new SubscriptionCancelEventParser();

	@Test
	public void testParse_whenParsingEventInfo_thenTheAccountId_andRequestParams_ShouldBeExtracted() throws Exception {
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
		SubscriptionCancel parsedEvent = testedParser.parse("the-key", testEventInfo, someParam("param1", "value22", "value44"));

		//Then
		assertThat(parsedEvent.getAccountIdentifier())
				.isEqualTo(testEventInfo.getPayload().getAccount().getAccountIdentifier());
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("param1", array("value22", "value44")));
	}

	private Map<String, String[]> someParam(String key, String... values) {
		HashMap<String, String[]> params = new HashMap<>();
		params.put(key, values);
		return params;
	}

}
