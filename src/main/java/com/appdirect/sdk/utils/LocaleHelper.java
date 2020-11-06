package com.appdirect.sdk.utils;

import java.util.Locale;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocaleHelper {

    private static final BiMap<String, String> ISO2_TO_ISO3_COUNTRY_BIMAP = HashBiMap.create();

    static {
        for (String iso2CountryCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", iso2CountryCode);
            ISO2_TO_ISO3_COUNTRY_BIMAP.put(iso2CountryCode, locale.getISO3Country());
        }
    }

    public static String toString(Locale locale) {
        return Objects.nonNull(locale) ? locale.toString() : null;
    }

    public static Locale fromString(String languageTag) {
        return !StringUtils.isEmpty(languageTag) ?
                LocaleUtils.toLocale(languageTag) : null;
    }

    public static String getISO3CountryCode(String iso2CountryCode) {
        return ISO2_TO_ISO3_COUNTRY_BIMAP.get(iso2CountryCode);
    }

    public static String getISO2CountryCode(String iso3CountryCode) {
        return ISO2_TO_ISO3_COUNTRY_BIMAP.inverse().get(iso3CountryCode);
    }

    public static boolean isValidIso3CountryCode(String iso3CountryCode) {
        return ISO2_TO_ISO3_COUNTRY_BIMAP.inverse().get(iso3CountryCode) != null;
    }

    public static boolean isValidIso2CountryCode(String code) {
        return ISO2_TO_ISO3_COUNTRY_BIMAP.keySet().contains(code);
    }
}
