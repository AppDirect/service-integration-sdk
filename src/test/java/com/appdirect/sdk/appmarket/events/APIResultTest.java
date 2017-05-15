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

import org.junit.Test;

public class APIResultTest {

	@Test
	public void constructorWithError_setsSuccessToFalse() throws Exception {
		APIResult unsuccessfulResult = new APIResult(ErrorCode.CONFIGURATION_ERROR, "some-message");

		assertThat(unsuccessfulResult.isSuccess()).isFalse();
		assertThat(unsuccessfulResult.getErrorCode()).isEqualTo(ErrorCode.CONFIGURATION_ERROR);
		assertThat(unsuccessfulResult.getMessage()).isEqualTo("some-message");
	}

	@Test
	public void userIdentifier_canBeUsed() throws Exception {
		APIResult someResult = APIResult.success("success-message");
		someResult.setUserIdentifier("some-user");

		assertThat(someResult.getUserIdentifier()).isEqualTo("some-user");
	}
}
