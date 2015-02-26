
public class PostService {

	private int places;
	
	public PostService(int places) { 
		if (places < 0) {
			places = 0;
		}
			this.places = places;	
	}
	public synchronized void enter() { 
		while (places == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			} 
		}
		places--; 
	}
	public synchronized void leave() { 
		places++;
		notify(); 
	}
}
