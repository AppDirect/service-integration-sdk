package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.model.FieldType;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.Form;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredField;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;

@RunWith(MockitoJUnitRunner.class)
public class VendorRequiredFieldsControllerTest {
	@Mock
	private VendorRequiredFieldHandler mockVendorRequiredFieldHandler;

	private VendorRequiredFieldController vendorRequiredFieldController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		vendorRequiredFieldController = new VendorRequiredFieldController(mockVendorRequiredFieldHandler);
	}

	@Test
	public void testValidateFields_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		Form form = Form.builder()
				.fields(Collections.singletonList(VendorRequiredField.builder()
						.fieldType(FieldType.COUNTRY)
						.inputCode("ABCDE")
						.inputTitle("COUNTRY")
						.order(1)
						.prePopulatedValue("GERMANY")
						.required(true)
						.subTitle("REGION")
						.toolTip("place your country")
						.build()))
				.order(4)
				.build();
		VendorRequiredFieldsResponse response = new VendorRequiredFieldsResponse(Collections.singletonList(form));
		when(mockVendorRequiredFieldHandler.getRequiredFields(
				"SKU",
				FlowType.RESELLER_FLOW,
				OperationType.SUBSCRIPTION_CHANGE,
				Locale.CANADA)).thenReturn(response);

		//When
		VendorRequiredFieldsResponse controllerResponse = vendorRequiredFieldController
				.getRequiredFields(
						"SKU",
						FlowType.RESELLER_FLOW,
						OperationType.SUBSCRIPTION_CHANGE,
						Locale.CANADA).call();

		//Then
		assertThat(controllerResponse).isEqualTo(response);
	}
}
