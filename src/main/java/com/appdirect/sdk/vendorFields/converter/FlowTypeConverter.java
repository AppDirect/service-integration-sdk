package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;

import com.appdirect.sdk.vendorFields.model.FlowType;

public class FlowTypeConverter extends PropertyEditorSupport {

	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(FlowType.fromValue(text));
	}
}
