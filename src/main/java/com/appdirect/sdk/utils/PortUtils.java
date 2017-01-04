package com.appdirect.sdk.utils;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.ServerSocket;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PortUtils {

	public static int getRandomFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		} catch (IOException e) {
			throw new RuntimeException("Failed getting a free port", e);
		}
	}
}
