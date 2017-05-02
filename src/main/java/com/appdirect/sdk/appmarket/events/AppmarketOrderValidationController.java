package com.appdirect.sdk.appmarket.events;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppmarketOrderValidationController {

	private final AppmarketOrderValidationHandler validationHandler;

	@Autowired
	public AppmarketOrderValidationController(AppmarketOrderValidationHandler validationHandler) {
		this.validationHandler = validationHandler;
	}

	@RequestMapping(method = POST, 
			value = "/unsecured/integration/orderValidation",
			consumes = APPLICATION_FORM_URLENCODED_VALUE,
			produces = APPLICATION_JSON_VALUE
	)
	ValidationResponse validateOrderFields(@RequestParam("locale") String locale,
										   @RequestBody MultiValueMap<String, String> body) {
		return validationHandler.validateOrderFields(locale, body.toSingleValueMap());
	}
}
