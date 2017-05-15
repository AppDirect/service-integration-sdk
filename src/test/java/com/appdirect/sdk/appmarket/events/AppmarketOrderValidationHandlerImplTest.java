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

package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.validation.AppmarketOrderValidationHandlerImpl;
import com.appdirect.sdk.appmarket.validation.ValidationResponse;

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
		Map<String, String> testOrderFields = new HashMap<>();

		//When
		ValidationResponse actualValidationMessages = tested.validateOrderFields(testOrderFields);

		//Then
		assertThat(actualValidationMessages.getResult()).isEmpty();
	}
}
