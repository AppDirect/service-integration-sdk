package com.appdirect.sdk.vendorFields.converter;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class LocaleObjectConverter extends PropertyEditorSupport {
	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		try {
			setValue(Locale.LanguageRange.parse(text)
					.stream()
					.sorted(Comparator.comparing(Locale.LanguageRange::getWeight).reversed())
					.map(localeRange -> Locale.forLanguageTag(localeRange.getRange()))
					.filter(locale -> Arrays.asList(Locale.getAvailableLocales()).contains(locale))
					.findFirst().get());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new PropertyEditorSupportException("Failed to serialize Locale from Accept-Language header with value=%s", text);
		}
	}
}
