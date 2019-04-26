package com.appdirect.sdk.credentials;

import com.appdirect.sdk.appmarket.Credentials;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceBackedCredentialsTest {

	private SourceBackedCredentials findCredentials;
	private Function<String, Credentials> functionTest = (String k) ->  {
		if (k.equalsIgnoreCase("bad-key")) {
			return Credentials.invalidCredentials();
		}
		return new Credentials(k,"s1");
	};

	@Before
	public void setUp() {
		findCredentials = new SourceBackedCredentials(functionTest);
	}

	@Test
	public void returnsGoodCredentials_whenKeyIsFound() throws Exception {
		//Given
		Credentials expected = Credentials.builder().developerKey("key1").developerSecret("s1").build();

		//When
		Credentials credentials = findCredentials.getConsumerCredentials("key1");

		//Then
		assertThat(credentials).isEqualTo(expected);
	}

	@Test
	public void returnsInvalidCredentials_whenKeyIsNotFound() throws Exception {
		//Given
		Credentials expected = Credentials.invalidCredentials();

		//When
		Credentials credentials = findCredentials.getConsumerCredentials("bad-key");

		//Then
		assertThat(credentials).isEqualTo(expected);
	}
}
