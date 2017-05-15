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

package com.appdirect.sdk.appmarket;

import lombok.Value;

/**
 * Data object representing the (developerKey, developerSecret) pair shared by any connector using the SDK and the
 * AppMarket. This information is used for signing communications between the two systems
 */
@Value
public class Credentials {
	public final String developerKey;
	public final String developerSecret;

	public static Credentials invalidCredentials() {
		return new Credentials("this key does not exist in the supplier", "this key does not exist in the supplier");
	}
}
