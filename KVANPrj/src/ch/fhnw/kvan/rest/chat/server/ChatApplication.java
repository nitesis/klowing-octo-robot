package ch.fhnw.kvan.rest.chat.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import ch.fhnw.kvan.rest.chat.resources.ChatResource;



public class ChatApplication extends Application {
	private final Set<Object> singletons = new HashSet<Object>();
	private final Set<Class<?>> empty = new HashSet<Class<?>>();

	public ChatApplication() {
		singletons.add(new ChatResource());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
