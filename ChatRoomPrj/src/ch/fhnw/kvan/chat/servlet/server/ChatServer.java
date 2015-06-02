package ch.fhnw.kvan.chat.servlet.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import ch.fhnw.kvan.chat.general.ChatRoom;
import ch.fhnw.kvan.chat.general.ChatRoomDriver;

@WebServlet("/chat")
public class ChatServer extends HttpServlet{

	private ChatRoomDriver crd = new ChatRoomDriver();
	private ChatRoom chatRoom;
	
	private PrintWriter out;
	private String message; 
	public void init() throws ServletException {
		
		crd.connect("localhost", 8080);
	};
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String req = request.getParameter("action");
		response.setContentType("text/html");
		out = response.getWriter();
		if ("addParticipant".equals(req)) {
			// Add user to ChatRoom
			boolean success = false;
			success = chatRoom.addParticipant(request.getParameter("name"));
			
			if (success) {
				out.write(chatRoom.getTopics());
				out.write(chatRoom.getParticipants());
				setMessage("addParticipant" + request.getParameter("name"));
			}
			
		} else if ("removeParticipant".equals(req)) {
			// Remove user from ChatRoom
			boolean success = chatRoom.removeParticipant(request.getParameter("name"));
			
			if (success) {
				setMessage("removeParticipant" + request.getParameter("name"));
			}
				
		} else if ("addTopic".equals(req)) {
			boolean success = chatRoom.addTopic(request.getParameter("topic"));
		// save the message in ChatRoom
		if (success) {
			// temporarily store this message here to be sent to other
			// active clients,
			// such as they receive this message as soon as it arrived
			setMessage("addTopic" + request.getParameter("topic"));
		}

		} else if ("removeTopic".equals(req)) {
			boolean success = chatRoom.addTopic(request.getParameter("topic"));
		// save the message in ChatRoom
		if (success) {
			// temporarily store this message here to be sent to other
			// active clients,
			// such as they receive this message as soon as it arrived
			setMessage("removeTopic" + request.getParameter("topic"));
		}
		} else if ("postMessage".equals(req)) {
			boolean success = chatRoom.addMessage(request.getParameter("topic"), request.getParameter("message"));
			if (success) {
				setMessage("postMessage" + request.getParameter("topic") + request.getParameter("message"));
			}
		} else if ("getMessages".equals(req)) {
			String message = chatRoom.getMessages(request.getParameter("topic"));
			out.println(message);	
			}
	
		out.close();		
	}
	
	public synchronized String getMessage() {
		String m = this.message;
		this.message = null;
		return m;
	}

	private synchronized void setMessage(String s) {
		this.message=s;
	}
}	

