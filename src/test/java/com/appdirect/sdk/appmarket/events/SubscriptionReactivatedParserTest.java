package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventExecutionContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventExecutionContexts.eventContext;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

public class SubscriptionReactivatedParserTest {
	private SubscriptionReactivatedParser parser = new SubscriptionReactivatedParser();

	@Test
	public void parse_extractsTheConsumerKey() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEvent(), eventContext("the-key"));

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_extractsTheAccountInfo() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEventFor("big-account"), defaultEventContext());

		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo("big-account");
	}

	@Test
	public void parse_extractsTheQueryParams() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEvent(), eventContext("key", oneQueryParam("hello", "the")));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("hello", array("the")));
	}

	private EventInfo someEvent() {
		return someEventFor("some-account");
	}

	private EventInfo someEventFor(String accountId) {
		return EventInfo.builder().payload(EventPayload.builder().account(AccountInfo.builder().accountIdentifier(accountId).build()).build()).build();
	}
}
