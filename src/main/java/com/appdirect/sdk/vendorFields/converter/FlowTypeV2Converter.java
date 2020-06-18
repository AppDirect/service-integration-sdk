package com.appdirect.sdk.vendorFields.converter;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import com.appdirect.sdk.vendorFields.model.FlowTypeV2;
import java.beans.PropertyEditorSupport;

public class FlowTypeV2Converter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
		try {
			setValue(FlowTypeV2.fromValue(text));
		} catch (IllegalArgumentException e) {
			throw new PropertyEditorSupportException(e.getMessage());
		}
	}
}
