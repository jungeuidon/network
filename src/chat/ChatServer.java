package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {

//	private static final String IP = "127.0.0.1";  //자신의 IP
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		
		List<Writer> listPrintWriter = new ArrayList<Writer>();
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log( "연결 기다림 " + hostAddress + ":" + PORT);
			
			
			//3. 요청대기
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listPrintWriter).start();
			}
			
			
		} catch (SocketException e) {
			e.printStackTrace();
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
