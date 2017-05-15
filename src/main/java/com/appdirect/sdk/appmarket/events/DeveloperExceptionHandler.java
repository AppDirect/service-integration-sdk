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

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
@ControllerAdvice
public class DeveloperExceptionHandler {
	@ResponseBody
	@ExceptionHandler
	public APIResult handleDeveloperServiceException(DeveloperServiceException e, HttpServletResponse response) {
		APIResult result = e.getResult();
		response.setStatus(result.getStatusCodeReturnedToAppmarket());
		log.debug("Handling DeveloperServiceException. APIResult={}", result);
		return result;
	}
}
