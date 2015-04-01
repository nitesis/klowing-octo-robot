package ch.fhnw.kvan.socket.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class EchoHandler implements Runnable {
	private Socket s;

	EchoHandler(Socket s) {
		this.s = s;

	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			String input = in.readLine();
			while (input != null) {
				out.println(input);
				input = in.readLine();
			}
			System.out.println("End serving " + s);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
