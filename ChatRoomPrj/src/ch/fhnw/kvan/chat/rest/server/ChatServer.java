package ch.fhnw.kvan.chat.rest.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class ChatServer {
	public static void main(String[] args) throws IOException {

		final String baseUri = "http://localhost:9998/";

		final ResourceConfig rc0 = ResourceConfig
				.forApplication(new ChatRoomResource());
		
//		final ResourceConfig rc0 =
//	               new ResourceConfig().packages("ch.fhnw.kvan.chat.rest.server");

		System.out.println("Starting grizzly...");
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(baseUri), rc0);

		System.out
				.println(String
						.format("Jersey app started with WADL available at "
								+ "%s/application.wadl\nTry out %s\nHit enter to stop it...",
								baseUri, baseUri));

		System.in.read();
		httpServer.shutdown();
	}

}