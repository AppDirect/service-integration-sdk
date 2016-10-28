package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.appmarket.api.APIResult;

@Slf4j
@RestController
public class HealthController {

	@RequestMapping(method = RequestMethod.GET, value = "/health")
	public ResponseEntity<APIResult> healthPing() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
