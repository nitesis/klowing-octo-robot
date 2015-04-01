package ch.fhnw.kvan.socket.basics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class PoorManPing {
	public static void main(String args[]) throws SocketException,
			UnsupportedEncodingException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface intf = interfaces.nextElement();
			System.out.print(intf.getName());
			System.out.println(" [" + intf.getDisplayName() + "]");
			Enumeration<InetAddress> adr = intf.getInetAddresses();
			while (adr.hasMoreElements()) {
				System.out.println("\t" + adr.nextElement());
			}
			byte[] hardwareAddress = intf.getHardwareAddress();
			if (hardwareAddress != null) {
				System.out.print("Current MAC address : ");

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < hardwareAddress.length; i++) {
					sb.append(String.format("%02X%s", hardwareAddress[i],
							(i < hardwareAddress.length - 1) ? "-" : ""));
				}
				System.out.println(sb.toString());

			}

		}
		try {
			// InetAddress address = InetAddress.getByName("localhost");
			// ICMP Protokoll durch firewall ausgeschaltet
			InetAddress address = InetAddress.getByName("www.google.ch");
			System.out.println("Name: " + address.getHostName());
			System.out.println("Addr: " + address.getHostAddress());
			System.out.println("Reach: "
					+ address.isReachable(NetworkInterface.getByName("eth4"),
							33, 6000));
		} catch (UnknownHostException e) {
			System.err.println("Unable to lookup");
		} catch (IOException e) {
			System.err.println("Unable to reach");
		}
	}
}
