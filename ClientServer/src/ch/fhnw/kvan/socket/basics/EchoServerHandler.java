package ch.fhnw.kvan.socket.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerHandler implements Runnable {
	private ServerSocket server;

	EchoServerHandler(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		while (true) {
			Socket s = null;
			try {
				s = server.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				String input = in.readLine();
				while (input != null) {
					out.println(input);
					input = in.readLine();
				}
				System.out.println("done serving " + s);
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				try {
					s.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}

}
