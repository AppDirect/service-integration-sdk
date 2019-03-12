package com.appdirect.sdk.vendorFields.fields;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.vendorFields.fields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.fields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.fields.model.FlowType;
import com.appdirect.sdk.vendorFields.fields.model.OperationType;
import com.appdirect.sdk.vendorFields.fields.model.VendorRequiredFieldsResponse;

/**
 * Defines the endpoint for enforcing vendor required fields on their products
 */
@RestController
@RequiredArgsConstructor
public class VendorRequiredFieldController {
	private final VendorRequiredFieldHandler vendorRequiredFieldHandler;

	@RequestMapping(method = GET,
		value = "/api/v1/admin/requiredFields",
		produces = APPLICATION_JSON_VALUE)
	public Callable<VendorRequiredFieldsResponse> getRequiredFields(@RequestParam(value = "sku") String sku, @RequestParam(value = "flowType") FlowType flowType, @RequestParam(value = "operationType") OperationType operationType) {
		return () -> vendorRequiredFieldHandler.getRequiredFields(sku, flowType, operationType);
	}

	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
		webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
	}
}
