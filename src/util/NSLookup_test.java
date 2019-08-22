package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup_test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			while(true) {
			
			System.out.print(">");
			String domain = sc.nextLine();
			InetAddress[] inetAddresses = InetAddress.getAllByName(domain);
			
			if(domain.equals("exit")) {
				System.out.println("종료");
				break;
			}
			
			for(InetAddress inetAddress : inetAddresses) {	
				System.out.println(domain + " : "+ inetAddress.getHostAddress());
			}
				
			
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}

}
