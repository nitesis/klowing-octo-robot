package ch.fhnw.kvan.chat.socket.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  ConnectionListener extends Thread {
	List<ConnectionHandler> connections;
	
	public ConnectionListener() {
		this.connections = Collections.synchronizedList(new ArrayList<ConnectionHandler>());
	}
	
	public void addConnection(ConnectionHandler h) {
		this.connections.add(h);
	}
	
	public void run() {
		String s;
		while (true) {
			for (int i = 0; i < connections.size(); ++i) {
				//überprüfe bei jedem i: muss etwas aktualisiert werden? (message abholen)
				//etwas: Gesprächsthemenliste, Teilnehmerliste
				//Änderungen: Thema add/remove; Client login/logout --> Nachricht an alle
				s = connections.get(i).getMessage();
				if (s != null) {
					//schicke neue Nachricht an alle aktiven Clients
					if (s.startsWith("add_topic=")) {
						for (int j = 0; j < connections.size(); ++j) {
							connections.get(j).println(s);
						}
					} else if (s.startsWith("remove_topic=")) {
						for (int j = 0; j < connections.size(); ++j) {
							connections.get(j).println(s);
						}
					} else if (s.startsWith("name=")) {
						for (int j = 0; j < connections.size(); ++j) {
							connections.get(j).println(s);
						}
					} else if (s.startsWith("remove_name=")) {
						for (int j = 0; j < connections.size(); ++j) {
							connections.get(j).println(s);
						}
					}
				}
			}
		}
	}

}
