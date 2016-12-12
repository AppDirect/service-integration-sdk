package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

public class SubscriptionClosedParserTest {
	private SubscriptionClosedParser parser = new SubscriptionClosedParser();

	@Test
	public void parse_extractsTheConsumerKey() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse("the-key", someEvent(), oneQueryParam());

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_extractsTheAccountInfo() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse("the-key", someEventFor("big-account"), oneQueryParam());

		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo("big-account");
	}

	@Test
	public void parse_extractsTheQueryParams() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse("the-key", someEvent(), oneQueryParam("some", "params", "1"));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("some", array("params", "1")));
	}

	private EventInfo someEvent() {
		return someEventFor("some-account");
	}

	private EventInfo someEventFor(String accountId) {
		return EventInfo.builder().payload(EventPayload.builder().account(AccountInfo.builder().accountIdentifier(accountId).build()).build()).build();
	}
}
