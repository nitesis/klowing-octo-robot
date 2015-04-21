package socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
	
	ArrayList<PrintWriter> clientOut;
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public synchronized void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("gelesen: " + message);
					showMessageToAll(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // schliesst innere Klasse
	
	public static void main(String args[]) throws IOException {

		new Server().start();
	}
	
	public void start(){
		clientOut = new ArrayList<PrintWriter>();
		
		try { 
			ServerSocket serverSock = new ServerSocket(1235);
			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOut.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("Verbindung hergestellt.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end of los()

	public void showMessageToAll(String message) {
		Iterator<PrintWriter> it = clientOut.iterator();
		while(it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
