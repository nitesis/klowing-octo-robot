
public class R implements Runnable{

	int i = 0;
	
	@Override
	public synchronized void run() {
		do {
			System.out.println("Value of i = " + i++);
		} while (i < 10);
		
	}

}
