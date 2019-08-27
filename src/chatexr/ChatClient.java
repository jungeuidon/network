package chatexr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final int PORT = 8000;

	public static void main(String[] args) {
	
		Scanner sc = null;
		Socket socket = null;
		
		try {
			//1. 키보드연결
			sc = new Scanner(System.in);
			
			//2. socket 생성
			socket = new Socket();
			
			//3. 연결
			socket.connect(new InetSocketAddress(socket.getLocalAddress().getHostAddress(), PORT));
			
			//4. read/writer
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
			
			//5. join 프로토콜
			System.out.println("닉네임입력 >>");
			String nickname =sc.nextLine();
			printWriter.println("join : " + nickname);
			printWriter.flush();
			
			//6. ChatClientThread 시작
			new ChatClientThread(socket).start();
			
			
			//7. 키보드 입력처리
			while(true) {
				System.out.println(">>");
				String input = sc.nextLine();
			
				if("quit".equals(input)) {
					break;
				}
			
				printWriter.write(input);
				}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
}
