package chat.client.win;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.ChatClientThread;

public class ChatClientApp {
	
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;
		
		

		
		try {
			//1. create socket
			socket  = new Socket();
			
			//2. connect to server
//			String hostAddress = InetAddress.getLocalHost().getHostAddress();
//			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			socket.connect(new InetSocketAddress("192.168.56.1", PORT));
			
			//3. create i/o stream
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			//4. join protocol 구현
			Thread thread = new chat.ChatClientThread(socket);
			thread.start();
			
			
			while( true ) {
				
				System.out.print(">>> ");
				name = scanner.nextLine();
				printWriter.println("join:" + name);
				

				if (name.isEmpty() == false ) {
					break;
				}
				
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
				if(scanner != null) {
					scanner.close();
					System.out.println("tq");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		new ChatWindow(socket, name).show(); //join이 오면 실행
	}

}
