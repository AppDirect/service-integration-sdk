/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.validation;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	@Mock
	HttpServletRequest aRequest;

	private AppmarketOrderValidationController tested;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		tested = new AppmarketOrderValidationController(mockValidationHandler);
	}

	@Test
	public void testValidatie_whenControllerCalled_parametersForwardedToTheHandler() throws Exception {
		//Given
		MultiValueMap testParams = mock(MultiValueMap.class);
		Map<String, String> expectedParamsMap = new HashMap<>();
		when(testParams.toSingleValueMap())
				.thenReturn(expectedParamsMap);
		//When
		tested.validateOrderFields(aRequest, testParams);

		//Then
		verify(mockValidationHandler)
				.validateOrderFields(
						eq(expectedParamsMap)
				);
	}
}
