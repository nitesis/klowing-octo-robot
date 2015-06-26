package ch.fhnw.kvan.chat.rest.server;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import ch.fhnw.kvan.chat.general.ChatRoom;
import ch.fhnw.kvan.chat.general.ChatRoomDriver;

@Singleton
@Path("/chat")
public class ChatRoomResource extends Application {
	
	ChatRoomDriver crd = new ChatRoomDriver();
	ChatRoom chatroom = (ChatRoom) crd.getChatRoom();
	
	
	// Resources /chat/chatRoom/users/{name}
		@PUT
		@Path("users/{name}") //wohin geht die Anfrage
		@Consumes("text/plain") //wo geht die Antwort hin
		public void addUser(@PathParam("name")String name) {
			
			try {
				chatroom.addParticipant(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@DELETE
		@Path("users/{name}") //wohin geht die Anfrage
		@Consumes("text/plain") //wo geht die Antwort hin
		public void deleteUser(@PathParam("name")String name) {
			
			try {
				chatroom.removeParticipant(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@PUT
		@Path("topics/{topic}") //wohin geht die Anfrage
		@Consumes("text/plain") //wo geht die Antwort hin
		public void addTopic(@PathParam("topic")String topic) {
			
			try {
				chatroom.addTopic(topic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
//		@POST
//		@Path("topics/{topic}")
//		@Produces("text/plain")
//		public Response submitMessage(@PathParam("topic") String topic, String msg) {
//			int id = 0;
//			if (chatroom.getTopics().contains(topic)) {
//				Map<Integer, String> par = topics.get(topic);
//				if (getNewId(topic) != null)
//					id = getNewId(topic).intValue();
//				par.put(id, msg);
//			}
//
//			return Response.created(URI.create("/chat/" + topic + "/")).build();
//		}
		
		@DELETE
		@Path("topics/{topic}") //wohin geht die Anfrage
		@Consumes("text/plain") //wo geht die Antwort hin
		public void deleteTopic(@PathParam("topic")String topic) {
			
			try {
				chatroom.removeTopic(topic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
/*
 * eine Nachricht zum gegebenen Thema wird gesendet; 
 * diese muss vor dem Verschicken mit dem Namen des Klienten ergänzt werden
 */
		@POST
		@Path("messages/{topic}") //wohin geht die Anfrage
		@Produces("text/plain") //wo geht die Antwort hin
		public void postMessage(@PathParam("topic")String topic, String msg) {
			
			try {
				chatroom.addMessage(topic, msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

		/*
		 * die zehn letzten Nachrichten zum gegebenen Thema werden geholt
		 */
		@GET
		@Path("messages/{topic}") //wohin geht die Anfrage
		@Produces("text/plain") //wo geht die Antwort hin
		public Response getMessages(@PathParam("topic")String topic) {
			
			String ret = null;
			try {
				ret = chatroom.getMessages(topic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ResponseBuilder builder = Response.ok(ret, MediaType.TEXT_PLAIN);
			return builder.build();
		}
		
		/*
		 * der Klient verlangt die zehn letzten Nachrichten zum Thema 
		 * und die aktuelle Liste der Themen und der Teilnehmer
		 */
		@GET
		@Path("refresh/{topic}") 
		@Produces("text/plain") 
		public Response getMessagesAndTopics(@PathParam("topic")String topic) {
			
			String ret = null;
			try {
				ret = "Messages: " + chatroom.getMessages(topic) + "Topics: " + chatroom.getTopics() + "Users: " + chatroom.getParticipants();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ResponseBuilder builder = Response.ok(ret, MediaType.TEXT_PLAIN);
			return builder.build();

		}
		
		/*
		 * der Klient verlangt alle Meldungen, die der Server nach einem gewissen Zeitpunkt erhalten hat; 
		 * Meldungen heisst hier nicht nur Nachrichten, sondern auch alle Benachrichtigungen darüber, 
		 * dass sich z.B. ein Klient an- oder abgemeldet hat, usw.
		 */
//		@GET
//		@Path("notifications") //wohin geht die Anfrage
//		@Produces("text/plain") //wo geht die Antwort hin
//		public void getAllNotifications(@PathParam("notifications")String topic) {
//			
//			try {
//				chatroom.I DONT KNOW!!!
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		
}
