

	public class Zoo { 
		static String a;
		
		public static void main(String argv[]) { 
			Zoo zoo = new Zoo();
		}
		
	Zoo() {
	a = "alpha";
	Thread t = new Thread((Runnable) this); 
	t.start();
	Thread t1 = new Thread((Runnable) this); 
	a = "zebra";
	t1.start();
	}
	
	public void run() {
		for (int i = 0; i < 5; i++) {
		System.out.println(a);
		} 
	}
}
