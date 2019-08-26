package chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {

	private static final String IP = "192.168.107.1";
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		
		
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩
//			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(IP, PORT));
			log( "연결 기다림 " + IP + ":" + PORT);
			
			
			//3. 요청대기
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket).start();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && serverSocket.isClosed()== false) {
					serverSocket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public static void log(String log) {
		System.out.println("[ChatServer" + Thread.currentThread().getId() + "] " + log);
	}

}
