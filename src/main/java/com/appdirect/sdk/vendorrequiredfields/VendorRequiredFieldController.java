package com.appdirect.sdk.vendorrequiredfields;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.vendorrequiredfields.model.VendorRequiredFieldResponse;

/**
 * Defines the endpoint for enforcing vendor required fields on their products
 */
@RestController
@RequiredArgsConstructor
public class VendorRequiredFieldController {
	private final VendorRequiredFieldHandler vendorRequiredFieldHandler;

	@RequestMapping(method = GET,
		value = "/api/v1/restrictions",
		produces = APPLICATION_JSON_VALUE,
		consumes = APPLICATION_JSON_VALUE)
	public Callable<VendorRequiredFieldResponse> getRequiredFields() {
		return () -> vendorRequiredFieldHandler.getRequiredFields();
	}
}
