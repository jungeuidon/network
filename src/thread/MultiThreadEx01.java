package thread;

public class MultiThreadEx01 {

	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			System.out.println(i);
//		}
		
		Thread digitthread = new DigitThread();
		digitthread.start();
		
		for (char i = 'c'; i < 'z'; i++) {
			System.out.print(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
