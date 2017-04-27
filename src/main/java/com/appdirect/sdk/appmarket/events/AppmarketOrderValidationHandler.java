package com.appdirect.sdk.appmarket.events;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AppmarketOrderValidationHandler {

	@RequestMapping(method = GET, value = "/unsecured/integration/orderValidation", produces = APPLICATION_JSON_VALUE)
	Set<OrderValidationStatus> validateOrderFields(@RequestParam("locale") String locale,
												   @RequestBody Map<String, String> orderFields);
}
