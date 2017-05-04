package com.appdirect.sdk.appmarket.events;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.MultiValueMap;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketOrderValidationControllerTest {

	@Mock
	private AppmarketOrderValidationHandler mockValidationHandler;

	private AppmarketOrderValidationController tested;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		tested = new AppmarketOrderValidationController(mockValidationHandler);
	}

	@Test
	public void testValidatie_whenControllerCalled_parametersForwardedToTheHandler() throws Exception {
		//Given
		String testLocale = "EN";
		MultiValueMap testParams = mock(MultiValueMap.class);
		Map<String, String> expectedParamsMap = new HashMap<>();
		when(testParams.toSingleValueMap())
				.thenReturn(expectedParamsMap);
		//When
		tested.validateOrderFields(testLocale, testParams);

		//Then
		verify(mockValidationHandler)
				.validateOrderFields(
						eq(testLocale),
						eq(expectedParamsMap)
				);
	}
}
