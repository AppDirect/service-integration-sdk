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

package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.DeveloperExceptionHandler;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class DeveloperExceptionHandlerTest {

	@Test
	public void handleIsvServiceException_returnsTheApiResultInIt_setsTheStatusTo200() throws Exception {
		HttpServletResponse response = new MockHttpServletResponse();
		DeveloperServiceException someException = new DeveloperServiceException(UNAUTHORIZED, "no no no");

		APIResult result = new DeveloperExceptionHandler().handleDeveloperServiceException(someException, response);

		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(result).hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED)
				.hasFieldOrPropertyWithValue("message", "no no no");
	}
}
