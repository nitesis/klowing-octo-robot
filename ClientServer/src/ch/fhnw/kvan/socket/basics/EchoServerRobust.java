package ch.fhnw.kvan.socket.basics;

import java.io.IOException;
import java.net.ServerSocket;

public class EchoServerRobust {
	private static int SIZE_THREAD_POOL = 10;

	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		System.out
				.println("Echo server (with threads but robust) up and running");
		for (int i = 0; i < SIZE_THREAD_POOL; i++) {
			Thread t = new Thread(new EchoServerHandler(server));
			t.start();
		}
	}
}
