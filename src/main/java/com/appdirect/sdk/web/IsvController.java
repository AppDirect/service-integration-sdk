package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.isv.api.model.vo.APIResult;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class IsvController {
	@Autowired
	private IsvEventService isvEventService;

	@RequestMapping(method = RequestMethod.GET, value = "/integration/processEvent")
	public ResponseEntity<APIResult> processEvent(
			@RequestParam("eventUrl") String eventUrl) {
		log.debug("eventUrl = {}", eventUrl);
		APIResult result = isvEventService.processEvent(eventUrl);
		log.debug("Returning result: {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
