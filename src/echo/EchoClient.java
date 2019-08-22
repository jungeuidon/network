package echo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	
	private static String SERVER_IP = "192.168.1.33";  //1.16 선생님
	private static int SERVER_PORT = 8000;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
	
		try {
			//1. Socket 생성
			socket = new Socket();
			//2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP , SERVER_PORT);
			socket.connect(inetSocketAddress);
			System.out.println("서버에 연결되었습니다.");
			
			//3. I/O STREAM 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			while(true) {
				System.out.print(">>");
				
				String word = sc.nextLine();
				os.write(word.getBytes("utf-8"));
				
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer);
				
				if(word.equals("exit")) { //word.matches("exit")
					System.out.println("서버를 종료합니다.(정상종료)");
					break;
				}
				
				String data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<< " + data);
				
			}
			
		} catch (Exception e) {
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
