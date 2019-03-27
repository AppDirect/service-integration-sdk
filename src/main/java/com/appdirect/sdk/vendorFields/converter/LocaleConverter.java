package com.appdirect.sdk.vendorFields.converter;

import java.beans.PropertyEditorSupport;
import java.util.Locale;


public class LocaleConverter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(Locale.forLanguageTag(text));
	}
}
