package com.appdirect.sdk.support;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContentOf {
	public static String resourceAsString(String name) throws IOException {
		return new String(resourceAsBytes(name), UTF_8);
	}

	public static byte[] resourceAsBytes(String name) throws IOException {
		return Files.readAllBytes(Paths.get(resourceUri(name)));
	}

	public static String streamAsString(InputStream is) {
		// lifted from http://stackoverflow.com/a/5445161/26605
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private static URI resourceUri(String name) {
		try {
			URL resource = ContentOf.class.getClassLoader().getResource(name);
			if (resource == null) {
				throw new RuntimeException("Cannot find resource: " + name);
			}
			return resource.toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
