
public class TestThread {

	public static void main(String[] args) {
		
		R r = new R();
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		
		t1.start();
		t2.start();
	}
}
