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
 *
 */

package com.appdirect.sdk.exception;

import lombok.Getter;

import com.appdirect.sdk.appmarket.usersync.UserSyncApiResult;

public class UserSyncException extends RuntimeException {
	private static final long serialVersionUID = 8765855456255852065L;
	@Getter
	private UserSyncApiResult result;

	public UserSyncException(String errorCode, String message) {
		super(message);
		this.result = new UserSyncApiResult(errorCode, message);
	}

	public UserSyncException(String message, UserSyncApiResult userSyncApiResult) {
		super(message);
		this.result = userSyncApiResult;
	}
}
