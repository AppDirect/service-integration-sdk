package com.appdirect.sdk.vendorFields.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import com.appdirect.sdk.vendorFields.model.v2.OperationType;

public class OperationTypeConverterTest {
	private OperationTypeConverter operationTypeConverter;

	@BeforeMethod
	public void setup() {
		operationTypeConverter = new OperationTypeConverter();
	}

	@Test(dataProvider = "operationTypeDataProvider")
	public void testNoErrors(String operationType, OperationType expectedOperationType) {
		operationTypeConverter.setAsText(operationType);

		assertThat(expectedOperationType).isEqualTo(operationTypeConverter.getValue());
	}

	@Test
	public void testNull_throwsPropertyEditorSupportException() {
		String operationTypeToParse = null;

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> operationTypeConverter.setAsText(operationTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", operationTypeToParse, Arrays.toString(OperationType.values()));
	}

	@Test
	public void testEmpty_throwsPropertyEditorSupportException() {
		String operationTypeToParse = "";

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> operationTypeConverter.setAsText(operationTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", operationTypeToParse, Arrays.toString(OperationType.values()));
	}

	@Test
	public void testInvalid_throwsPropertyEditorSupportException() {
		String operationTypeToParse = "asdasdsadas";

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> operationTypeConverter.setAsText(operationTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", operationTypeToParse, Arrays.toString(OperationType.values()));
	}

	@DataProvider(name = "operationTypeDataProvider")
	private Iterator<Object[]> buildFlowTypeDataProvider() {
		return Arrays.stream(OperationType.values())
				.map(flowType -> new Object[]{flowType.getValue(), flowType})
				.collect(Collectors.toList())
				.iterator();
	}
}
