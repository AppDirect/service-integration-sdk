package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.appmarket.api.APIResult;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppmarketEventController {

	private final AppmarketEventService appmarketEventService;

	public AppmarketEventController(AppmarketEventService appmarketEventService) {
		this.appmarketEventService = appmarketEventService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/integration/processEvent")
	public ResponseEntity<APIResult> processEvent(@RequestParam("eventUrl") String eventUrl) {
		log.debug("eventUrl={}", eventUrl);
		APIResult result = appmarketEventService.processEvent(eventUrl);
		log.debug("Returning result: {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
