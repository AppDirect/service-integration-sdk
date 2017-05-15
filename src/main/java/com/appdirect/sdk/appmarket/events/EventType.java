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

enum EventType {
	SUBSCRIPTION_ORDER,
	SUBSCRIPTION_CHANGE,
	SUBSCRIPTION_CANCEL,
	SUBSCRIPTION_NOTICE,
	USER_ASSIGNMENT,
	USER_UNASSIGNMENT,
	USER_SYNC,
	USER_LINK,
	ACCOUNT_SYNC,
	ACCOUNT_UNSYNC,
	ADDON_ORDER,
	ADDON_BIND,
	ADDON_UNBIND,
	ADDON_CHANGE,
	ADDON_CANCEL
}
