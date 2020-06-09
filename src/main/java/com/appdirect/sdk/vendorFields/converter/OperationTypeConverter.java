package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import com.appdirect.sdk.vendorFields.model.OperationType;

public class OperationTypeConverter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
		try {
			setValue(OperationType.fromValue(text));
		} catch (IllegalArgumentException e) {
			throw new PropertyEditorSupportException(e.getMessage());
		}
		
	}
}
