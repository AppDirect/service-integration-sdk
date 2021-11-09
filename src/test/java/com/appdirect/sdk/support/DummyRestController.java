package com.appdirect.sdk.support;

import static com.appdirect.sdk.web.oauth.RequestIdFilter.MDC_REQUEST_ID_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyRestController {

	@RequestMapping(method = GET, value = "/api/v1/dummy", produces = APPLICATION_JSON_VALUE)
	public String returnMDC() {
		return String.valueOf(MDC.get(MDC_REQUEST_ID_KEY));
	}
}
