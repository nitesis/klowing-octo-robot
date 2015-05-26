// Zeitanzeige1.java
public class Zeitanzeige1 implements Runnable {
	public void run() {
		while (true) {
			System.out.println(new java.util.Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public static void main(String[] args) throws java.io.IOException {
		Zeitanzeige1 zeit = new Zeitanzeige1();
		Thread t = new Thread(zeit);
		t.start();

		System.in.read(); // blockiert bis RETURN
		t.interrupt();
		System.out.println("ENDE");
	}
}
