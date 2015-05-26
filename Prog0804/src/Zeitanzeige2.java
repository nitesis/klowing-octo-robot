// Zeitanzeige2.java
public class Zeitanzeige2 implements Runnable {
	// t wird von verschiedenen Threads gelesen und veraendert
	private volatile Thread t;
	private int z;
	

	public void start() {
		t = new Thread(this);
		t.start();
	}

	public void stop() {
		t = null;
	}

	public void run() {
		String name = Thread.currentThread().getName();
		while (Thread.currentThread() == t) {
			System.out.println(new java.util.Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.out.println(name + ": " + ++z);
		}
	}

	public static void main(String[] args) throws java.io.IOException {
		Zeitanzeige2 zeit = new Zeitanzeige2();
		Zeitanzeige2 zeit2 = new Zeitanzeige2();
		zeit.start();
		zeit2.start();

		System.in.read(); // blockiert bis RETURN
		zeit.stop();
		zeit2.stop();
		System.out.println("ENDE");
	}
}
