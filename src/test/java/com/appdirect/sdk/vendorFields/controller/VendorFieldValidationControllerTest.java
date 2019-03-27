package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;

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
import com.google.inject.internal.Maps;

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
		VendorFieldsValidationResponse response = VendorFieldsValidationResponse.builder()
				.validations(Collections.singletonList(VendorFieldValidation.builder()
						.fieldName("EMAIL")
						.errorMessage("must contain @")
						.build()))
				.build();
		VendorFieldsValidationRequest vendorFieldsValidationRequest = VendorFieldsValidationRequest.builder()
				.fieldValues(Maps.newHashMap())
				.flowType(FlowType.RESELLER_FLOW)
				.operationType(OperationType.SUBSCRIPTION_CHANGE)
				.sku("SKU")
				.partner("APPDIRECT")
				.applicationIdentifier("APPLICATION_SKU")
				.build();
		when(mockVendorFieldValidationHandler.validateFields(vendorFieldsValidationRequest))
				.thenReturn(response);
		//When
		VendorFieldsValidationResponse controllerResponse =
				vendorFieldValidationController.validateFields(vendorFieldsValidationRequest).call();

		//Then
		assertThat(controllerResponse).isEqualTo(response);
	}
}

