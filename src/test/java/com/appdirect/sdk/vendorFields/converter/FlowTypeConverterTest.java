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
import com.appdirect.sdk.vendorFields.model.FlowType;

public class FlowTypeConverterTest {
	private FlowTypeConverter flowTypeConverter;

	@BeforeMethod
	public void setup() {
		flowTypeConverter = new FlowTypeConverter();
	}

	@Test(dataProvider = "flowTypeDataProvider")
	public void testNoErrors(String flowTypeValue, FlowType expectedFlowType) {
		flowTypeConverter.setAsText(flowTypeValue);

		assertThat(expectedFlowType).isEqualTo(flowTypeConverter.getValue());
	}

	@Test
	public void testNull_throwsPropertyEditorSupportException() {
		String flowTypeToParse = null;

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> flowTypeConverter.setAsText(flowTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", flowTypeToParse, Arrays.toString(FlowType.values()));
	}

	@Test
	public void testEmpty_throwsPropertyEditorSupportException() {
		String flowTypeToParse = "";
		
		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> flowTypeConverter.setAsText(flowTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", flowTypeToParse, Arrays.toString(FlowType.values()));
	}

	@Test
	public void testInvalid_throwsPropertyEditorSupportException() {
		String flowTypeToParse = "asdasdsadas";

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> flowTypeConverter.setAsText(flowTypeToParse))
				.withMessage("Unknown enum type %s, Allowed values are %s", flowTypeToParse, Arrays.toString(FlowType.values()));
	}

	@DataProvider(name = "flowTypeDataProvider")
	private Iterator<Object[]> buildFlowTypeDataProvider() {
		return Arrays.stream(FlowType.values())
				.map(flowType -> new Object[]{flowType.getValue(), flowType})
				.collect(Collectors.toList())
				.iterator();
	}
}
