package com.appdirect.sdk.vendorFields.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;

/**
 * Defines the endpoint for validating the fields with the vendor
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VendorFieldValidationController {
	@Autowired
	private final VendorFieldValidationHandler vendorFieldValidationHandler;

	@RequestMapping(method = POST,
			value = "/api/v1/admin/vendorValidations",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public Callable<VendorFieldsValidationResponse> validateFields(@RequestBody VendorFieldsValidationRequest vendorFieldsValidationRequest) {
		log.info("Calling validate fields API with sku={}, flowType={}, operationType={}, partner={}, applicationIdentifier={}",
				vendorFieldsValidationRequest.getSku(),
				vendorFieldsValidationRequest.getFlowType(),
				vendorFieldsValidationRequest.getOperationType(),
				vendorFieldsValidationRequest.getPartner(),
				vendorFieldsValidationRequest.getApplicationIdentifier()
		);

		return () -> vendorFieldValidationHandler.validateFields(vendorFieldsValidationRequest);
	}

	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
		webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
	}
}
