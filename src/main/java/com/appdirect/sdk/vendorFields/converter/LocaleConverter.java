package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.LocaleUtils;

public class LocaleConverter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(LocaleUtils.toLocale(text));
	}
}
