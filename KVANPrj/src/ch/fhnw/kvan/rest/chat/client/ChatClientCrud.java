package ch.fhnw.kvan.rest.chat.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ChatClientCrud {

	public static void main(String[] args) {
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
}
