package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ch.fhnw.kvan.chat.socket.ConnectionHandler;

import ch.fhnw.kvan.chat.general.ChatRoomDriver;



public class Server {
	
	private static int SIZE_THREAD_POOL = 10;
	private static ChatRoomDriver chatRoomDriver = new ChatRoomDriver();
	
	public static void main(String args[]) throws IOException {

		chatRoomDriver.connect("localhost", 1235);
		
		ServerSocket server = new ServerSocket(1235);
		System.out.println("Chat server up and running");

		for (int i = 0; i < SIZE_THREAD_POOL; i++) {
			Thread t = new Thread(new ConnectionHandler(server));
			t.start();
		}
		
		
		

	}

}
