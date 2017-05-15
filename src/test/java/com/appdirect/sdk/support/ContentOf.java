/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
