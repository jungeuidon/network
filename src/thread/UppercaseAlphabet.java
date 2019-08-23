package thread;

public class UppercaseAlphabet {

	
		public void print() {
			for (char i = 'A'; i < 'z'; i++) {
				System.out.println(i);
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
	

}
