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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.appmarket.restrictions.context.RestrictionInfo;

/**
 * Defines the endpoint for enforcing restrictions on their products
 */
@RestController
@RequiredArgsConstructor
public class RestrictionController {
	private final RestrictionHandler restrictionHandler;

	@RequestMapping(method = POST,
			value = {"/api/v1/restrictions", "/api/v2/restrictions", "/api/v2/basic/restrictions"},
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public Callable<RestrictionResponse> getRestrictions(@RequestBody RestrictionInfo restrictionInfo, @RequestParam("partner") String partner) {
		return () -> restrictionHandler.getRestrictions(restrictionInfo, partner);
	}
}
