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

package com.appdirect.sdk.utils;

import static com.appdirect.sdk.utils.StringUtils.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.support.UtilityClassesAreWellDefined;

public class StringUtilsTest {
	@Test
	public void stringUtils_isWellDefinedUtilityClass() throws Exception {
		UtilityClassesAreWellDefined.verify(StringUtils.class);
	}

	@Test
	public void isEmpty_nullString_isEmpty() throws Exception {
		assertThat(isEmpty(null)).isTrue();
	}

	@Test
	public void isEmpty_emptyString_isEmpty() throws Exception {
		assertThat(isEmpty("")).isTrue();
	}

	@Test
	public void isEmpty_blankString_isNotEmpty() throws Exception {
		assertThat(isEmpty(" ")).isFalse();
	}
}
