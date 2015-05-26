package ch.fhnw.kvan.chat.socket.client;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.general.ChatRoom;
import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;

/**
 * The ConnectionHandler creates an input and an output stream for the given
 * client socket. It listens continuously for incoming data: If a new message comes in,
 * a new participant has announced himself or a participant left, this information is
 * forwarded to the ChatRoom. At the same time, every such notification is temporarily 
 * stored here, until the ConnectionListener picks it up.
 * 
 * @see ConnectionHandler
 * @author © ibneco, Rheinfelden; partly based on code by Robert Sedgewick and
 *         Kevin Wayne
 * @version
 */
public class ConnectionHandler extends Thread {
	private final Socket socket;
	private final Out out;
	private final In in;
	// message holds notifications which will be sent to all active clients; 
	// i.e. not only incoming messages, but also add/remove topic requests and 
	// add/remove participant requests.
	private String message; 
	private ChatRoom chatRoom;

	private static Logger logger;

	public ConnectionHandler(Socket socket) {
		// Log4J initialisation
		logger = Logger.getLogger(ConnectionHandler.class);

		in = new In(socket);
		out = new Out(socket);
		this.socket = socket;
		
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public void println(String s) {
		out.println(s);
	}

	@Override
	public void run() {
		String s;
		while ((s = in.readLine()) != null) {
			try {
				if (s.startsWith("add_participant=")) {
					// Add user to ChatRoom
					if (addParticipant(s)) {
						// Send current topic list and active participant list to
						// new user
						println(chatRoom.getTopics());
						println(chatRoom.getParticipants());
						// temporarily store this notification to be sent to other
						// active clients,
						// such as they add the new participant to their local
						// participant list
						setMessage(s);
					}
				} else if (s.startsWith("remove_participant=")) {
					// Remove user from ChatRoom
					if (removeParticipant(s)) {
						// temporarily store this notification to be sent to other
						// active clients,
						// such as they remove the participant who left the chat
						// room from their local participant list
						setMessage(s);
					}
				} else if (s.startsWith("message=")) {
					// save the message in ChatRoom
					if (saveMessage(s)) {
						// temporarily store this message here to be sent to other
						// active clients,
						// such as they receive this message as soon as it arrived
						setMessage(s);
					}

				} else if (s.startsWith("add_topic=")) {
					// Add topic to ChatRoom
					if (addTopic(s)) {
						// temporarily store this notification to be sent to other
						// active clients,
						// such as they add the new topic to their local topic list
						setMessage(s);
					}
				} else if (s.startsWith("remove_topic=")) {
					// Remove topic from ChatRoom
					if (removeTopic(s)) {
						// temporarily store this notification to be sent to other
						// active clients,
						// such as they remove the topic, which was removed by a
						// client, from their local topic list
						setMessage(s);
					}
				} else if (s.startsWith("action=getMessages")) {
					// Get last 10 messages to the given topic from ChatRoom
					getMessages(s);
				} else if (s.startsWith("action=refresh")) {
					// Refresh the last 10 messages to the given topic and get
					// lists of topics and of participants
					refresh(s);
				} 
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not excute request: "
						+ s);
			}
		}
		out.close();
		in.close();
		try {
			socket.close();
			logger.info("Socket closed");
		} catch (IOException e) {
			logger.error("Connection I/O: " + e.getMessage());
		}

	}

	/*********************************************************************
	 * The methods getMessage() and setMessage() are synchronized so that the
	 * thread in Connection doesn't call setMessage() while the
	 * ConnectionListener thread is calling getMessage().
	 *********************************************************************/
	public synchronized String getMessage() {
		String m = this.message;
		this.message = null;
		return m;
	}

	private synchronized void setMessage(String s) {
		this.message=s;
	}

	private boolean addTopic(String s) {
		boolean success = false;
		String[] topicArray = s.split("=");
		if (topicArray.length > 1 && !topicArray[1].equalsIgnoreCase("")) {
			try {
				success =  chatRoom.addTopic(topicArray[1]);
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not add topic "
						+ topicArray[1]);
			}
		}
		return success;
	}

	private boolean removeTopic(String s) {
		boolean success = false;
		String[] topicArray = s.split("=");
		if (topicArray.length > 1 && !topicArray[1].equalsIgnoreCase("")) {
			try {
				success =  chatRoom.removeTopic(topicArray[1]);
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not remove topic "+ topicArray[1]);
			}
		}
		return success;
	}

	private boolean saveMessage(String s) {
		boolean success = false;
		String msg = "";
		String topic = "";
		String[] topicArray;
		String[] strArray = s.split(";");
		String[] messageArray = strArray[0].split("=");
		if (messageArray.length > 1) {
			msg = messageArray[1];
		}
		if (strArray.length > 1) {
			topicArray = strArray[1].split("=");
			if (topicArray.length > 1) {
				topic = topicArray[1];
			}
		}
		if (!topic.equalsIgnoreCase("") && !msg.equalsIgnoreCase("")) {
			try {
				success = chatRoom.addMessage(topic, msg);
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not save message topic="
						+ topic + " message=" + msg);
			}
		}
		return success;
	}

	private void getMessages(String s) {
				
		String topic = "";
		String[] array = s.split(";");
		if (array.length > 1) {
			String[] topicArray = array[1].split("=");
			if (topicArray.length > 1) {
				topic = topicArray[1];
			}
		}
		if (!topic.equalsIgnoreCase("")) {
			try {
				String messages = chatRoom.getMessages(topic);
				if (!messages.equalsIgnoreCase("")) {
					println(messages);
				}
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not refresh.");
			}
		}
		try {
			// Send current topic list and active participant list to new user
			println(chatRoom.getTopics());
			println(chatRoom.getParticipants());
		} catch (IOException e) {
			logger.error("ConnectionHandler IOException: could not refresh.");
		}
	}

	private void refresh(String s) {
		String topic = "";
		String[] array = s.split(";");
		if (array.length > 1) {
			String[] topicArray = array[1].split("=");
			if (topicArray.length > 1) {
				topic = topicArray[1];
			}
		}
		if (!topic.equalsIgnoreCase("")) {
			try {
				String messages = chatRoom.refresh(topic);
				if (!messages.equalsIgnoreCase("")) {
					println(messages);
				}
			} catch (IOException e) {
				logger.error("ConnectionHandler IOException: could not refresh.");
			}
		}
		try {
			// Send current topic list and active participant list to new user
			println(chatRoom.getTopics());
			println(chatRoom.getParticipants());
		} catch (IOException e) {
			logger.error("ConnectionHandler IOException: could not refresh.");
		}
	}

	private boolean addParticipant(String s) {
		boolean success = false;
		String[] nameArray = s.split("=");
		if (nameArray.length > 1) {
			String clientName = nameArray[1];
			if (!clientName.equalsIgnoreCase("")) {
				try {
					success = chatRoom.addParticipant(clientName);
				} catch (IOException e) {
					logger.error("ConnectionHandler IOException: could not add participant "
							+ clientName);
				}
			}
		}
		return success;
	}

	private boolean removeParticipant(String s) {
		
		boolean  success = false;
		String[] nameArray = s.split("=");
		if (nameArray.length > 1) {
			String clientName = nameArray[1];
			if (!clientName.equalsIgnoreCase("")) {
				try {
					success = chatRoom.removeParticipant(clientName);
				} catch (IOException e) {
					logger.error("ConnectionHandler IOException: could not remove participant "+ clientName);
				}
			}
		}
		return success;
	}

}
