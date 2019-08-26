package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import udp.UDPEchoServer;

public class TimeClient {

private static final String SERVER_IP = "192.168.1.33";
	
	public static void main(String[] args) {
		Scanner scanner = null;
		DatagramSocket socket = null;
		
		try {
			//1. 키보드 연결
			scanner = new Scanner(System.in);
			
			//2. 소켓 생성
			socket = new DatagramSocket();
			
			SimpleDateFormat format = new SimpleDateFormat (" yyyy-MM-dd HH:mm:ss a");
			String date = format.format(new Date());
			
			
			while(true) {
				//3. 사용자 입력을 받음
				System.out.print(">>");
				String message = scanner.nextLine();
				
				if("quit".equals(message)) {
					break;
				}
				if(message.matches("\"\"")) {
					message = date;
				}
				
				
				//4. 메세지 전송
				byte[] sendData = message.getBytes("UTF-8"); 
				DatagramPacket sendPacket = new DatagramPacket(
					sendData,
					sendData.length, 
					new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
				socket.send(sendPacket);
				
				//5. 메세지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket); //blocking
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				message = new String(data, 0, length, "UTF-8");
				System.out.println("<<" + message);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(scanner != null) {
				scanner.close();
			}
			if(socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}
}
