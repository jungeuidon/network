package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost {

	public static void main(String[] args) {
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname =  inetAddress.getHostName();  // host name : 컴퓨터이름
			String hostAddress = inetAddress.getHostAddress();
			byte[] ipAddresses = inetAddress.getAddress();
			
			for (byte ipAddress : ipAddresses) {
				System.out.print((int)ipAddress & 0x000000ff);
				System.out.print(".");
			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
