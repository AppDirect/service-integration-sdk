package com.appdirect.sdk.vendorFields.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.exception.PropertyEditorSupportException;
import com.google.common.collect.ImmutableMap;

public class LocaleConverterTest {
	private static final String LANGUAGES_SEPARATOR = ",";
	private static final String LANGUAGE_WEIGHT_SEPARATOR = ";";
	private static final String LANGUAGE_WITH_WEIGHT_TEMPLATE = "%s" + LANGUAGE_WEIGHT_SEPARATOR + "q=" + "%s";

	private LocaleConverter localeConverter;

	@BeforeMethod
	public void setup() {
		localeConverter = new LocaleConverter();
	}

	@Test
	public void testOneLocale() {
		String localeToParse = "en";
		List<Locale> expected = Collections.singletonList(Locale.forLanguageTag(localeToParse));

		localeConverter.setAsText(localeToParse);

		assertThat(localeConverter.getValue()).isEqualTo(expected);
	}

	@Test
	public void testTwoLocales() {
		String firstLocale = "en-ca";
		String secondLocale = "en";
		Map<String, Optional<String>> localeWeightMap = ImmutableMap.of(
				firstLocale, Optional.empty(),
				secondLocale, Optional.empty());
		String localeToParse = buildAcceptLanguageHeader(localeWeightMap);
		List<Locale> expected = Arrays.asList(Locale.forLanguageTag(firstLocale), Locale.forLanguageTag(secondLocale));

		localeConverter.setAsText(localeToParse);

		assertThat(localeConverter.getValue()).isEqualTo(expected);
	}

	@Test
	public void testMultipleLocales_orderedByWeight() {
		String firstLocale = "en-ca";
		String firstLocaleWeight = "0.8";
		String secondLocale = "en";
		String secondLocaleWeight = "0.5";
		String thirdLocale = "de";
		String thirdLocaleWeight = "0.9";
		String bestLocale = "en-us";
		Map<String, Optional<String>> localeWeightMap = ImmutableMap.of(
				firstLocale, Optional.of(firstLocaleWeight),
				secondLocale, Optional.of(secondLocaleWeight),
				thirdLocale, Optional.of(thirdLocaleWeight),
				bestLocale, Optional.empty());
		String localeToParse = buildAcceptLanguageHeader(localeWeightMap);
		List<Locale> expected = Arrays.asList(
				Locale.forLanguageTag(bestLocale),
				Locale.forLanguageTag(thirdLocale),
				Locale.forLanguageTag(firstLocale),
				Locale.forLanguageTag(secondLocale));

		localeConverter.setAsText(localeToParse);

		assertThat(localeConverter.getValue()).isEqualTo(expected);
	}

	@Test
	public void testInvalidLocale_isNotIncludedInList() {
		String localeToParse = "us";

		localeConverter.setAsText(localeToParse);

		assertThat(localeConverter.getValue()).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testNull_throwsPropertyEditorSupportException() {
		String localeToParse = null;

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> localeConverter.setAsText(localeToParse))
				.withMessage("Failed to serialize Locale from Accept-Language header with value=%s", localeToParse);
	}

	@Test
	public void testEmptyString_throwsPropertyEditorSupportException() {
		String localeToParse = "";

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> localeConverter.setAsText(localeToParse))
				.withMessage("Failed to serialize Locale from Accept-Language header with value=%s", localeToParse);
	}

	@Test
	public void testInvalidString_throwsPropertyEditorSupportException() {
		String localeToParse = "asdasdasdasdasdas";

		assertThatExceptionOfType(PropertyEditorSupportException.class)
				.isThrownBy(() -> localeConverter.setAsText(localeToParse))
				.withMessage("Failed to serialize Locale from Accept-Language header with value=%s", localeToParse);
	}

	/**
	 * Builds a String following the RFC 7231 Protocol Standard.
	 * E.g. passing a {@link Map} containing two entries: (en, {@link Optional#empty()}) and (en-us, {@link Optional#of(Object)} 0.8) will be
	 * transformed into en,en-US;q=0.8
	 */
	private String buildAcceptLanguageHeader(Map<String, Optional<String>> localeWeightMap) {
		return localeWeightMap.entrySet()
				.stream()
				.map(entry -> {
					if (!entry.getValue().isPresent()) {
						return entry.getKey();
					} else {
						return String.format(LANGUAGE_WITH_WEIGHT_TEMPLATE, entry.getKey(), entry.getValue().get());
					}
				})
				.collect(Collectors.joining(LANGUAGES_SEPARATOR));
	}
}
