package chatexr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	Socket socket;
	BufferedReader br = null;
	
	ChatClientThread(Socket socket) {
		this.socket = socket;
	}
	
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			while(true) {
			String data = br.readLine();
			if(data==null) {
				break;
			}
			System.out.println(data);
			}
			
		} catch (SocketException e) {
			System.out.println("소켓 종료");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed()==false) {
					socket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	
		
	}
}
