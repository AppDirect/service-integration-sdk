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

package com.appdirect.sdk.appmarket.restrictions;

public enum RestrictionCode {
	TERMS_OF_SERVICE_NOT_ACCEPTED,
	UPGRADE_OF_PLAN_NOT_ALLOWED,
	DOWNGRADE_OF_PLAN_NOT_ALLOWED,
	CHANGE_OF_PLAN_NOT_ALLOWED,
	SEAT_CHANGE_NOT_ALLOWED,
	INSUFFICIENT_PERMISSIONS,
	INVALID_EDITION,
	PREREQUISITE_CONDITION_NOT_SATISFIED,
	MAX_ENTITLEMENTS_REACHED,
	DOMAIN_NOT_VERIFIED,
	BASE_PRODUCT_MISSING,
	ACTION_NOT_SUPPORTED,
	SUSPENDED_BY_RESELLER,
}
