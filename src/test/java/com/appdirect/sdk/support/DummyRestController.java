package com.appdirect.sdk.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyRestController {
	public void doNothing (HttpServletRequest request) {
		//Do Nothing
	}

	public void doNothing () {
		//Do Nothing
	}
}
