package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorFieldValidation;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;

@RunWith(MockitoJUnitRunner.class)
public class VendorFieldValidationControllerTest {
	@Mock
	private VendorFieldValidationHandler mockVendorFieldValidationHandler;

	private VendorFieldValidationController vendorFieldValidationController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		vendorFieldValidationController = new VendorFieldValidationController(mockVendorFieldValidationHandler);
	}

	@Test
	public void testValidateFields_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String sku = "SKU";
		Map<String, String> fieldsToValidate = new HashMap<>();
		List<VendorFieldValidation> validations = new ArrayList<>();
		VendorFieldValidation validation = new VendorFieldValidation("EMAIL", "must contain @");
		validations.add(validation);
		VendorFieldsValidationResponse response = new VendorFieldsValidationResponse(validations);
		VendorFieldsValidationRequest vendorFieldsValidationRequest = VendorFieldsValidationRequest.builder()
				.fieldValues(fieldsToValidate)
				.flowType(FlowType.RESELLER_FLOW)
				.operationType(OperationType.SUBSCRIPTION_CHANGE)
				.sku(sku)
				.build();

		//When
		when(mockVendorFieldValidationHandler.validateFields(vendorFieldsValidationRequest))
				.thenReturn(response);

		//Then
		VendorFieldsValidationResponse controllerResponse = vendorFieldValidationController.validateFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE, fieldsToValidate).call();
		assertThat(controllerResponse).isEqualTo(response);
	}
}
