package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.AccountStatus.INITIALIZED;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventInfoJsonSerializationTest {
	private ObjectMapper jsonMapper = getTheSameJsonMapperThatSpringUses();

	@Test
	public void canDeserializeOrderWithInitializedAccount() throws Exception {
		EventInfo eventInfo = jsonMapper.readValue(resourceAsString("events/subscription-order-addon.json"), EventInfo.class);

		assertThat(eventInfo.getType()).isEqualTo(SUBSCRIPTION_ORDER);
		assertThat(eventInfo.getFlag()).isNull();
		assertThat(eventInfo.getPayload().getAccount()).isEqualTo(anInitializedAccount(null, "the-account-id-of-the-main-app"));
	}

	private AccountInfo anInitializedAccount(String accountIdentifier, String parentAccountIdentifier) {
		return AccountInfo.builder().accountIdentifier(accountIdentifier).parentAccountIdentifier(parentAccountIdentifier).status(INITIALIZED).build();
	}

	private ObjectMapper getTheSameJsonMapperThatSpringUses() {
		return Jackson2ObjectMapperBuilder.json().build();
	}
}
