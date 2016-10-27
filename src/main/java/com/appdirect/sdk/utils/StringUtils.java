package com.appdirect.sdk.utils;

public final class StringUtils {
	private StringUtils() {
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
}
