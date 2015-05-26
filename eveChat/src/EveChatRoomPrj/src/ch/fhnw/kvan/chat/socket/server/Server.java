package EveChatRoomPrj.src.ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import EveChatRoomPrj.src.ch.fhnw.kvan.chat.general.ChatRoom;
import EveChatRoomPrj.src.ch.fhnw.kvan.chat.general.ChatRoomDriver;
import EveChatRoomPrj.src.ch.fhnw.kvan.chat.socket.client.ConnectionHandler;
import EveChatRoomPrj.src.ch.fhnw.kvan.chat.socket.client.ConnectionListener;

public class Server {

	// erstellt Thread (ConnectionListener), der alle aktiven
	// Socket-Verbindungen zu den verschiedenen Klienten verwaltet

	// wartet, ob sich ein neuer Klient anmeldet
	// --> neuer Klient: neuer Klient-Socket wird erstellt

	// gibt jedem neuen Klient einen Thread via ConnectionHandler (mit diversen
	// Parametern aufrufen)

	public static void main(String[] args) throws IOException {
		String host = null;
		int port = 1235;
		
		// erzeugt Server-Socket, um mit Klienten zu kommunizieren
		ChatRoomDriver crd;
		ServerSocket server = null;
		ConnectionListener thread = new ConnectionListener();
		Socket s;
		
		try {
			crd = new ChatRoomDriver();
			crd.connect(host, port);
			server = new ServerSocket(port);
			
			thread.start();
			
			while (true) {
				//wait for new clients
				s = server.accept();
				//add new client
				ConnectionHandler connection = new ConnectionHandler(s);
				thread.addConnection(connection);
				connection.setChatRoom(ChatRoom.getInstance());
				//start connectionHandler for this client
				connection.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.close();
		}
	}
}
