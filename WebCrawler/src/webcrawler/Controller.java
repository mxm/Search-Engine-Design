package webcrawler;

public class Controller extends Thread {
	
	int numThreads;
	int depth;
	boolean stop;
	
	Worker[] workers;
	
	public Controller (int numThreads, int depth) {
		this.numThreads = numThreads;
		this.depth = depth;
		workers = new Worker[numThreads];
	}
	
	public void run(){
		
		for(int i=0; i < numThreads;i++){
			workers[i] = new Worker();
			workers[i].start();
		}
		
		while(!stop){
						
		}
		
	}

}
