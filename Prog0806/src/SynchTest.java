// SynchTest.java
public class SynchTest extends Thread {
	private Work w;

	public SynchTest(Work w) {
		this.w = w;
	}

	public void run() {
		w.doWorkA(Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		Work w = new Work();
		SynchTest t1 = new SynchTest(w);
		SynchTest t2 = new SynchTest(w);
		SynchTest t3 = new SynchTest(w);

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
		}

		t3.start();
		Work.doWorkB(Thread.currentThread().getName());
	}
}
