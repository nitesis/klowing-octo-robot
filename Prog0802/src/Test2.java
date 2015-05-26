// Test2.java
public class Test2 implements Runnable {
	private int z;

	public void run() {
		// currentThread liefert eine Referenz auf den aktuellen
		// Thread, getName liefert den Namen des Threads
		String name = Thread.currentThread().getName();
		for (int i = 0; i < 3; i++) {
			// sleep legt den Thread für 1 Sekunde schlafen
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.out.println(name + ": " + ++z);
		}
		System.out.println(name + ": Ich bin fertig!");
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Test2());
		Thread t2 = new Thread(new Test2());
		t1.start();
		t2.start();
		System.out.println("Habe zwei Threads gestartet.");
	}
}
