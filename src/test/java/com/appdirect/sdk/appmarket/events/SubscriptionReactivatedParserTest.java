package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SubscriptionReactivatedParserTest {
	private SubscriptionReactivatedParser parser = new SubscriptionReactivatedParser();

	@Test
	public void parse_extractsTheConsumerKey() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse("the-key", someEvent(), someQueryParams());

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_extractsTheAccountInfo() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse("the-key", someEventFor("big-account"), someQueryParams());

		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo("big-account");
	}

	@Test
	public void parse_extractsTheQueryParams() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse("the-key", someEvent(), someQueryParams("hello", "the"));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("hello", array("the")));
	}

	private EventInfo someEvent() {
		return someEventFor("some-account");
	}

	private EventInfo someEventFor(String accountId) {
		return EventInfo.builder().payload(EventPayload.builder().account(AccountInfo.builder().accountIdentifier(accountId).build()).build()).build();
	}

	private Map<String, String[]> someQueryParams() {
		return someQueryParams("param1", "value1");
	}

	private Map<String, String[]> someQueryParams(String key, String... values) {
		Map<String, String[]> queryParams = new HashMap<>();
		queryParams.put(key, values);
		return queryParams;
	}
}
