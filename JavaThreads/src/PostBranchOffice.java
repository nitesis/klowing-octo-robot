import java.util.LinkedList;
import java.util.Queue;


public class PostBranchOffice {

	public static void main(String[] args) {
		
		PostService desk = new PostService(3);
		Thread t;
		Queue <Thread> warteSchlange = new LinkedList<>();
		
			for (int i = 1; i <= 20; i++) {
				t = new Thread(new PostClient("Kunde" + i, desk));
				warteSchlange.add(t);
				try {
					t.join();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				
			}
		

	}

}
