package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketOrderValidationHandlerImplTest {

	private AppmarketOrderValidationHandlerImpl tested;

	@Before
	public void setUp() throws Exception {
		tested = new AppmarketOrderValidationHandlerImpl();
	}

	@Test
	public void testValidateOrderFields_whenCalled_AnEmptySetIsReturned() throws Exception {
		//Given
		String testLocale = "EN";
		Map<String, String> testOrderFields = new HashMap<>();

		//When
		ValidationResponse actualValidationMessages = tested.validateOrderFields(testLocale, testOrderFields);

		//Then
		assertThat(actualValidationMessages.getResult()).isEmpty();
	}
}
