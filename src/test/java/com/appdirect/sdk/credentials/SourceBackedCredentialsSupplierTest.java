package com.appdirect.sdk.credentials;

import com.appdirect.sdk.appmarket.Credentials;

import java.util.function.Function;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceBackedCredentialsSupplierTest {
	private SourceBackedCredentialsSupplier sourceBackedCredentialsSupplier;
	private Function<String, Credentials> functionTest = (String k) ->  {
		if (k.equalsIgnoreCase("bad-key")) {
			return Credentials.invalidCredentials();
		}
		return new Credentials(k,"s1");
	};

	@BeforeMethod
	public void setUp() {
		sourceBackedCredentialsSupplier = new SourceBackedCredentialsSupplier(functionTest);
	}

	@Test
	public void returnsGoodCredentials_whenKeyIsFound() {
		Credentials expected = Credentials.builder().developerKey("key1").developerSecret("s1").build();

		Credentials credentials = sourceBackedCredentialsSupplier.getConsumerCredentials("key1");

		assertThat(credentials).isEqualTo(expected);
	}

	@Test
	public void returnsInvalidCredentials_whenKeyIsNotFound() {
		Credentials expected = Credentials.invalidCredentials();

		Credentials credentials = sourceBackedCredentialsSupplier.getConsumerCredentials("bad-key");

		assertThat(credentials).isEqualTo(expected);
	}
}
