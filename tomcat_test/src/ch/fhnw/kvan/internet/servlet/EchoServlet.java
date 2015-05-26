/*
 * Copyright (c) 2000-2010 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.internet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(">> EchoServlet");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body><pre>");
		Enumeration<String> e;

		out.println("Properties:");
		out.println("getMethod:        " + request.getMethod());
		out.println("getContentLength: " + request.getContentLength());
		out.println("getContentType:   " + request.getContentType());
		out.println("getProtocol:      " + request.getProtocol());
		out.println("getRemoteAddr:    " + request.getRemoteAddr());
		out.println("getRemotePort:    " + request.getRemotePort());
		out.println("getRemoteHost:    " + request.getRemoteHost());
		out.println("getRemoteUser:    " + request.getRemoteUser());
		out.println("getServerName:    " + request.getServerName());
		out.println("getAuthType:      " + request.getAuthType());
		out.println("getQueryString:   " + request.getQueryString());
		out.println("getRequestURI:    " + request.getRequestURI());
		out.println("getServletPath:   " + request.getServletPath());

		out.println("\nHeaders:");
		e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			out.println(name + " = " + request.getHeader(name));
		}

		out.println("\nParameters:");
		e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			out.println(name + " = " + request.getParameter(name));
		}

		out.println("</pre></body></html>");
		System.out.println("<< EchoServlet");
	}
}
