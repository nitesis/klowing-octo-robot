// DeadlockTest.java
public class DeadlockTest {
	private Object a = new Object();
	private Object b = new Object();

	public void f(String name) {
		System.out.println(name + " will a sperren");
		synchronized (a) {
			System.out.println(name + " a gesperrt");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			System.out.println(name + " will b sperren");
			synchronized (b) {
				System.out.println(name + " b gesperrt");
			}
		}
	}

	public void g(String name) {
		System.out.println(name + " will b sperren");
		synchronized (b) {
			System.out.println(name + " b gesperrt");
			System.out.println(name + " will a sperren");
			synchronized (a) {
				System.out.println(name + " a gesperrt");
			}
		}
	}

	public static void main(String[] args) {
		final DeadlockTest dt = new DeadlockTest();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				dt.f("Thread 1");
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				dt.g("Thread 2");
			}
		});

		t1.start();
		t2.start();
	}
}
