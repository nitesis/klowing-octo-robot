package ch.fhnw.kvan.socket.basics;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

class UDPClient {
	public static void main(String args[]) throws Exception {
		DatagramPacket packet;
		while (true) {
			InetAddress ia = InetAddress.getByName("localhost");
			String s = new Date().toString();
			packet = new DatagramPacket(s.getBytes(), s.length(), ia, 4711);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			System.out.println("Weg ist es");
			Thread.sleep(1000);
		}
	}
}
