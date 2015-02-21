
public class R implements Runnable {

	private int nr;
	
	public R(int nr) {
		this.nr = nr;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Hello " + nr + " " + i);
		}
	}

}

