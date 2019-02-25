package com.appdirect.sdk.vendorrequiredfields.converter;

import java.beans.PropertyEditorSupport;

import com.appdirect.sdk.vendorrequiredfields.model.FlowType;

public class FlowTypeConverter extends PropertyEditorSupport {

	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(FlowType.fromValue(text));
	}
}
