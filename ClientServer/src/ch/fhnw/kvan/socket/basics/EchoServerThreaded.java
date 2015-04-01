package ch.fhnw.kvan.socket.basics;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerThreaded {
	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Echo server (with threads) up and running");

		while (true) {
			Socket s = server.accept();

			Thread t = new Thread(new EchoHandler(s));
			t.start();

		}
	}
}
