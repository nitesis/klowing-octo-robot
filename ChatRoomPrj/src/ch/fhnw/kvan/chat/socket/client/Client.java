package ch.fhnw.kvan.chat.socket.client;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;
import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;

/**
 * The Client connects to the given host server using the given host name and
 * port number, then announces itself at the server and creates his ClientGUI.
 * It listens and to all incoming messages, updates it's GUI respectively and
 * sends client's messages to the server.
 * 
 * % java Client <client name> <host> <port number>
 * 
 * @see Client
 * @author © ibneco, Rheinfelden; partly based on code by Robert Sedgewick and
 *         Kevin Wayne
 * @version
 */
public class Client implements ch.fhnw.kvan.chat.interfaces.IChatDriver,
		ch.fhnw.kvan.chat.interfaces.IChatRoom {
	// name of the client
	private String screenName;
	// GUI stuff
	private ClientGUI clientGUI;
	// socket for connection to chat server
	private Socket socket;
	// for writing to and reading from the server
	private Out out;
	private In in;

	// separate thread listening for incoming messages
	private ListenThread thread;

	private static Logger logger;

	public Client(String screenName) {
		// Log4J initialisation
		logger = Logger.getLogger(Client.class);
		// keep the user name
		this.screenName = screenName;
	}

	private void initGUIAndListen() {
		// create client GUI
		clientGUI = new ClientGUI(this, screenName);
		// start listening thread listening to and handling messages from Server
		thread = new ListenThread();
		thread.start();
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();

		// create client
		final Client client = new Client(args[0]);
		// connect to server
		try {
			client.connect(args[1], Integer.parseInt(args[2]));
		} catch (IOException e) {
			logger.error("Client IOException: could not connect host="
					+ args[1] + " port=" + args[2]);
		}
		// initiate clientGUI and start listening to messages from server
		client.initGUIAndListen();
		// announce itself to the server
		client.addParticipant(args[0]);
	}

	@Override
	public void connect(String host, int port) throws IOException {
		// connect to server
		try {
			socket = new Socket(host, port);
			out = new Out(socket);
			in = new In(socket);
		} catch (Exception ex) {
			logger.error("Client connecting to server: " + ex.getMessage());
		}

	}

	@Override
	public void disconnect() throws IOException {
		out.close();
	}

	@Override
	public IChatRoom getChatRoom() {
		return this;
	}

	private void updateClientTopics(String topics) {
		String[] array = topics.split("=");
		if (array.length > 1) {
			String[] topicsArray = array[1].split(";");
			if (topicsArray.length > 0) {
				clientGUI.updateTopics(topicsArray);
			} 
		} 
	}

	private void addClientTopic(String topicString) {
		String[] array = topicString.split("=");
		if (array.length > 1) {
			clientGUI.addTopic(array[1]);
		} 
	}

	private void removeClientTopic(String topicString) {
		String[] array = topicString.split("=");
		if (array.length > 1) {
			clientGUI.removeTopic(array[1]);
		} 
	}

	private void updateClientParticipants(String participants) {
		String[] array = participants.split("=");
		if (array.length > 1) {
			String[] participantsArray = array[1].split(";");
			if (participantsArray.length > 0) {
				clientGUI.updateParticipants(participantsArray);
			} 
		} 
	}

	private void addClientParticipant(String participantString) {
		String[] array = participantString.split("=");
		if (array.length > 1) {
			if (!array[1].equals(screenName)) {
				clientGUI.addParticipant(array[1]);
			} 
		} 
	}

	private void removeClientParticipant(String participantString) {
		String[] array = participantString.split("=");
		if (array.length > 1) {
			clientGUI.removeParticipant(array[1]);
		} 
	}

	@Override
	public boolean addParticipant(String name) throws IOException {
		out.println("add_participant=" + name);
		return true;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		out.println("remove_participant=" + name);
		disconnect();
		return true;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		out.println("add_topic=" + topic);
		return true;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		out.println("remove_topic=" + topic);
		return true;
	}

	@Override
	public boolean addMessage(String topic, String message) throws IOException {
		String currentMessage = ("[" + screenName + "]: " + message);
		currentMessage = "message=" + currentMessage + ";topic=" + topic;
		out.println(currentMessage);
		return true;
	}

	@Override
	public String getMessages(String topic) throws IOException {
		out.println("action=getMessages;topic=" + topic);
		return null;
	}

	@Override
	public String refresh(String topic) throws IOException {
		out.println("action=refresh;topic=" + topic);
		return null;
	}
	
	private void addClientMessage(String msgString) {
		String topic = "";
		String[] strArray = msgString.split(";");
		if (strArray.length > 1) {
			String[] topicArray = strArray[1].split("=");
			if (topicArray.length > 1) {
				topic = topicArray[1];
			}
		}		
		if (topic.equals(clientGUI.getCurrentTopic())) {
			if (strArray.length > 0) {
				String[] messageArray = strArray[0].split("=");
				if (messageArray.length > 1) {
					String msg = messageArray[1];
					clientGUI.addMessage(msg);
				} 
			} 
		} 
	}

	private void updateClientMessages(String messages) {
		String[] array = messages.split("=");
		if (array.length > 1) {
			String[] messagesArray = array[1].split(";;");

			String[] tempArray = new String[messagesArray.length];
			// reverse the order of the messages, such as the last is at the end
			int j = 0;
			for (int i = messagesArray.length - 1; i >= 0; i--) {
				tempArray[j++] = messagesArray[i];

			}

			for (int i = 0; i < tempArray.length; i++) {
				messagesArray[i] = tempArray[i];
			}
			
			clientGUI.updateMessages(messagesArray);
		}
		else {
			clientGUI.updateMessages(new String[0]);
		}
	}

	// ListenThread is listening for incoming messages from sever and handling
	// them
	private class ListenThread extends Thread {

		@Override
		public void run() {
			String s;
			while ((s = in.readLine()) != null) {
				if (s.startsWith("topics=")) {
					updateClientTopics(s);
				} else if (s.startsWith("message=")) {
					addClientMessage(s);
				} else if (s.startsWith("add_topic=")) {
					addClientTopic(s);
				} else if (s.startsWith("remove_topic=")) {
					removeClientTopic(s);
				} else if (s.startsWith("participants=")) {
					updateClientParticipants(s);
				} else if (s.startsWith("add_participant=")) {
					addClientParticipant(s);
				} else if (s.startsWith("remove_participant=")) {
					removeClientParticipant(s);
				} else if (s.startsWith("messages=")) {
					updateClientMessages(s);
				} 
			}
			
			out.close();
			in.close();
			try {
				socket.close();
				logger.info("Socket closed");
			} catch (IOException e) {
				logger.error("Closing socket: I/O Exception: " + e.getMessage());
			}
		}
	}
}
