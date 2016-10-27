package com.appdirect.sdk.utils;

import static com.appdirect.sdk.utils.StringUtils.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.support.UtilityClassesAreWellDefined;

public class StringUtilsTest {
	@Test
	public void stringUtils_isWellDefinedUtilityClass() throws Exception {
		UtilityClassesAreWellDefined.verify(StringUtils.class);
	}

	@Test
	public void isEmpty_nullString_isEmpty() throws Exception {
		assertThat(isEmpty(null)).isTrue();
	}

	@Test
	public void isEmpty_emptyString_isEmpty() throws Exception {
		assertThat(isEmpty("")).isTrue();
	}

	@Test
	public void isEmpty_blankString_isNotEmpty() throws Exception {
		assertThat(isEmpty(" ")).isFalse();
	}
}
