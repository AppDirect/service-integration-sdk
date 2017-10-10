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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restriction {
	private String offerId;
	private String editionUuid;
	private OperationType operation;
	private String editionPricingUuid;
	private RestrictionCode restrictionCode;
	private String restrictionMessage;
}
