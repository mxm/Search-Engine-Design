package webcrawler;

import java.util.concurrent.CyclicBarrier;

public class Controller extends Thread {

	int numThreads;
	int depth = 0, maxDepth;
	volatile boolean stop;
	Queue q;

	Worker[] workers;
	CyclicBarrier barrier;

	public Controller(int numThreads,final int maxDepth) {
		this.numThreads = numThreads;
		this.maxDepth = maxDepth;
		workers = new Worker[numThreads];
		q = new Queue();
		barrier = new CyclicBarrier(numThreads, new Runnable() {

			public void run() {
				depth++;
				if (depth > maxDepth) {
					stop = true;
				} else {
					q.setDepth(depth);
					System.out.println("depth: " + depth);
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		// initiate Workers
		for (int i = 0; i < numThreads; i++)
			workers[i] = new Worker(this, barrier, q, i);
	}

	public void run() {

		// start workers
		for (Worker worker : workers) {
			worker.start();
		}

	}
}
