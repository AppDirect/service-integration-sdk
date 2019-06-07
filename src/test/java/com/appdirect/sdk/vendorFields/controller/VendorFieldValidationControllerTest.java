package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.bind.WebDataBinder;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
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
	@Mock
	private WebDataBinder webdataBinder;

	private VendorFieldValidationController vendorFieldValidationController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		vendorFieldValidationController = new VendorFieldValidationController(mockVendorFieldValidationHandler);
	}

	@Test
	public void testValidateFields_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		List<Locale> locales = Collections.singletonList(Locale.US);
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
				.editionCode("SKU")
				.partner("APPDIRECT")
				.build();
		when(mockVendorFieldValidationHandler.validateFields(vendorFieldsValidationRequest))
				.thenReturn(response);
		//When
		VendorFieldsValidationResponse controllerResponse =
				vendorFieldValidationController.validateFields(vendorFieldsValidationRequest, locales).call();

		//Then
		assertThat(controllerResponse).isEqualTo(response);
	}

	@Test
	public void testInitBinder() {
		vendorFieldValidationController.initBinder(webdataBinder);

		verify(webdataBinder, times(1)).registerCustomEditor(eq(FlowType.class), any(FlowTypeConverter.class));
		verify(webdataBinder, times(1)).registerCustomEditor(eq(OperationType.class), any(OperationTypeConverter.class));
		verify(webdataBinder, times(1)).registerCustomEditor(eq(List.class), any(LocaleConverter.class));
	}
}

