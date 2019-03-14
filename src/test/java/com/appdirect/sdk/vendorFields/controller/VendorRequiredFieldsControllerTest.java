package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.controller.VendorRequiredFieldController;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.model.FieldType;
import com.appdirect.sdk.vendorFields.model.Form;
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
		String sku = "SKU";
		List<VendorRequiredField> validations = new ArrayList<>();
		VendorRequiredField validation = new VendorRequiredField().builder()
				.fieldType(FieldType.COUNTRY)
				.inputCode("ABCDE")
				.inputTitle("COUNTRY")
				.order(1)
				.prePopulatedValue("GERMANY")
				.required(true)
				.subTitle("REGION")
				.toolTip("place your country")
				.build();
		validations.add(validation);
		Form form = new Form(validations, 4);
		List<Form> formList = new ArrayList();
		formList.add(form);
		VendorRequiredFieldsResponse response = new VendorRequiredFieldsResponse(formList);

		//When
		when(mockVendorRequiredFieldHandler.getRequiredFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE))
				.thenReturn(response);

		//Then
		VendorRequiredFieldsResponse controllerResponse = vendorRequiredFieldController.getRequiredFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE).call();
		assertThat(controllerResponse).isEqualTo(response);
	}
}
