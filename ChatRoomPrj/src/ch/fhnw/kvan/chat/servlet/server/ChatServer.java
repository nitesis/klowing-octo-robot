package ch.fhnw.kvan.chat.servlet.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import ch.fhnw.kvan.chat.general.ChatRoomDriver;

//@WebServlet("/chat")
public class ChatServer extends HttpServlet{

	ChatRoomDriver crd = new ChatRoomDriver();
	
	public void init() throws ServletException {
		
		crd.connect("localhost", 8080);
	};
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Hallo Welt</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hallo Welt</h1>");
		out.println("Herzlich willkommen!");
		out.println("</body></html>");
	}
	
}
