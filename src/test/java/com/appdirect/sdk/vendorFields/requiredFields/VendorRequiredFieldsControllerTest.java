package com.appdirect.sdk.vendorFields.requiredFields;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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

import com.appdirect.sdk.vendorFields.fieldsValidation.VendorFieldValidationController;
import com.appdirect.sdk.vendorFields.fieldsValidation.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.fieldsValidation.model.VendorFieldValidation;
import com.appdirect.sdk.vendorFields.fieldsValidation.model.VendorFieldsValidationResponse;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.requiredFields.model.FieldType;
import com.appdirect.sdk.vendorFields.requiredFields.model.Form;
import com.appdirect.sdk.vendorFields.requiredFields.model.VendorRequiredField;
import com.appdirect.sdk.vendorFields.requiredFields.model.VendorRequiredFieldsResponse;

@RunWith(MockitoJUnitRunner.class)
public class VendorRequiredFieldsControllerTest {
	@Mock
	private VendorRequiredFieldHandler mockVendorRequiredFieldHandler;

	private VendorRequiredFieldController tested;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		tested = new VendorRequiredFieldController(mockVendorRequiredFieldHandler);
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
		Form form = new Form(validations,4);
		List<Form>  formList = new ArrayList();
		formList.add(form);
		VendorRequiredFieldsResponse response = new VendorRequiredFieldsResponse(formList);

		//When
		when(mockVendorRequiredFieldHandler.getRequiredFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE))
				.thenReturn(response);
		tested.getRequiredFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE);

		//Then
		VendorRequiredFieldsResponse controllerResponse = tested.getRequiredFields(sku, FlowType.RESELLER_FLOW, OperationType.SUBSCRIPTION_CHANGE).call();
		assertThat(controllerResponse).isEqualTo(response);
	}
}
