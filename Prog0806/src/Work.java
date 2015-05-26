// Work.java
public class Work {
	public synchronized void doWorkA(String name) {
		System.out.println(name + ": Beginn A");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println(name + ": Ende A");
	}

	public synchronized static void doWorkB(String name) {
		System.out.println(name + ": Beginn B");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println(name + ": Ende B");
	}
}
