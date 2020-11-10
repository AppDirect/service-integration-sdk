package com.appdirect.sdk.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LocaleHelperTest {
    @Test(dataProvider = "validLocales")
    public void testToString_noErrors(Locale locale) {
        assertThat(LocaleHelper.toString(locale)).isEqualTo(locale.toString());
    }

    @Test
    public void testToString_fromNull_returnsNull() {
        assertThat(LocaleHelper.toString(null)).isNull();
    }

    @Test(dataProvider = "validLanguageTags")
    public void testFromString_noErrors(String languageTag, Locale expected) {
        assertThat(LocaleHelper.fromString(languageTag)).isEqualTo(expected);
    }

    @Test
    public void testFromString_fromNull_returnsNull() {
        assertThat(LocaleHelper.fromString(null)).isNull();
    }

    @DataProvider(name = "validLocales")
    public static Object[][] validLocales() {
        return new Object[][]{
                {new Locale("en")},
                {new Locale("en_US")}
        };
    }

    @DataProvider(name = "validLanguageTags")
    public static Object[][] validLanguageTags() {
        return new Object[][]{
                {"en_CA", Locale.CANADA},
                {"en_US", Locale.US}
        };
    }

    @Test
    public void testGetISO2CountryCode_forValidISO3() {
        assertThat(LocaleHelper.getISO2CountryCode("CAN")).isEqualTo("CA");
        assertThat(LocaleHelper.getISO2CountryCode("IND")).isEqualTo("IN");
    }

    @Test
    public void testGetISO3CountryCode_forValidISO2() {
        assertThat(LocaleHelper.getISO3CountryCode("CA")).isEqualTo("CAN");
        assertThat(LocaleHelper.getISO3CountryCode("IN")).isEqualTo("IND");
    }

    @Test
    public void testIsValidIso3CountryCode_forInvalidISO3() {
        assertThat(LocaleHelper.isValidIso3CountryCode("CAB")).isFalse();
    }

    @Test
    public void testIsValidIso3CountryCode_forValidISO3() {
        assertThat(LocaleHelper.isValidIso3CountryCode("USA")).isTrue();
    }

    @Test
    public void testIsValidIso2CountryCode_forInvalidISO2() {
        assertThat(LocaleHelper.isValidIso3CountryCode("UU")).isFalse();
    }

    @Test
    public void testIsValidIso2CountryCode_forValidISO2() {
        assertThat(LocaleHelper.isValidIso3CountryCode("USA")).isTrue();
    }
}
