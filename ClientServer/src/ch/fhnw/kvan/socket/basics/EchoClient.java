package ch.fhnw.kvan.socket.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		String host = "localhost";
		int port = 1234;

		Socket s = new Socket(host, port, null, 0);

		PrintWriter out = new PrintWriter(s.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String input = stdin.readLine();

		while (input != null && !input.equals("")) {
			System.out.println("Client: " + input);
			out.println(input);
			out.flush();
			System.out.println("Echo: " + in.readLine());
			input = stdin.readLine();

		}
		s.close();
	}

}
