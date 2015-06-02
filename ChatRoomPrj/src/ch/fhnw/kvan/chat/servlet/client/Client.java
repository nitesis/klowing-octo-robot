package ch.fhnw.kvan.chat.servlet.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;

public class Client implements ch.fhnw.kvan.chat.interfaces.IChatRoom, ch.fhnw.kvan.chat.interfaces.IChatDriver{

	
	private String screenName;

	public Client(String screenName) {
		this.screenName = screenName;
	}
	

	@Override
	public boolean addParticipant(String name) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=addParticipant&name=" + name;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		return true;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=removeParticipant&name=" + name;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		disconnect();
		return true;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=addTopic&topic=" + topic;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		return true;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=removeTopic&topic=" + topic;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		return true;
	}

	@Override
	public boolean addMessage(String topic, String message) throws IOException {
		String currentMessage = ("[" + screenName + "]: " + message);
		//currentMessage = "message=" + currentMessage + ";topic=" + topic;
		
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=postMessage&message=" + currentMessage + "&topic=" + topic;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		return true;
	}

	@Override
	public String getMessages(String topic) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=getMessage&topic=" + topic;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}

	@Override
	public String refresh(String topic) throws IOException {
		final String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/chat/?action=refresh&topic=" + topic;
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
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
}