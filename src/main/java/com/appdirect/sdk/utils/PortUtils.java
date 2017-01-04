package com.appdirect.sdk.utils;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.ServerSocket;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PortUtils {

	/**
	 * @see <a href="https://gist.github.com/vorburger/3429822">Source gist</a> 
	 * @return a random, unused TCP port
	 */
	public static int getRandomFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		} catch (IOException e) {
			throw new RuntimeException("Failed getting a free port", e);
		}
	}
}
