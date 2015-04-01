package ch.fhnw.kvan.socket.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Echo server up and running");

		while (true) {
			Socket s = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);

			String input = in.readLine();
			while (input != null) {
				out.println(input);
				input = in.readLine();
			}
			System.out.println("End serving " + s);
			s.close();
		}
	}
}
