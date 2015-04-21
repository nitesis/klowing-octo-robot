package socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	private String name;
	private String host;
	private int port;
	
	public Client (String name, String host, int port) {
		this.name = name;
		this. host = host;
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception {
		Client client = new Client("alice", "localhost", 1235);
		client.start();
	}
	
	public void start() {
		try {
		Socket chatSocket = new Socket("localhost", 1235);
		System.out.println("Chat client up and running");
		
		PrintWriter out = new PrintWriter(chatSocket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String input = stdin.readLine();
		while (input != null && !input.equals("")) {
			out.println(input);
			out.flush();
			System.out.println(in.readLine());
			input = stdin.readLine();
		}
		chatSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
