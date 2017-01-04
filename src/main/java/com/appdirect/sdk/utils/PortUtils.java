package com.appdirect.sdk.utils;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.ServerSocket;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PortUtils { //NOSONAR, the private constructor is created by Lombok

	/**
	 * @return a random, unused TCP port
	 * @see <a href="https://gist.github.com/vorburger/3429822">Source gist</a>
	 */
	public static int getRandomFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		} catch (IOException e) {
			throw new FreePortNotFoundException("Failed getting a free port", e);
		}
	}
}
