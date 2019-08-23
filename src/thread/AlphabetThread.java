package thread;

public class AlphabetThread extends Thread{

	
	@Override
	public void run() {
		for (char i = 'c'; i < 'z'; i++) {
			System.out.print(i);
	}
	
	
	}
}

