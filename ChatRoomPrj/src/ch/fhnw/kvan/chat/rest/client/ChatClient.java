package ch.fhnw.kvan.chat.rest.client;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;




public class ChatClient implements ch.fhnw.kvan.chat.interfaces.IChatRoom, ch.fhnw.kvan.chat.interfaces.IChatDriver{
	
	public static void main(String[]args) {	
		
		Response result;

		Client c = ClientBuilder.newClient();
		WebTarget r = c.target("http://localhost:9998/chat");

		// ============================ POST request ===========================
		String str = "I'm damned, if I will answer!";

		result = r.path("/politics").request()
				.post(Entity.entity(str, MediaType.TEXT_PLAIN));

		if (result.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		System.out.println(result);
		System.out.println("Status:   " + result.getStatus());
		System.out.println("Location: " + result.getLocation());
		System.out.println("Content-Location: "
				+ result.getHeaders().getFirst("Content-Location"));

		// ============================ GET ====================================
		result = r.path("/politics/2/").request().accept(MediaType.TEXT_PLAIN)
				.get();

		String output = result.readEntity(String.class);

		System.out.println(result);
		System.out.println("Status:   " + result.getStatus());
		System.out.println("Location: " + result.getLocation());
		System.out.println("Content-Location: "
				+ result.getHeaders().getFirst("Content-Location"));
		System.err.println("Response: " + output);

		if (result.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		// ============================ PUT ====================================
		String msg = "I'm sick and tired of politics";

		result = r.path("/politics/3/").request()
				.put(Entity.entity(msg, MediaType.TEXT_PLAIN));

		System.out.println("Status:   " + result.getStatus());
		System.out.println("Request:   " + result);

		if (result.getStatus() != 204) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		// ============================ GET ====================================
		result = r.path("/politics/3/").request().accept(MediaType.TEXT_PLAIN)
				.get();

		System.out.println(result);
		System.out.println("Status:   " + result.getStatus());
		System.out.println("Location: " + result.getLocation());
		System.out.println("Content-Location: "
				+ result.getHeaders().getFirst("Content-Location"));
		System.err.println("Response: " + result.readEntity(String.class));

		if (result.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		// ============================ GET all msg ===========================

		result = r.path("/politics/").request().accept(MediaType.TEXT_XML)
				.get();

		System.out.println("Status:   " + result.getStatus());
		System.out.println("Location: " + result.getLocation());
		System.out.println("Content-Location: "
				+ result.getHeaders().getFirst("Content-Location"));
		System.out.println("Response: " + result.readEntity(String.class));

		if (result.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		// ============================ DELETE =================================

		result = r.path("/politics/3/").request().accept(MediaType.TEXT_XML)
				.delete();
		System.out.println(result);
		System.out.println("Status:   " + result.getStatus());
		System.out.println("Request:   " + result);

		if (result.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}

		// ============================ GET all msg ===========================

		result = r.path("/politics/").request().accept(MediaType.TEXT_XML)
				.get();

		System.out.println("Status:   " + result.getStatus());
		System.out.println("Location: " + result.getLocation());
		System.out.println("Content-Location: "
				+ result.getHeaders().getFirst("Content-Location"));
		System.out.println("Response: " + result.readEntity(String.class));

		if (result.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ result.getStatus());
		}
	}

	@Override
	public void connect(String host, int port) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IChatRoom getChatRoom() {
		
		return this;
	}

	@Override
	public boolean addParticipant(String name) throws IOException {
		Client c = ClientBuilder.newClient();
		WebTarget r = c.target("http://localhost:9998/chat");

		 r.path("/users").request()
				.put(Entity.entity(name, MediaType.TEXT_PLAIN));
		return true;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		Client c = ClientBuilder.newClient();
		WebTarget r = c.target("http://localhost:9998/chat");
		
		r.path("/users").request()
		.put(Entity.entity(name, MediaType.TEXT_PLAIN));
		return true;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMessage(String topic, String message) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMessages(String topic) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refresh(String topic) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
