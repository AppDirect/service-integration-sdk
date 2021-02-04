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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@Slf4j
@RestController
class AppmarketEventController {

	private final AppmarketEventService appmarketEventService;
	private final OAuthKeyExtractor keyExtractor;

	AppmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor keyExtractor) {
		this.appmarketEventService = appmarketEventService;
		this.keyExtractor = keyExtractor;
	}

	/**
	 * Defines the connector endpoint to which AppMarket integration events should be sent.
	 * @param request the http request
	 * @param eventUrl the url from which the payload of the incoming event can be retrieved.
	 * @return the HTTP response to return to the AppMarket.
	 */
	@RequestMapping(method = GET, value = "/api/v1/integration/processEvent", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResult> processEvent(HttpServletRequest request, @RequestParam("eventUrl") String eventUrl) {

		String keyUsedToSignRequest = keyExtractor.extractFrom(request);
		log.info("eventUrl={} signed with consumerKey={}", eventUrl, keyUsedToSignRequest);

		APIResult result = appmarketEventService.processEvent(eventUrl, eventExecutionContext(request, keyUsedToSignRequest));

		log.info("apiResult={}", result);
		return new ResponseEntity<>(result, httpStatusOf(result));
	}


	@RequestMapping(method = GET, value = "/api/v2/integration/processEvent", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResult> processEvent(HttpServletRequest request, @RequestParam("eventUrl") String eventUrl, @RequestParam("applicationUuid") String applicationUuid) {
		log.info(" Received Event with applicationUuid ={} and eventUrl={}", applicationUuid, eventUrl);

		APIResult result = appmarketEventService.processEvent(eventUrl, eventExecutionContext(request, applicationUuid),applicationUuid);

		log.info("apiResult={} for applicationUuid={}", result, applicationUuid);
		return new ResponseEntity<>(result, httpStatusOf(result));
	}

	private EventHandlingContext eventExecutionContext(HttpServletRequest request, String keyUsedToSignRequest) {
		HashMap<String, String[]> queryParamsNotTiedToRequestReference = new HashMap<>(request.getParameterMap());
		return new EventHandlingContext(keyUsedToSignRequest, queryParamsNotTiedToRequestReference);
	}

	private HttpStatus httpStatusOf(APIResult result) {
		return HttpStatus.valueOf(result.getStatusCodeReturnedToAppmarket());
	}
}
