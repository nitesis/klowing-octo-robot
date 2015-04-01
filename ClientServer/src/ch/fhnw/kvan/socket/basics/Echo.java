package ch.fhnw.kvan.socket.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Echo {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket s = new Socket("10.35.147.24", 7);
		PrintWriter out = new PrintWriter(s.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String input = stdin.readLine();
		while (input != null && !input.equals("")) {
			out.println(input);
			out.flush();
			System.out.println("Echo: " + in.readLine());
			input = stdin.readLine();
		}
		s.close();
	}
}
