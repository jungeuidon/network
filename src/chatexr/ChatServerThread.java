package chatexr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread{

	private String nickname;
	private Socket socket;
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	List<Writer> listWriters = new ArrayList<Writer>();
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	
	@Override
	public void run() {
		
		
		try {
			//1. Remote Host info
			//String hostAddress = InetAddress.getLocalHost().getHostAddress();
			
			
			//2. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			//3. 요청처리
			while(true) {
				String request = bufferedReader.readLine();
				if(request ==null) {
					log("클라이언트로부터 연결끊김");
					doQuit(printWriter);
					break;
				}
				
				
				//4. 프로토콜 분석
				String[] tokens = request.split(":");
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if("quit".equals(tokens[0])) {
					doQuit(printWriter);
				} else {
					log("에러 : 알 수 없는 요청(" + tokens[0] + ")");
				}
			}
		
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private void log (String msg) {
		System.out.println("[TCP Server] : " + msg);
	}
	
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		
		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);
		
		addWriter(writer);
		
		//ack
		printWriter.println("join:OK");
		printWriter.flush();
	}
	
	private void doMessage (String message) {
		broadcast(message);
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}
	
	private void removeWriter(Writer writer) {
		listWriters.remove(writer);
	}
	
	private void addWriter(Writer writer) {
		synchronized(listWriters) {
			listWriters.add(writer);
		}
	}
	
	private void broadcast(String data) {
		synchronized(listWriters) {
			
			for(Writer writer: listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
}
