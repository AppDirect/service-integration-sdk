package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import com.appdirect.sdk.vendorFields.model.FlowType;

public class FlowTypeConverter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
		try {
			setValue(FlowType.fromValue(text));
		} catch (IllegalArgumentException e) {
			throw new PropertyEditorSupportException(e.getMessage());
		}
	}
}
