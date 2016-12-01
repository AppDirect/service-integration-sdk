package com.appdirect.sdk.utils;

import java.net.URI;

public final class EventIdExtractor {

	private EventIdExtractor() {
	}

	public static String extractId(String eventUrl) {
		String path = URI.create(eventUrl).getPath();
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
