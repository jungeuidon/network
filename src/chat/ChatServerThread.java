package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Severity;

public class ChatServerThread extends Thread{

	private String nickname;
	private Socket socket;
	List<Writer> listWriters = new ArrayList<Writer>();
	BufferedReader bufferedReader = null;
	PrintWriter printWriter = null;
	
//	ServerSocket serverSocket = null;
	
//	public ChatServerThread(Socket socket) {
//		this.socket = socket;
//	}
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		
		String request = null;
		//1. 외부 클라이언트 정보
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		log("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
				+ inetSocketAddress.getPort());
			try {
				//2. 스트림
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
				
				//3. 요청처리
				while(true) {
					request = bufferedReader.readLine();
					if(request ==null) {
						log("클라이언트로 부터 연결 끊김");
						doQuit(printWriter);
						break;
					}
					if(nickname !=null) {
						System.out.println(nickname + ":" + request);
					}
					
					//4. 프로토콜 분석
					String[] tokens = request.split(":");
//					System.out.println("request : " + request );
//					System.out.println("-------------");
//					System.out.println("[0]" + tokens[0]);
//					System.out.println("-------------");
//					System.out.println("[1]" + tokens[1]);
//					System.out.println("-------------");
//					System.out.println("[2]" + tokens[2]);
//					System.out.println("-------------");
					
					if("join".equals(tokens[0])) {
						System.out.println("조인이다임마");
						doJoin(tokens[1], printWriter);
					} else if ("message".equals(tokens[0])) {
						System.out.println("message !!!");
						doMessage(tokens[1]);
					} else if ("quit".equals(tokens[0])) {
						doQuit(printWriter);
					} else {
						ChatServer.log("Error : 알 수 없는 요청("+ tokens[0] +")");
					} 
					
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("서버 에러IO");
			} finally {
				try {
					if( socket != null && socket.isClosed() ==false ) {
						socket.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println("서버 에러2");
				}
			}
			
			
		} 
	
	
	private void log(String message) {
		System.out.println("[TCP Server] " + message);
	}
	
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		
		String data = nickName + "님이 참여하였습니다.";
		
		System.out.println("ServerTh doJoin : " + data);
		broadcast(data);
		
		//writer pool에 저장
		addWriter(writer);
		
		
		//ack 
		printWriter.println("채팅방에 성공적으로 입장하였습니다.");
		printWriter.flush();
		
	}
	
	private void doMessage(String message) {
		//구현
		
		 String data = nickname + ":" + message;
		 System.out.println("ServerTh doMessage : " + data);
		 broadcast(data);
	}
	
	private void doQuit(PrintWriter writer) {
		removeWriter( writer );
		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);
		
	}
	
	private void removeWriter( Writer writer) {
		//구현 : writer를 WriterPool에서 제거*************************
		listWriters.remove(writer);
		
		
	}
	
	private void addWriter(Writer writer) {
		synchronized(listWriters) {
			listWriters.add(writer);
		}
	}
	
	private void broadcast(String data) {
		synchronized(listWriters) {
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
}
