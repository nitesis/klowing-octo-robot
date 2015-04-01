package ch.fhnw.kvan.socket.basics;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket socket = new DatagramSocket(4711);
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		System.out.println("UDP server up and running");
		while (true) {
			socket.receive(packet);
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			int len = packet.getLength();
			byte data[] = packet.getData();
			System.out.println("Request from " + address + " from port " + port
					+ " of length " + len + "\n" + new String(data, 0, len));
		}
	}
}
