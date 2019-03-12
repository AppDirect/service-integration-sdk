package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;

import com.appdirect.sdk.vendorFields.fields.model.OperationType;

public class OperationTypeConverter extends PropertyEditorSupport {

	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(OperationType.fromValue(text));
	}
}
