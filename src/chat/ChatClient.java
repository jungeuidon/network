package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final int PORT = 8000;
	
	
	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		
		try {
			
			//1. 키보드 연결
			sc = new Scanner(System.in);
			
			//2. socket 생성
			socket = new Socket();
			
			//3. 연결
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			socket.connect(new InetSocketAddress(hostAddress, PORT));
			
			//4. reader/writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			//5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname =sc.nextLine();
			System.out.println("클라이언트 ");
			pw.println("join:" + nickname);
			pw.flush();
			
			
			//6. ChatClientThread 시작
			Thread thread = new ChatClientThread(socket);
//			System.out.println("클라이언트 스레드시작");
			thread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() ==false) {
					socket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
