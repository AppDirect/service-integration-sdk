package com.appdirect.sdk.credentials;

import com.appdirect.sdk.appmarket.Credentials;

import java.util.function.Function;

import org.assertj.core.api.Condition;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceBackedCredentialsTest {


	private Function<String, Credentials> functionTest = (String k) ->  {
		if (k.equalsIgnoreCase("bad-key")) {
			return Credentials.invalidCredentials();
		}
		return new Credentials(k,"s1");
	};

	@Test
	public void returnsGoodCredentials_whenKeyIsFound() throws Exception {
		//Given
		SourceBackedCredentials findCredentials = new SourceBackedCredentials(functionTest);

		//When
		Credentials credentials = findCredentials.getConsumerCredentials("key1");

		//Then
		assertThat(credentials).is(credentialsOf("key1", "s1"));
	}

	@Test
	public void returnsInvalidCredentials_whenKeyIsNotFound() throws Exception {
		//Given
		SourceBackedCredentials findCredentials = new SourceBackedCredentials(functionTest);


		//When
		Credentials credentials = findCredentials.getConsumerCredentials("bad-key");

		//Then
		assertThat(credentials).is(credentialsOf("this key does not exist in the supplier", "this key does not exist in the supplier"));
	}

	private Condition<Credentials> credentialsOf(String key, String secret) {
		return new Condition<>(c -> c.developerKey.equals(key) && c.developerSecret.equals(secret), "credentials of %s:%s", key, secret);
	}
}
