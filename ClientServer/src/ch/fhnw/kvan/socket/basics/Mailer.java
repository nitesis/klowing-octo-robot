package ch.fhnw.kvan.socket.basics;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Mailer {
	private static Scanner in;
	private static Writer out;

	/**
	 * SMTP Server: smtp.fhnw.ch, smtp.googlemail.com, mail.bluewin.ch
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// open Socket to server using smtp
		// args[0] must contain reference to mail server
		Socket s = new Socket("pop.googlemail.com", 995);
		// open Scanner and PrintWriter on Socket
		in = new Scanner(s.getInputStream());
		out = new PrintWriter(s.getOutputStream(), true);
		// Determine name of local host
		String hostName = InetAddress.getLocalHost().getHostName();
		System.out.println(hostName);
		receive();
		send("HELO " + hostName);
		receive();
		send("MAIL FROM: <carlo.nicola@fhnw.ch>");
		receive();
		send("RCPT TO: <carlo.nicola@fhnw.ch>");
		receive();
		send("DATA");
		receive();
		send("To: carlo.nicola@fhnw.ch");
		send("Subject: Ostern");
		send("Hallo, was w�nscht Du zu Ostern?");
		send(".");
		receive();
		s.close();
	}

	private static void receive() throws IOException {
		if (in.hasNext()) {
			System.err.println(in.nextLine()); // print in red
		}
	}

	public static void send(String s) throws IOException {
		out.write(s.replaceAll("\n", "\r\n"));
		out.write("\r\n");
	}
}
