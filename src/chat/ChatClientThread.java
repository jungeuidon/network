package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class ChatClientThread extends Thread {
	
	private Socket socket =null;
	
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			
			while(true) {
				String line = br.readLine();
				if(line==null) {
					break;
				}
				System.out.println(line);

			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed()==false) {
					socket.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void log(String msg) {
		System.out.println("[TCP 클라이언트 ] : " + msg);
	}
}
