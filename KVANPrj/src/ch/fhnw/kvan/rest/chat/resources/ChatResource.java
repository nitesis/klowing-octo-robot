package ch.fhnw.kvan.rest.chat.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Path("/chat")
public class ChatResource {
	private final Map<String, Map<Integer, String>> topics;

	public ChatResource() {
		topics = new HashMap<String, Map<Integer, String>>();
		Map<Integer, String> par = new HashMap<Integer, String>();
		par.put(1, "one");
		topics.put("politics", par);

	}

	// Resources /chat/{topic}
	@POST
	@Path("{topic}")
	@Produces("text/plain")
	public Response submitMessage(@PathParam("topic") String topic, String msg) {
		int id = 0;
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			if (getNewId(topic) != null)
				id = getNewId(topic).intValue();
			par.put(id, msg);
		}

		return Response.created(URI.create("/chat/" + topic + "/")).build();
	}

	// Resources /chat/{topic}/{id}
	@GET
	@Path("{topic}/{id}")
	@Produces("text/plain")
	public Response getMessage(@PathParam("topic") String topic,
			@PathParam("id") int id) {
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			String response = par.get(id);
			ResponseBuilder builder = Response.ok(response,
					MediaType.TEXT_PLAIN);
			return builder.build();
		} else {
			ResponseBuilder builder = Response.status(Status.NOT_FOUND);
			return builder.build();
		}
	}

	@GET
	@Path("{topic}")
	@Produces("text/xml")
	public Response getAllMessagesAt(@PathParam("topic") String topic) {
		int id = 0;
		int start = 0;

		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			List<String> list = new LinkedList<String>();
			if (getMaxId(topic) != null)
				id = getMaxId(topic).intValue();

			while (start <= id) {
				list.add(par.get(start));
				start++;
			}
			ResponseBuilder builder = Response.ok(list.toString(),
					MediaType.TEXT_XML);
			return builder.build();

		} else {
			ResponseBuilder builder = Response.status(Status.NOT_FOUND);
			return builder.build();
		}
	}

	@PUT
	@Path("{topic}/{id}")
	@Consumes("text/plain")
	public void changeMessage(@PathParam("topic") String topic,
			@PathParam("id") int id, String msg) {
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			par.put(id, msg);
		}
	}

	@DELETE
	@Path("{topic}/{id}")
	@Consumes("text/plain")
	@Produces("text/xml")
	public Response deleteMessage(@PathParam("topic") String topic,
			@PathParam("id") int id) {
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			// On the server side print all the messages under topic
			System.out
					.println("Before DELETE there are following msg in topic: "
							+ par);
			if (par.get(id) != null) {
				String msg = par.remove(id);
				if (msg == null) {
					ResponseBuilder builder = Response.status(Status.GONE);
					return builder.build();
				}

			} else {
				ResponseBuilder builder = Response.status(Status.OK);
				return builder.build();
			}

		} else {
			ResponseBuilder builder = Response.status(Status.NOT_FOUND);
			return builder.build();
		}

		ResponseBuilder builder = Response.status(Status.OK);
		return builder.build();
	}

	public AtomicInteger getNewId(String topic) {
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			return new AtomicInteger(par.size() + 1);
		} else
			return null;
	}

	public AtomicInteger getMaxId(String topic) {
		if (topics.containsKey(topic) == true) {
			Map<Integer, String> par = topics.get(topic);
			return new AtomicInteger(par.size());
		} else
			return null;
	}
}
