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

package com.appdirect.sdk.support;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class QueryParameters {

	public static Map<String, String[]> oneQueryParam() {
		return oneQueryParam("param1", "value1");
	}

	public static Map<String, String[]> oneQueryParam(String key, String... values) {
		return queryParams(entryFor(key, values));
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static Map<String, String[]> queryParams(Map.Entry<String, String[]>... keyValuesPair) {
		return stream(keyValuesPair).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static SimpleEntry<String, String[]> entryFor(String key, String... values) {
		return new SimpleEntry<>(key, values);
	}

}
