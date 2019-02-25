package com.appdirect.sdk.vendorrequiredfields.converter;

import java.beans.PropertyEditorSupport;

import com.appdirect.sdk.vendorrequiredfields.model.OperationType;

public class OperationTypeConverter extends PropertyEditorSupport {

	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(OperationType.fromValue(text));
	}
}
