
public class DeamonThreadExample {

	public static void main(String[] args) {
		Thread deamonThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("Deamon thread is running");
					}
				} catch (Exception e){}
				finally {
					System.out.println("Deamon Thread exiting.");
				}
			}
		}, "Deamon-Thread");

	}

}
